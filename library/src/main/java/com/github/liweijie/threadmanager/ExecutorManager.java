package com.github.liweijie.threadmanager;

import android.util.Log;
import android.util.SparseArray;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 作者：黎伟杰 on 2019/6/19.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public class ExecutorManager {

    /**
     *
     */
    private int infinite;
    private SparseArray<ThreadPoolExecutor> mExcutors;

    private ExecutorManager() {
        mExcutors = new SparseArray<>(5);
    }

    private static final class ExecutorManagerHolder {
        private static final ExecutorManager INSTANCE = new ExecutorManager();
    }

    public static ExecutorManager getManager() {
        return ExecutorManagerHolder.INSTANCE;
    }

    /**
     * 可自己配置具体的每种类型的队列个数
     * 否则使用默认的
     *
     * @param infinite
     */
    public void config(int infinite) {
        this.infinite = infinite;
    }

    public synchronized ThreadPoolExecutor getExecutor(ExecutorType type) {
        if (type == null) {
            Log.e("ExecutorManager>>", "The ExecutorType is null!!!Please set a valid ExecutorType");
            return null;
        }
        ThreadPoolExecutor executor = mExcutors.get(type.getType());
        if (executor != null) {
            return executor;
        }
        initExecutor(type);
        return mExcutors.get(type.getType());
    }

    private void initExecutor(ExecutorType type) {
        int corePoolSize = 0, maximumPoolSize = 0, keepAliveTime = 0;
        LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>();
        switch (type) {
            case INFINITE:
                break;

            default:
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, workQueue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        mExcutors.put(type.getType(), executor);
    }

}
