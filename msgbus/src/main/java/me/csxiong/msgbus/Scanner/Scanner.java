package me.csxiong.msgbus.Scanner;

import java.util.Set;

import me.csxiong.msgbus.entity.MsgType;

/**
 * Created by csxiong on 2018/8/25.
 */

public interface Scanner<T> {

    Set<MsgType> scanAllMsgType(T t, Class<?> paramterClazz);

    Class<T> getScanTarget();

}
