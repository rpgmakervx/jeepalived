package org.code4j.jeepalived.config;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 13:28
 *
 * 初始化各种参数的常量类
 */
public class Init {

    public static final int READ_SECOND = Config.getIntValue(Config.READ_SECOND);
    public static final int WRITE_SECOND = Config.getIntValue(Config.WRITE_SECOND);
    public static final int READ_WRITE_SECOND = Config.getIntValue(Config.READ_WRITE_SECOND);

    public static final int SEND_TO = Config.getIntValue(Config.SEND_TO);

    public static final int WAIT_PING_SECOND = Config.getIntValue(Config.WAIT_PING_SECOND);
    public static final int WAIT_PONG_SECOND =  Config.getIntValue(Config.WAIT_PONG_SECOND);
    public static final int MAX_UNREC_PING_TIMES = Config.getIntValue(Config.MAX_UNREC_PING_TIMES);
    public static final int MAX_UNREC_PONG_TIMES = Config.getIntValue(Config.MAX_UNREC_PONG_TIMES);
    public static final int RECONNECT_SECOND = Config.getIntValue(Config.RECONNECT_SECOND);

    public static final int RECEIVE_PORT = Config.getIntValue(Config.RECEIVE_PORT);
    public static final int SEND_TO_PORT = Config.getIntValue(Config.SEND_TO_PORT);
    public static final String PRIMARY_HOST = Config.getStrValue(Config.PRIMARY_HOST);

    public static final String SET_PRIMARY_IP = Config.getStrValue(Config.SET_PRIMARY_IP);
    public static final String SET_ORIGIN_IP = Config.getStrValue(Config.SET_ORIGIN_IP);


    public static final String PING = "ping\n";
    public static final String PONG = "pong\n";

}
