package com.github.liweijie.threadmanager.imp;

import com.github.liweijie.threadmanager.AbstractExecutor;

import com.github.liweijie.threadmanager.domain.ExecutorType;

/**
 * 作者：黎伟杰 on 2019/7/7.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public class CallerRunsExecutor extends AbstractExecutor {

    private CallerRunsExecutor() {
    }

    private static class CallerRunsExecutorHolder {
        private static final CallerRunsExecutor INSTANCE = new CallerRunsExecutor();
    }

    public static CallerRunsExecutor getInstance() {
        return CallerRunsExecutor.CallerRunsExecutorHolder.INSTANCE;
    }

    @Override
    public ExecutorType getType() {
        return ExecutorType.CALLER_RUNS_POLICY;
    }
}
