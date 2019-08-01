package com.github.liweijie.threadmanager;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 作者：黎伟杰 on 2019/7/7.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public abstract class AbstractExecutor {

    private ThreadPoolExecutor executor;

    /**
     * 线程池类型
     *
     * @return
     */
    public abstract ExecutorType getType();

    protected AbstractExecutor() {
        this.executor = ExecutorManager.getManager().getExecutor(getType());
    }

    public ThreadPoolExecutor getExecutor() {
        return this.executor;
    }

    /**
     * 执行一个Runnable任务
     *
     * @param task
     */
    public void execute(Runnable task) {
        this.executor.execute(task);
    }

    /**
     * 停止任务，他是之不再执行新任务，但是执行中的任务不会被停止
     */
    public void shutDown() {
        this.executor.shutdown();
    }

    /**
     * 立即停止任务，并且返回正在执行的任务
     *
     * @return
     */
    public List<Runnable> shatDownNow() {
        return this.executor.shutdownNow();
    }

    /**
     * 提交Runnable任务并可以返回Future
     *
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task) {
        return this.executor.submit(task);
    }

    /**
     * 提交Callable任务，并返回Future
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> task) {
        return this.executor.submit(task);
    }

    /**
     * 预启动所有的核心线程
     */
    public void prestartCoreThread() {
        this.executor.prestartCoreThread();
    }

    /**
     * 预启动所有的线程池
     */
    public void prestartAllCoreThreads() {
        this.executor.prestartAllCoreThreads();
    }

    /**
     * isShutdown
     *
     * @return
     */
    public boolean isShutdown() {
        return this.executor.isShutdown();
    }

    /**
     * isTerminated
     *
     * @return
     */
    public boolean isTerminated() {
        return this.executor.isTerminated();
    }

    /**
     * isTerminating
     *
     * @return
     */
    public boolean isTerminating() {
        return this.executor.isTerminating();
    }

    /**
     * awaitTermination
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.executor.awaitTermination(timeout, unit);
    }

    /**
     * 重新设置线程池的最大线程数量
     *
     * @param maximumPoolSize
     */
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.executor.setMaximumPoolSize(maximumPoolSize);
    }

    /**
     * 是否允许核心线程超时
     *
     * @param isAllow
     */
    public void allowCoreThreadTimeOut(boolean isAllow) {
        this.executor.allowCoreThreadTimeOut(isAllow);
    }

    /**
     * @return
     */
    public boolean isAllowCoreThreadTimeOut() {
        return this.executor.allowsCoreThreadTimeOut();
    }

}
