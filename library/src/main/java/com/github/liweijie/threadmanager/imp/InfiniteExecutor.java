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
public class InfiniteExecutor extends AbstractExecutor {
    private InfiniteExecutor() {
    }

    private static class InfiniteExecutorHolder {
        private static final InfiniteExecutor INSTANCE = new InfiniteExecutor();
    }

    public static InfiniteExecutor getInstance() {
        return InfiniteExecutor.InfiniteExecutorHolder.INSTANCE;
    }

    @Override
    public ExecutorType getType() {
        return ExecutorType.INFINITE;
    }
}
