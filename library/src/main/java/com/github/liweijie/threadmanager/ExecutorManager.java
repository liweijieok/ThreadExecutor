package com.github.liweijie.threadmanager;

import android.util.Log;
import android.util.SparseArray;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import domain.ExecutorConfig;
import domain.ExecutorType;

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

    private static SparseArray<ExecutorConfig> DEFAULT_CONFIGS = new SparseArray<>();

    static {
        //cpu个数
        int processors = Runtime.getRuntime().availableProcessors() + 1;
        //无限，而且内部超出的话内部使用线程直接开启，不用管外部环境
        DEFAULT_CONFIGS.put(ExecutorType.INFINITE.getType(), new ExecutorConfig(0, Integer.MAX_VALUE, new DefaultThreadFactory(ExecutorType.INFINITE.toString()), 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()));
        //超出丢弃旧的
        DEFAULT_CONFIGS.put(ExecutorType.DISCARD_OLDEST_POLICY.getType(), new ExecutorConfig(processors, processors, new DefaultThreadFactory(ExecutorType.DISCARD_OLDEST_POLICY.toString()), 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(256)));
        //超出不处理
        DEFAULT_CONFIGS.put(ExecutorType.DISCARD_POLICY.getType(), new ExecutorConfig(processors, processors, new DefaultThreadFactory(ExecutorType.DISCARD_POLICY.toString()), 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128)));
        //超出打断
        DEFAULT_CONFIGS.put(ExecutorType.ABORT_POLICY.getType(), new ExecutorConfig(processors, processors, new DefaultThreadFactory(ExecutorType.ABORT_POLICY.toString()), 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(128)));
        //无限，使用系统的拒绝策略，但是假如超出的话，得开外部的环境，外部自己去执行任务
        DEFAULT_CONFIGS.put(ExecutorType.CALLER_RUNS_POLICY.getType(), new ExecutorConfig(0, Integer.MAX_VALUE, new DefaultThreadFactory(ExecutorType.INFINITE.toString()), 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()));
    }


    private SparseArray<ThreadPoolExecutor> mExecutors;

    private ExecutorManager() {
        mExecutors = new SparseArray<>(5);
    }

    private static final class ExecutorManagerHolder {
        private static final ExecutorManager INSTANCE = new ExecutorManager();
    }

    /**
     * 设置各种类型的配置
     * 建议只是配置AbortPolicy，DiscardPolicy，DiscardOldestPolicy这三种的，其他三个使用默认的即可
     *
     * @param type
     * @param config
     * @return
     */
    public ExecutorManager config(ExecutorType type, ExecutorConfig config) {
        if (config != null) {
            DEFAULT_CONFIGS.put(type.getType(), config);
        }
        return this;
    }

    public static ExecutorManager getManager() {
        return ExecutorManagerHolder.INSTANCE;
    }


    public synchronized ThreadPoolExecutor getExecutor(ExecutorType type) {
        if (type == null) {
            Log.e("ExecutorManager>>", "The ExecutorType is null!!!Please set a valid ExecutorType");
            return null;
        }
        ThreadPoolExecutor executor = mExecutors.get(type.getType());
        if (executor != null) {
            return executor;
        }
        initExecutor(type);
        return mExecutors.get(type.getType());
    }

    private void initExecutor(ExecutorType type) {
        ExecutorConfig config = DEFAULT_CONFIGS.get(type.getType());
        if (config == null) {
            throw new IllegalStateException("ExecutorConfig is null ");
        }
        RejectedExecutionHandler handler;
        switch (type) {
            case INFINITE:
                handler = new InfiniteHandler();
                break;
            case ABORT_POLICY:
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case DISCARD_POLICY:
                handler = new ThreadPoolExecutor.DiscardPolicy();
                break;
            case CALLER_RUNS_POLICY:
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case DISCARD_OLDEST_POLICY:
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            default:
                throw new IllegalStateException("ExecutorType is null ");
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getCoreSize(), config.getMaxSize(), config.getKeepAliveTime(), config.getUnit(), config.getTasks(), config.getFactory(), handler);
        mExecutors.put(type.getType(), executor);
    }


}
