package com.wzf.boardgame.function.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-12 18:27
 */

public class EventBus {
    private Map<Object, CopyOnWriteArrayList<SubscriberMethod>> subscriptionMap;
    private Handler handler;
    private ExecutorService executorService;
    private static EventBus instance;

    private EventBus() {
        subscriptionMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object subscription) {
        subscriptionMap.put(subscription, getSubscriptionMethod(subscription));
    }

    public void post(Object event) {
        Set<Map.Entry<Object, CopyOnWriteArrayList<SubscriberMethod>>> entries = subscriptionMap.entrySet();
        Iterator<Map.Entry<Object, CopyOnWriteArrayList<SubscriberMethod>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, CopyOnWriteArrayList<SubscriberMethod>> next = iterator.next();
            CopyOnWriteArrayList<SubscriberMethod> value = next.getValue();
            for (SubscriberMethod subscriberMethod : value) {
                if (subscriberMethod.getEventType().isAssignableFrom(event.getClass())) {//判断是不是同一个类
                    changeThread(next.getKey(), subscriberMethod.getMethod(), subscriberMethod.getThreadMode(), event);
                }
            }
        }

    }


    public void unRegister(Object subscription) {
        subscriptionMap.remove(subscription);
    }

    private void changeThread(final Object key, final Method method, ThreadMode threadMode, final Object event) {
        switch (threadMode) {
            case POSTING:
                invokeMethod(key, method, event);
                break;
            case MAIN:
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    invokeMethod(key, method, event);
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(key, method, event);
                        }
                    });
                }
                break;
            case BACKGROUND:
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    invokeMethod(key, method, event);
                } else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(key, method, event);
                        }
                    });
                }
                break;
        }
    }

    private void invokeMethod(Object key, Method method, Object event) {
        try {
            method.invoke(key, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private CopyOnWriteArrayList<SubscriberMethod> getSubscriptionMethod(Object subscription) {
        CopyOnWriteArrayList<SubscriberMethod> subMethods = new CopyOnWriteArrayList<>();
        Class<?> subClass = subscription.getClass();
        Method[] methods = subClass.getDeclaredMethods();
        while (subClass != null) {
            String className = subClass.getName();
            if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("android.")) {
                break;
            }
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }

                Class<?>[] params = method.getParameterTypes();
                if (params == null || params.length != 1) {
                    throw new RuntimeException("eventbus params must be one parameter");
                }

                subMethods.add(new SubscriberMethod(method, subscribe.threadMode(), params[0]));
            }
            subClass = subClass.getSuperclass();
        }
        return subMethods;
    }
}
