package com.github.liweijie.threadmanager.imp;

import com.github.liweijie.threadmanager.AbstractExecutor;

import domain.ExecutorType;

/**
 * 作者：黎伟杰 on 2019/7/7.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public class DiscardExecutor extends AbstractExecutor {
    private DiscardExecutor() {
    }

    private static class DiscardExecutorHolder {
        private static final DiscardExecutor INSTANCE = new DiscardExecutor();
    }

    public static DiscardExecutor getInstance() {
        return DiscardExecutor.DiscardExecutorHolder.INSTANCE;
    }

    @Override
    public ExecutorType getType() {
        return ExecutorType.DISCARD_POLICY;
    }
}
