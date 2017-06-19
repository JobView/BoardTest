package com.wzf.boardgame.function.eventbus;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-12 18:34
 */

public class SubscriberMethod {
    final Method method;
    final ThreadMode threadMode;
    final Class<?> eventType;

    public SubscriberMethod(Method method, ThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }
}
