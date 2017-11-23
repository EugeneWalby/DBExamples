package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

/**
 * Created by eugene on 11/21/2017.
 */

public interface DBView<T> {
    void showProgress();
    void hideProgress();
    void onSQLiteInsertCompleted(@NonNull final String timeElapsed);
    void onSQLiteReadCompleted(@NonNull final String timeElapsed);
    void onSQLiteUpdateCompleted(@NonNull final String timeElapsed);
    void onSQLiteDeleteCompleted(@NonNull final String timeElapsed);

    void onRealmInsertCompleted(@NonNull final String timeElapsed);
    void onRealmReadCompleted(@NonNull final String timeElapsed);
    void onRealmUpdateCompleted(@NonNull final String timeElapsed);
    void onRealmDeleteCompleted(@NonNull final String timeElapsed);
}
