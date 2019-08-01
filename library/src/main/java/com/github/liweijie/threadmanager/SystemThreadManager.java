package com.github.liweijie.threadmanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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

    public ExecutorService getCache() {
        return getCache(new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setName("thread_" + count.getAndIncrement());
                return t;
            }
        });
    }

    public ExecutorService getCache(ThreadFactory factory) {
        return Executors.newCachedThreadPool(factory);
    }

    public ExecutorService getFix(int size) {
        return getFix(size, new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setName("thread_" + count.getAndIncrement());
                return t;
            }
        });
    }

    public ExecutorService getFix(int size, ThreadFactory factory) {
        return Executors.newFixedThreadPool(size, factory);
    }


}
