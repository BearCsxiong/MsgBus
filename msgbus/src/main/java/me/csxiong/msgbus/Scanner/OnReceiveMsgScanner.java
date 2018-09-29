package me.csxiong.msgbus.Scanner;

import android.os.Looper;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import me.csxiong.msgbus.annotation.OnReceiveMsg;
import me.csxiong.msgbus.annotation.Tag;
import me.csxiong.msgbus.entity.MsgType;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 针对注解OnReceiveMsg的扫描工具
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
public class OnReceiveMsgScanner implements Scanner<OnReceiveMsg> {

    //缓存所有消息接收者中参数的ParentClasses
    private static final ConcurrentMap<Class<?>, Set<Class<?>>> msgTypeCache = new ConcurrentHashMap<>();

    @Override
    public Set<MsgType> scanAllMsgType(OnReceiveMsg onReceiveMsg, Class<?> paramterClazz) {
        LinkedHashSet<MsgType> msgTypes = new LinkedHashSet<>();
        Set<Class<?>> parentClasses = getParentClasses(paramterClazz);
        Tag[] tags = onReceiveMsg.tags();
        if (tags.length == 0) {
            msgTypes.add(new MsgType(parentClasses, Tag.DEFAULT));
        } else {
            for (Tag tag : tags) {
                msgTypes.add(new MsgType(parentClasses, tag.value()));
            }
        }
        return msgTypes;
    }

    @Override
    public Class<OnReceiveMsg> getScanTarget() {
        return OnReceiveMsg.class;
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static Set<Class<?>> getParentClasses(Class<?> concreteClass) {
        Class<?> exClasses = ex(concreteClass);
        Set<Class<?>> classes = msgTypeCache.get(exClasses);
        if (classes != null) {
            return classes;
        }
        classes = new HashSet<>();
        List<Class<?>> parents = new LinkedList<>();
        parents.add(exClasses);

        while (!parents.isEmpty()) {
            Class<?> clazz = parents.remove(0);
            classes.add(clazz);

            Class<?> parent = clazz.getSuperclass();
            if (parent != null) {
                parents.add(parent);
            }
        }
        msgTypeCache.put(exClasses, classes);
        return classes;
    }

    public static Class<?> ex(Class<?> target) {
        String className = target.getName();
        //->基本类型无法订阅bug
        switch (className) {
            case "byte":
                return Byte.class;
            case "char":
                return Character.class;
            case "int":
                return Integer.class;
            case "short":
                return Short.class;
            case "long":
                return Long.class;
            case "boolean":
                return Boolean.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
        }
        return target;
    }
}
