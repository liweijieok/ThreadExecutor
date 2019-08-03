package com.github.liweijie.threadmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * 作者：黎伟杰 on 2019/8/1.
 * 邮箱：liweijieok@qq.com
 * description： 系统的线程池
 * update by:
 * update day:
 *
 * @author liweijie
 */
public class SystemThreadManager {

    private SystemThreadManager() {

    }

    private static final class SystemThreadManagerHolder {
        private static final SystemThreadManager MANAGER = new SystemThreadManager();
    }

    public static SystemThreadManager getInstance() {
        return SystemThreadManagerHolder.MANAGER;
    }

    public ExecutorService newCache() {
        return newCache(new DefaultThreadFactory("cache"));
    }

    public ExecutorService newCache(ThreadFactory factory) {
        return Executors.newCachedThreadPool(factory);
    }

    public ExecutorService newFix(int size) {
        return newFix(size, new DefaultThreadFactory("fix"));
    }

    public ExecutorService newFix(int size, ThreadFactory factory) {
        return Executors.newFixedThreadPool(size, factory);
    }

    public ScheduledExecutorService newSchedule(int coreSize) {
        return Executors.newScheduledThreadPool(coreSize);
    }

    public ScheduledExecutorService newSchedule(int size, ThreadFactory fac) {
        return Executors.newScheduledThreadPool(size, fac);
    }

    public ScheduledExecutorService newSingleSchedule() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    public ScheduledExecutorService newSingleSchedule(ThreadFactory fac) {
        return Executors.newSingleThreadScheduledExecutor(fac);
    }



}
