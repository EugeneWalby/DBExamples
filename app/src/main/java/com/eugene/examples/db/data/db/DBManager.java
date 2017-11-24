package com.eugene.examples.db.data.db;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.User;
import com.eugene.examples.db.ui.callback.DBOperationCallback;

import java.util.List;

/**
 * Created by eugene on 11/23/2017.
 */

public interface DBManager {
    void insertUsers(@NonNull final List<User> userList, @NonNull final DBOperationCallback callback);
}
