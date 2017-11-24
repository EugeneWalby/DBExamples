package com.eugene.examples.db.data.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.RealmUser;
import com.eugene.examples.db.ui.callback.DBOperationCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by eugene on 11/23/2017.
 */

public class RealmDBManager {
    private static RealmDBManager realmManager;
    private Realm realm;

    private Executor executor;
    private Handler handler;

    public static void initDatabase(@NonNull final Context context) {
        realmManager = new RealmDBManager(context);
    }

    public static boolean isNull() {
        return realmManager == null;
    }

    public static RealmDBManager getInstance() {
        return realmManager;
    }

    private RealmDBManager(@NonNull final Context context) {
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Realm.init(context);
                realm = Realm.getDefaultInstance();
            }
        });
    }

    public void insertUsers(@NonNull final List<RealmUser> realmUserList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onStartDBOperations();
                    }
                });
                final long startTime = System.currentTimeMillis();
                realm.beginTransaction();
                realm.insert(realmUserList);
                realm.commitTransaction();
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRealmInsertFinished(String.valueOf(endTime - startTime));
                    }
                });
            }
        });
    }

    public void getUsers(@NonNull final DBOperationCallback callback) {
        final List<RealmUser> userList = new ArrayList<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                RealmResults<RealmUser> users = realm.where(RealmUser.class).findAll();
                final long endTime = System.currentTimeMillis();
                for (RealmUser realmUser : users) {
                    userList.add(realmUser);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRealmReadFinished(userList, String.valueOf(endTime - startTime));
                    }
                });
            }
        });
    }

    public void updateUsers(@NonNull final List<RealmUser> realmUserList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(realmUserList);
                realm.commitTransaction();
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRealmUpdateFinished(String.valueOf(endTime - startTime));
                    }
                });
            }
        });
    }

    public void deleteUsers(@NonNull final List<RealmUser> realmUserList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                realm.beginTransaction();
                realm.where(RealmUser.class).findAll().deleteAllFromRealm();
                realm.commitTransaction();
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRealmDeleteFinished(String.valueOf(endTime - startTime));
                        callback.onFinishDBOperations();
                    }
                });
            }
        });
    }
}
