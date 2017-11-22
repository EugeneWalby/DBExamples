package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.db.SQLiteDBManager;
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
    public void insertToSQLite(@NonNull final List<User> users) {
        view.showProgress();
        SQLiteDBManager.getInstance().insertUsers(users, this);
    }

    @Override
    public void onOperationFinished(@NonNull final String timeElapsed) {
        view.onSQLiteInsertCompleted(timeElapsed);
        view.hideProgress();
    }
}
