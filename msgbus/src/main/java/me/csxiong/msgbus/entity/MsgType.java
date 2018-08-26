package me.csxiong.msgbus.entity;

import java.util.Observable;
import java.util.Set;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 消息类型,基于观察者模式触发消息传递
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
public class MsgType extends Observable {

    private Set<Class<?>> msgTypeClasses;

    private String tag;

    public MsgType(Set<Class<?>> msgTypeClasses, String tag) {
        this.msgTypeClasses = msgTypeClasses;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Class<?>> getMsgTypeClasses() {
        return msgTypeClasses;
    }

    public void setMsgTypeClasses(Set<Class<?>> msgTypeClasses) {
        this.msgTypeClasses = msgTypeClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MsgType msgType = (MsgType) o;

        if (msgTypeClasses != null ? !msgTypeClasses.equals(msgType.msgTypeClasses) : msgType.msgTypeClasses != null)
            return false;
        return tag != null ? tag.equals(msgType.tag) : msgType.tag == null;
    }

    @Override
    public int hashCode() {
        int result = msgTypeClasses != null ? msgTypeClasses.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    public void sendMsg(Object obj) {
        setChanged();
        notifyObservers(obj);
    }
}
