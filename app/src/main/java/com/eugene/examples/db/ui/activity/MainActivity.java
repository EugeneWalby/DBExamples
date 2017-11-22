package com.eugene.examples.db.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eugene.examples.db.R;
import com.eugene.examples.db.data.db.SQLiteDBManager;
import com.eugene.examples.db.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DBView {
    private static final int USERS_COUNT = 25000;
    private List<User> usersList;
    private DBPresenter presenter;
    private ProgressBar progressBar;
    private TextView tvSQLiteInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        createUsersList();
        presenter.insertToSQLite(usersList);
//        updateSQLite();
//        readFromSQLite();
//        deleteFromSQLite();

//        Realm realm = Realm.getDefaultInstance();
    }

    private void init() {
        presenter = new DBPresenterImpl(this);
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        tvSQLiteInsert = (TextView) findViewById(R.id.tvSQLiteInsert);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSQLiteInsertCompleted(@NonNull final String timeElapsed) {
        tvSQLiteInsert.setText(timeElapsed);
    }

    private void createUsersList() {
        usersList = new ArrayList<>(USERS_COUNT);
        for (int i = 0; i < USERS_COUNT; i++) {
            usersList.add(new User("User" + i, 20 + i % 20, "City" + i));
        }
    }

    private void updateSQLite() {
        for (int i = 0; i < usersList.size(); i++) {
            usersList.get(i).setFullName("Test" + i);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < usersList.size(); i++) {
            SQLiteDBManager.getInstance().updateUser(usersList.get(i), i + 1);
        }
        long endTime = System.currentTimeMillis();
        Log.d("TIME SQLITE update", String.valueOf(endTime - startTime));
    }

    private void readFromSQLite() {
        long startTime = System.currentTimeMillis();
        SQLiteDBManager.getInstance().getUsers();
        long endTime = System.currentTimeMillis();
        Log.d("TIME SQLITE read", String.valueOf(endTime - startTime));
    }

    private void deleteFromSQLite() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < USERS_COUNT; i++) {
            SQLiteDBManager.getInstance().deleteUser(i + 1);
        }
        long endTime = System.currentTimeMillis();
        Log.d("TIME SQLITE delete", String.valueOf(endTime - startTime));
    }
}
