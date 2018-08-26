package me.csxiong.msgbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import me.csxiong.msgbus.Scanner.OnReceiveMsgScanner;
import me.csxiong.msgbus.Scanner.Scanner;
import me.csxiong.msgbus.annotation.OnReceiveMsg;
import me.csxiong.msgbus.annotation.Tag;
import me.csxiong.msgbus.entity.Channel;
import me.csxiong.msgbus.entity.MsgType;
import me.csxiong.msgbus.entity.Receiver;
import me.csxiong.msgbus.entity.ThreadMode;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 具体MsgBus执行者,以及使用方法
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
public class Bus {

    private Scanner<OnReceiveMsg> scanner;

    public Bus() {
        this.scanner = new OnReceiveMsgScanner();
    }

    private static final ConcurrentMap<MsgType, Set<Receiver>> msg2Receivers = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Object, Set<Channel>> channels = new ConcurrentHashMap<>();

    /**
     * 注册接收者消息事件
     *
     * @param obj 消息接收者
     */
    public void register(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("无法为空对象注册");
        }
        for (Object _obj : channels.keySet()) {
            if (_obj == obj) {
                throw new IllegalArgumentException("该对象已注册!");
            }
        }

        Class<?> receiverClazz = obj.getClass();
        boolean enableReceive = false;
        LinkedHashSet<Channel> channels = new LinkedHashSet<>();
        for (Method method : receiverClazz.getDeclaredMethods()) {
            OnReceiveMsg onReceiveMsg = method.getAnnotation(scanner.getScanTarget());
            if (onReceiveMsg != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("方法:" + method.getName() + "接收参数必须且只能接受一个参数");
                }
                Class<?> parameterClazz = parameterTypes[0];
                if (parameterClazz.isInterface()) {
                    throw new IllegalArgumentException("方法:" + method.getName() + "接收参数类型不可为interface类型");
                }
                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException("方法:" + method.getName() + "接收方法修饰->public");
                }
                method.setAccessible(true);
                enableReceive = true;
                //线程中
                ThreadMode threadMode = onReceiveMsg.target();
                Set<MsgType> msgTypes = scanner.scanAllMsgType(onReceiveMsg, parameterTypes[0]);
                for (MsgType msgType : msgTypes) {
                    Receiver receiver = new Receiver(threadMode, obj, method);
                    if (!msg2Receivers.containsKey(msgType)) {
                        LinkedHashSet<Receiver> receivers = new LinkedHashSet<>();
                        receivers.add(receiver);
                        msgType.addObserver(receiver);
                        msg2Receivers.put(msgType, receivers);
                        channels.add(new Channel(msgType, receiver));
                        continue;
                    }
                    for (MsgType _msgType : msg2Receivers.keySet()) {
                        if (!msgType.equals(_msgType)) {
                            continue;
                        }
                        Set<Receiver> receivers = msg2Receivers.get(_msgType);
                        receivers.add(receiver);
                        _msgType.addObserver(receiver);
                        channels.add(new Channel(_msgType, receiver));
                    }
                }
            }
        }

        if (!enableReceive) {
            throw new IllegalArgumentException(obj.getClass().getName() + "是否接提供接收方法?");
        }
        Bus.channels.put(obj, channels);

    }

    /**
     * 判断此消息接收者是否接收此消息
     *
     * @param obj 消息接收者
     * @return 此消息接收者是否已注册MsgBus true 已注册 false 未注册
     */
    public boolean isRegister(Object obj) {
        for (Object _obj : channels.keySet()) {
            if (_obj == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * 注销接收者消息事件
     *
     * @param obj 消息接收者
     */
    public void unregister(Object obj) {
        //TODO 解绑
        Set<Channel> receivers = null;
        for (Object _obj : channels.keySet()) {
            if (_obj == obj) {
                receivers = channels.remove(_obj);
                break;
            }
        }
        if (receivers == null) {
            throw new IllegalArgumentException("检查是否已注册?");
        }

        for (Channel channel : receivers) {
            channel.getMsgType().deleteObserver(channel.getReceiver());
            Set<Receiver> _receivers = msg2Receivers.get(channel.getMsgType());
            _receivers.remove(channel.getReceiver());
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void post(Object msg) {
        post(msg, Tag.DEFAULT);
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     * @param tag 标记
     */
    public void post(Object msg, String tag) {
        if (msg == null) {
            throw new IllegalArgumentException("发送的消息不能为空");
        }
        Class<?> msgClass = msg.getClass();
        Set<Class<?>> parentClasses = OnReceiveMsgScanner.getParentClasses(msgClass);
        MsgType msgType = new MsgType(parentClasses, tag);
        for (MsgType _msgType : msg2Receivers.keySet()) {
            if (msgType.getMsgTypeClasses().containsAll(_msgType.getMsgTypeClasses()) && msgType.getTag().equals(_msgType.getTag())) {
                _msgType.sendMsg(msg);
            }
        }
    }

}
