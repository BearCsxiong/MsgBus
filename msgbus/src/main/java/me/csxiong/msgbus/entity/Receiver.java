package me.csxiong.msgbus.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import me.csxiong.msgbus.ExPoster;
import me.csxiong.msgbus.Scanner.OnReceiveMsgScanner;

/**
 * Created by csxiong on 2018/8/25.
 */

public class Receiver implements Observer {

    @Override
    public void update(Observable observable, final Object o) {
        //需要切换线程则对应切换线程
        if (threadMode.equals(ThreadMode.MAIN) && !OnReceiveMsgScanner.isMainThread()) {
            ExPoster.getInstance().runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    receiveMsg(o);
                }
            });
        } else if (threadMode.equals(ThreadMode.BACKGROUND) && OnReceiveMsgScanner.isMainThread()) {
            ExPoster.getInstance().runOnBackground(new Runnable() {
                @Override
                public void run() {
                    receiveMsg(o);
                }
            });
        } else {
            //若要求和实际所在线程都相同线程
            receiveMsg(o);
        }
    }

    private ThreadMode threadMode;

    private Object methodHolder;

    private Method targetMethod;

    public Receiver(ThreadMode threadMode, Object methodHolder, Method targetMethod) {
        this.threadMode = threadMode;
        this.methodHolder = methodHolder;
        this.targetMethod = targetMethod;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Object getMethodHolder() {
        return methodHolder;
    }

    public void setMethodHolder(Object methodHolder) {
        this.methodHolder = methodHolder;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public void receiveMsg(Object obj) {
        try {
            targetMethod.invoke(methodHolder, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
