package com.eugene.examples.db.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eugene.examples.db.R;
import com.eugene.examples.db.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DBView {
    private static final int USERS_COUNT = 10000;
    private List<User> userList;
    private DBPresenter presenter;

    private ProgressBar progressBar;
    private TextView tvSQLiteInsert;
    private TextView tvSQLiteRead;
    private TextView tvSQLiteUpdate;
    private TextView tvSQLiteDelete;

    private TextView tvRealmInsert;
    private TextView tvRealmRead;
    private TextView tvRealmUpdate;
    private TextView tvRealmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        createUsersList();
//        presenter.insertSQLite(userList);
//        presenter.readSQLite();
//        presenter.updateSQLite(userList);
//        presenter.deleteSQLite(userList);

        presenter.insertRealm(userList);
    }

    private void init() {
        presenter = new DBPresenterImpl(this);
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        tvSQLiteInsert = (TextView) findViewById(R.id.tvSQLiteInsert);
        tvSQLiteRead = (TextView) findViewById(R.id.tvSQLiteRead);
        tvSQLiteUpdate = (TextView) findViewById(R.id.tvSQLiteUpdate);
        tvSQLiteDelete = (TextView) findViewById(R.id.tvSQLiteDelete);

        tvRealmInsert = (TextView) findViewById(R.id.tvRealmInsert);
        tvRealmRead = (TextView) findViewById(R.id.tvRealmRead);
        tvRealmUpdate = (TextView) findViewById(R.id.tvRealmUpdate);
        tvRealmDelete = (TextView) findViewById(R.id.tvRealmDelete);
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

    @Override
    public void onSQLiteReadCompleted(@NonNull final String timeElapsed) {
        tvSQLiteRead.setText(timeElapsed);
    }

    @Override
    public void onSQLiteUpdateCompleted(@NonNull final String timeElapsed) {
        tvSQLiteUpdate.setText(timeElapsed);
    }

    @Override
    public void onSQLiteDeleteCompleted(@NonNull final String timeElapsed) {
        tvSQLiteDelete.setText(timeElapsed);
    }

    @Override
    public void onRealmInsertCompleted(@NonNull String timeElapsed) {
        tvRealmInsert.setText(timeElapsed);
    }

    @Override
    public void onRealmReadCompleted(@NonNull String timeElapsed) {
        tvRealmRead.setText(timeElapsed);
    }

    @Override
    public void onRealmUpdateCompleted(@NonNull String timeElapsed) {
        tvRealmUpdate.setText(timeElapsed);
    }

    @Override
    public void onRealmDeleteCompleted(@NonNull String timeElapsed) {
        tvRealmDelete.setText(timeElapsed);
    }

    private void createUsersList() {
        userList = new ArrayList<>(USERS_COUNT);
        for (int i = 0; i < USERS_COUNT; i++) {
            userList.add(new User("User" + i, 20 + i % 20, "City" + i));
        }
    }
}
