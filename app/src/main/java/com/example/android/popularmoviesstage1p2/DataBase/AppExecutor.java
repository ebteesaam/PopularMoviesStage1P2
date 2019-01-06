package com.example.android.popularmoviesstage1p2.DataBase;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ebtesam on 24/12/2018 AD.
 */

public class AppExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }
}