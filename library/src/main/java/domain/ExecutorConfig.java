package domain;

import com.github.liweijie.threadmanager.DefaultThreadFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
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
public class ExecutorConfig {
    private int coreSize;
    private int maxSize;
    private ThreadFactory factory;
    private long keepAliveTime;
    private TimeUnit unit;
    private BlockingQueue<Runnable> tasks;

    public ExecutorConfig(int coreSize, int maxSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> tasks) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.tasks = tasks;
        this.factory = new DefaultThreadFactory("thread");
    }

    public ExecutorConfig(int coreSize, int maxSize, ThreadFactory factory, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> tasks) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.factory = factory;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.tasks = tasks;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public ThreadFactory getFactory() {
        return factory;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public BlockingQueue<Runnable> getTasks() {
        return tasks;
    }

}
