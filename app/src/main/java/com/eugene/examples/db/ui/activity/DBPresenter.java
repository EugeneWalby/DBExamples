package com.eugene.examples.db.ui.activity;

import android.support.annotation.NonNull;

import com.eugene.examples.db.data.model.User;

import java.util.List;

/**
 * Created by eugene on 11/21/2017.
 */

public interface DBPresenter {
    void insertToSQLite(@NonNull final List<User> users);
}
