package com.eugene.examples.db;

import android.app.Application;

import com.eugene.examples.db.data.db.RealmDBManager;
import com.eugene.examples.db.data.db.SQLiteDBManager;
import com.facebook.stetho.Stetho;

/**
 * Created by eugene on 11/20/2017.
 */

public class DBApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if (SQLiteDBManager.isNull()) {
            SQLiteDBManager.initDatabase(this);
        }

        SQLiteDBManager.getInstance().openDB();

        if (RealmDBManager.isNull()) {
            RealmDBManager.initDatabase(this);
        }
    }
}
