package me.csxiong.msgbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.csxiong.msgbus.entity.ThreadMode;

/**
 * Created by csxiong on 2018/8/24.
 */

/**
 * 注解的作用
 * 1.可以解释程序含义
 * 2.被其他程序使用
 * 注解的格式:
 * "@注解名" 还可以加参数值
 * 比如
 *
 * @author Administrator
 * @Override 注解 首字母大写 表示重写父类方法
 * @Deprecated 废弃的 过时的 不建议使用的
 * @SuppressWarnings(value="unchecked") 自定义注解:
 * @Target(value={ElementType.METHOD,ElementType.TYPE}) 可以看源文件  TYPE类型里面是枚举类型 里面 有 类 包  成员变量 局部变量 等等类型 可以理解为全部类型
 * @Retention(RetentionPolicy.RUNTIME)自定义一般都选 runtime
 * @Retention 保留  三个 范围  source源文件  class字节码文件 一般变压器用前面2个                     runtime 运行时（通过反射读取）一般自定义用它
 * <p>
 * 注解在哪使用?
 * 可以在package,class,mthod,field等 相等于加了辅助信息   我们可以通过反射机制实现对这些元数据的访问
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnReceiveMsg {
    ThreadMode target() default ThreadMode.MAIN;

    Tag[] tags() default {};
}
