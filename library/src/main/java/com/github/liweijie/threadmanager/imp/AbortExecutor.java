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
public class AbortExecutor extends AbstractExecutor {

    private AbortExecutor() {
    }

    private static class AbortExecutorHolder{
        private static final AbortExecutor INSTANCE = new AbortExecutor();
    }

    public static AbortExecutor getInstance() {
        return AbortExecutorHolder.INSTANCE;
    }

    @Override
    public ExecutorType getType() {
        return ExecutorType.INFINITE;
    }
}
