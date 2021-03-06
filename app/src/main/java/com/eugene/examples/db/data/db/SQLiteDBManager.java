package com.eugene.examples.db.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.User;
import com.eugene.examples.db.ui.callback.DBOperationCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.R.attr.name;

/**
 * Created by eugene on 11/20/2017.
 */

public class SQLiteDBManager extends SQLiteOpenHelper implements DBManager {
    private static final String DB_NAME = "SQLiteDB";
    private static final int VERSION = 1;
    private static final String TABLE_USER = "user_table";
    private static final String ID = "_id";
    private static final String FULL_NAME = "first_name";
    private static final String AGE = "age";
    private static final String CITY = "city";

    private static final String TYPE_TEXT = " TEXT NOT NULL";
    private static final String TYPE_INTEGER = " INTEGER NOT NULL";
    private static final String COMMA_SEP = ", ";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = "  AUTOINCREMENT";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            " (" +
            ID + TYPE_INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA_SEP +
            FULL_NAME + TYPE_TEXT + COMMA_SEP +
            AGE + TYPE_INTEGER + COMMA_SEP +
            CITY + TYPE_TEXT +
            ")";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static SQLiteDBManager dbManager;
    private SQLiteDatabase db;
    private Executor executor;
    private Handler handler;

    public static SQLiteDBManager getInstance() {
        return dbManager;
    }

    private SQLiteDBManager(@NonNull final Context context) {
        super(context, DB_NAME, null, VERSION);
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    public static void initDatabase(@NonNull final Context context) {
        dbManager = new SQLiteDBManager(context);
    }

    public static boolean isNull() {
        return dbManager == null;
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        db.execSQL(DROP_TABLE + name);
        onCreate(db);
    }

    public void openDB() {
        db = getWritableDatabase();
    }

    public void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void insertUsers(@NonNull final List<User> userList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                callback.onStartDBOperations();
                final long startTime = System.currentTimeMillis();
                db.beginTransaction();
                try {
                    for (User user : userList) {
                        insertUser(user);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                    final long endTime = System.currentTimeMillis();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSQLiteInsertFinished(String.valueOf(endTime - startTime));
                        }
                    });
                }
            }
        });
    }

    private void insertUser(@NonNull final User user) {
        final ContentValues cv = new ContentValues();
        cv.put(FULL_NAME, user.getFullName());
        cv.put(AGE, user.getAge());
        cv.put(CITY, user.getCity());
        db.insert(TABLE_USER, null, cv);
    }

    public void updateUsers(@NonNull final List<User> userList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                db.beginTransaction();
                try {
                    for (int i = 0; i < userList.size(); ++i) {
                        updateUser(userList.get(i), i);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                    final long endTime = System.currentTimeMillis();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSQLiteUpdateFinished(String.valueOf(endTime - startTime));
                        }
                    });
                }
            }
        });
    }

    private void updateUser(@NonNull final User user, final int id) {
        final ContentValues cv = new ContentValues();
        cv.put(FULL_NAME, user.getFullName());
        cv.put(AGE, user.getAge());
        cv.put(CITY, user.getCity());
        final String where = ID + " = " + String.valueOf(id);
        db.update(TABLE_USER, cv, where, null);
    }

    public List<User> getUsers(@NonNull final DBOperationCallback callback) {
        final String select = "SELECT * FROM " + TABLE_USER;
        final Cursor cursor = db.rawQuery(select, null);
        return getUserModelList(cursor, callback);
    }

    private List<User> getUserModelList(@NonNull final Cursor cursor, @NonNull final DBOperationCallback callback) {
        final List<User> userModelList = new ArrayList<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    final User user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(FULL_NAME)));
                    user.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(AGE)));
                    user.setCity(cursor.getString(cursor.getColumnIndexOrThrow(CITY)));
                    userModelList.add(user);
                }
                cursor.close();
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSQLiteReadFinished(String.valueOf(endTime - startTime));
                    }
                });
            }
        });

        return userModelList;
    }

    public void deleteUsers(@NonNull final List<User> userList, @NonNull final DBOperationCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final long startTime = System.currentTimeMillis();
                for (int i = 0; i < userList.size(); i++) {
                    deleteUser(i + 1);
                }
                final long endTime = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSQLiteDeleteFinished(String.valueOf(endTime - startTime));
                        callback.onFinishDBOperations();
                    }
                });
            }
        });
    }

    private void deleteUser(final int id) {
        db.delete(TABLE_USER, ID + "=" + id, null);
    }
}
