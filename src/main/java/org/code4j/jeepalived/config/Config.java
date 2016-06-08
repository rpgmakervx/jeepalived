package org.code4j.jeepalived.config;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 16:20
 */
public class Config {

    private static Configuration configuration ;


    public Config(String configpath){
        configuration = new Configuration(configpath);
    }
//    public Config(String configpath){
//        configuration = Configuration.getConfiguration(configpath);
//    }

    public static final String PRIMARY_HOST = "primary_host";
    public static final String LOCAL_HOST = "local_host";
    public static final String SEND_TO_PORT = "send_to_port";
    public static final String RECEIVE_PORT = "receive_port";

    public static final String SEND_TO = "send_to";

    public static final String READ_SECOND = "read_second";
    public static final String WRITE_SECOND = "write_second";
    public static final String READ_WRITE_SECOND = "read_write_second";

    public static final String WAIT_PING_SECOND = "wait_ping_second";
    public static final String MAX_UNREC_PING_TIMES = "max_unrec_ping_times";


    public static final String WAIT_PONG_SECOND = "wait_pong_second";
    public static final String MAX_UNREC_PONG_TIMES = "max_unrec_pong_times";
    public static final String RECONNECT_SECOND = "reconnect_second";

    public static final String SET_PRIMARY_IP = "set_primary_ip";
    public static final String SET_ORIGIN_IP = "set_origin_ip";
    public static Integer getIntValue(String key){
        return configuration.getIntValue(key);
    }

    public static String getStrValue(String key){
        return configuration.getStrValue(key);
    }
}
