package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

/**
 * Created by eugene on 11/21/2017.
 */

public interface DBView {
    void showProgress();
    void hideProgress();
    void onSQLiteInsertCompleted(@NonNull final String timeElapsed);
}
