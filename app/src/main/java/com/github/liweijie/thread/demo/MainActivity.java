package com.github.liweijie.thread.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.liweijie.threadmanager.ExecutorManager;
import com.github.liweijie.threadmanager.imp.AbortExecutor;
import com.github.liweijie.threadmanager.imp.CallerRunsExecutor;
import com.github.liweijie.threadmanager.imp.DiscardExecutor;
import com.github.liweijie.threadmanager.imp.DiscardOldest;
import com.github.liweijie.threadmanager.imp.InfiniteExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.github.liweijie.threadmanager.domain.ExecutorConfig;
import com.github.liweijie.threadmanager.domain.ExecutorType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExecutorConfig config = new ExecutorConfig(4, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        ExecutorManager.getManager().config(ExecutorType.DISCARD_POLICY, config);

        DiscardExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "DiscardExecutor");
            }
        });
        InfiniteExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "InfiniteExecutor");
            }
        });
        DiscardOldest.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "DiscardOldest");
            }
        });
        CallerRunsExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "CallerRunsExecutor");
            }
        });
        AbortExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "AbortExecutor");
            }
        });
    }
}
