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
public class DiscardOldest extends AbstractExecutor {
    private DiscardOldest() {
    }

    private static class DiscardOldestHolder {
        private static final DiscardOldest INSTANCE = new DiscardOldest();
    }

    public static DiscardOldest getInstance() {
        return DiscardOldest.DiscardOldestHolder.INSTANCE;
    }

    @Override
    public ExecutorType getType() {
        return ExecutorType.DISCARD_OLDEST_POLICY;
    }
}
