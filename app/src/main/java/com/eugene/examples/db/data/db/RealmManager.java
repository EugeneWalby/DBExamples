package com.eugene.examples.db.data.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.RealmUser;
import com.eugene.examples.db.data.model.User;
import com.eugene.examples.db.ui.callback.DBOperationCallback;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.realm.Realm;

/**
 * Created by eugene on 11/23/2017.
 */

public class RealmManager {
    private static RealmManager realmManager;
    private Realm realm;

    private Executor executor;
    private Handler handler;

    public static void initDatabase(@NonNull final Context context) {
        realmManager = new RealmManager(context);
    }

    public static boolean isNull() {
        return realmManager == null;
    }

    public static RealmManager getInstance() {
        return realmManager;
    }

    private RealmManager(@NonNull final Context context) {
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                realm = Realm.getInstance(context);
            }
        });
    }

    public void insertUsers(@NonNull final List<User> userList, @NonNull final DBOperationCallback callback) {
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
                for (int i = 0; i < userList.size(); i++) {
                    RealmUser realmUser = realm.createObject(RealmUser.class);
                    realmUser.setId(i + 1);
                    realmUser.setFullName(userList.get(i).getFullName());
                    realmUser.setAge(userList.get(i).getAge());
                    realmUser.setCity(userList.get(i).getCity());
                }
                realm.commitTransaction();
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onRealmInsertFinished(String.valueOf(endTime - startTime));
                        callback.onFinishDBOperations();
                    }
                });
            }
        });
    }
}
