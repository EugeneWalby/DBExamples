package com.eugene.examples.db.ui.callback;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.RealmUser;

import java.util.List;

public interface DBOperationCallback {
    void onStartDBOperations();
    void onFinishDBOperations();
    void onSQLiteInsertFinished(@NonNull final String timeElapsed);
    void onSQLiteReadFinished(@NonNull final String timeElapsed);
    void onSQLiteUpdateFinished(@NonNull final String timeElapsed);
    void onSQLiteDeleteFinished(@NonNull final String timeElapsed);

    void onRealmInsertFinished(@NonNull final String timeElapsed);
    void onRealmReadFinished(@NonNull final List<RealmUser> realmUserList, @NonNull final String timeElapsed);
    void onRealmUpdateFinished(@NonNull final String timeElapsed);
    void onRealmDeleteFinished(@NonNull final String timeElapsed);
}