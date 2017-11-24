package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.db.RealmDBManager;
import com.eugene.examples.db.data.db.SQLiteDBManager;
import com.eugene.examples.db.data.model.RealmUser;
import com.eugene.examples.db.data.model.User;
import com.eugene.examples.db.ui.callback.DBOperationCallback;

import java.util.List;

/**
 * Created by eugene on 11/21/2017.
 */

public class DBPresenterImpl implements DBPresenter, DBOperationCallback {
    private DBView view;

    public DBPresenterImpl(DBView view) {
        this.view = view;
    }

    @Override
    public void insertSQLite(@NonNull final List<User> userList) {
        SQLiteDBManager.getInstance().insertUsers(userList, this);
    }

    @Override
    public void readSQLite() {
        SQLiteDBManager.getInstance().getUsers(this);
    }

    @Override
    public void updateSQLite(@NonNull final List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).setFullName("Test" + i);
        }
        SQLiteDBManager.getInstance().updateUsers(userList, this);
    }

    @Override
    public void deleteSQLite(@NonNull final List<User> userList) {
        SQLiteDBManager.getInstance().deleteUsers(userList, this);
    }

    @Override
    public void insertRealm(@NonNull final List<RealmUser> realmUserList) {
        RealmDBManager.getInstance().insertUsers(realmUserList, this);
    }

    @Override
    public void readRealm() {
        RealmDBManager.getInstance().getUsers(this);
    }

    @Override
    public void updateRealm(@NonNull final List<RealmUser> realmUserList) {
        for (int i = 0; i < realmUserList.size(); i++) {
            realmUserList.get(i).setFullName("Test" + i);
        }
        RealmDBManager.getInstance().updateUsers(realmUserList, this);
    }

    @Override
    public void deleteRealm(@NonNull final List<RealmUser> realmUserList) {
        RealmDBManager.getInstance().deleteUsers(realmUserList, this);
    }

    @Override
    public void onStartDBOperations() {
        view.showProgress();
    }

    @Override
    public void onFinishDBOperations() {
        view.hideProgress();
    }

    @Override
    public void onSQLiteInsertFinished(@NonNull final String timeElapsed) {
        view.onSQLiteInsertCompleted(timeElapsed);
    }

    @Override
    public void onSQLiteReadFinished(@NonNull final String timeElapsed) {
        view.onSQLiteReadCompleted(timeElapsed);
    }

    @Override
    public void onSQLiteUpdateFinished(@NonNull final String timeElapsed) {
        view.onSQLiteUpdateCompleted(timeElapsed);
    }

    @Override
    public void onSQLiteDeleteFinished(@NonNull final String timeElapsed) {
        view.onSQLiteDeleteCompleted(timeElapsed);
    }

    @Override
    public void onRealmInsertFinished(@NonNull final String timeElapsed) {
        view.onRealmInsertCompleted(timeElapsed);
    }

    @Override
    public void onRealmReadFinished(@NonNull final List<RealmUser> realmUserList, @NonNull final String timeElapsed) {
        view.onRealmReadCompleted(timeElapsed);
    }

    @Override
    public void onRealmUpdateFinished(@NonNull final String timeElapsed) {
        view.onRealmUpdateCompleted(timeElapsed);
    }

    @Override
    public void onRealmDeleteFinished(@NonNull final String timeElapsed) {
        view.onRealmDeleteCompleted(timeElapsed);
    }
}
