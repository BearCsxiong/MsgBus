package me.csxiong.msgbus.entity;

import java.util.Observable;
import java.util.Set;

/**
 * Created by csxiong on 2018/8/25.
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
