package com.eugene.examples.db.ui.callback;

import android.support.annotation.NonNull;

public interface DBOperationCallback {
    void onOperationFinished(@NonNull final String timeElapsed);
}