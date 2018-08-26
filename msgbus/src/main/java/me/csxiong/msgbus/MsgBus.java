package me.csxiong.msgbus;


/**
 * BusHolder
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
