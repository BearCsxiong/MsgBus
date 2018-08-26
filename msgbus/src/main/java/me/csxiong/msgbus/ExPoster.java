package me.csxiong.msgbus;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by csxiong on 2018/8/26.
 */

public class ExPoster {

    private ExPoster() {
        executor = new ScheduledThreadPoolExecutor(8);
        handler = new Handler(Looper.getMainLooper());
    }

    private static ExPoster exPoster;

    public static ExPoster getInstance() {
        if (exPoster == null) {
            exPoster = new ExPoster();
        }
        return exPoster;
    }

    private final ScheduledThreadPoolExecutor executor;

    private final Handler handler;

    public void runOnMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    public void runOnBackground(Runnable runnable) {
        executor.execute(runnable);
    }
}
