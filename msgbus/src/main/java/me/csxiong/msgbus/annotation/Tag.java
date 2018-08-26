package me.csxiong.msgbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 目标消息接收者,定义此接收方法接收标志
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {
    String DEFAULT = "msgbus_default_tag";

    String value();
}
