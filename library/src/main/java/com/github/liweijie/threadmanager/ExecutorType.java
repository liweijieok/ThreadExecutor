package com.github.liweijie.threadmanager;

/**
 * 作者：黎伟杰 on 2019/6/19.
 * 邮箱：liweijieok@qq.com
 * description：
 * update by:
 * update day:
 *
 * @author liweijie
 */
public enum ExecutorType {
    /**
     * 无限的
     */
    INFINITE(1),;
    private int type;

    ExecutorType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


}
