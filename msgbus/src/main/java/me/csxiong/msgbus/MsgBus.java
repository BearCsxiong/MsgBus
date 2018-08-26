package me.csxiong.msgbus;

/**
 * -------------------------------------------------------------------------------
 * |
 * | desc : MsgBus目的为了解决最好不要在SDK中集成第三方框架,但是使用一些优秀的框架简便开发流程
 * |
 * |--------------------------------------------------------------------------------
 * | on 2018/8/26 created by csxiong
 * |--------------------------------------------------------------------------------
 * <p>
 * 消息注册流程
 * MsgBus
 * |
 * Bus
 * |
 * register(消息接收者)
 * |                 |               |
 * MsgType        MsgType         MsgType
 * |                 |               |
 * Receiver       Receiver        Receiver
 */

/**
 * 消息发送流程
 * MsgBus
 * |
 * Bus
 * |
 * post(消息)
 * |-解析需要传递此消息的数据类型渠道
 * MsgType        MsgType         MsgType
 * |                 |               |<-------------触发订阅事件源消息改变
 * Receiver       Receiver        Receiver
 * |                 |               |<-------------订阅者触发消息接收者接收消息的方法并按照定义接收方法切换线程
 * obj              obj             obj
 * \                 |               /
 * 所有注册消息接收者方法，接收此消息
 */
public class MsgBus {

    private static MsgBus msgBus;

    private Bus bus;

    public static Bus get() {
        if (msgBus == null) {
            msgBus = new MsgBus();
        }
        return msgBus.bus;
    }

    private MsgBus() {
        bus = new Bus();
    }

}
