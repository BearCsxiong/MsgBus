package me.csxiong.msgbus.entity;


/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : 渠道,针对传入消息类型和对应接收者而定义
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 */
public class Channel {

    private MsgType msgType;

    private Receiver receiver;

    public Channel(MsgType msgType, Receiver receiver) {
        this.msgType = msgType;
        this.receiver = receiver;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        if (msgType != null ? !msgType.equals(channel.msgType) : channel.msgType != null)
            return false;
        return receiver != null ? receiver.equals(channel.receiver) : channel.receiver == null;
    }

    @Override
    public int hashCode() {
        int result = msgType != null ? msgType.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        return result;
    }
}
