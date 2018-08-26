package me.csxiong.msgbus.Scanner;

import java.util.Set;

import me.csxiong.msgbus.entity.MsgType;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 目标扫描者,针对注解的目标扫描者
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
public interface Scanner<T> {

    /**
     * 返回所有可接收消息的消息类型
     *
     * @param t             目标注解
     * @param paramterClazz 接收者内部注册的接收方法的接收参数Class
     * @return
     */
    Set<MsgType> scanAllMsgType(T t, Class<?> paramterClazz);

    /**
     * 返回目标注解Class
     *
     * @return
     */
    Class<T> getScanTarget();

}
