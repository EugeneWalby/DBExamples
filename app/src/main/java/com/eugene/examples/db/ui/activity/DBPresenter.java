package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.User;

import java.util.List;

/**
 * Created by eugene on 11/21/2017.
 */

public interface DBPresenter {
    void insertSQLite(@NonNull final List<User> userList);
    void readSQLite();
    void updateSQLite(@NonNull final List<User> userList);
    void deleteSQLite(@NonNull final List<User> userList);

    void insertRealm(@NonNull final List<User> userList);
    void readRealm();
    void updateRealm(@NonNull final List<User> userList);
    void deleteRealm(@NonNull final List<User> userList);
}
