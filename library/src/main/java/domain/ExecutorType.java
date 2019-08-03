package domain;

/**
 * 作者：黎伟杰 on 2019/7/7.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public enum ExecutorType {
    /**
     * 无限添加任务,当主队列满了的时候，会直接开线程去执行,不用外部处理
     */
    INFINITE(1),
    /**
     * 任务满的时候，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
     */
    DISCARD_OLDEST_POLICY(2),
    /**
     * 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
     */
    DISCARD_POLICY(3),
    /**
     * 如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行
     */
    CALLER_RUNS_POLICY(4),
    /**
     * 如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
     */
    ABORT_POLICY(5);

    private int type;

    ExecutorType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
            case 1:
                return "infinite";
            case 2:
                return "discard_oldest_policy";
            case 3:
                return "discard_policy";
            case 4:
                return "caller_runs_policy";
            case 5:
                return "abort_policy";
            default:
                return "unkonw";
        }
    }}
