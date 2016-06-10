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

    public static final String TARGET_HOST = "target_host";
    public static final String SEND_TO_PORT = "send_to_port";
    public static final String RECEIVE_PORT = "receive_port";

    public static final String SEND_TO = "send_to";

    public static final String READ_SECOND = "read_second";
    public static final String WRITE_SECOND = "write_second";
    public static final String READ_WRITE_SECOND = "read_write_second";

    public static final String MAX_UNREC_PING_TIMES = "max_unrec_ping_times";


    public static final String MAX_UNREC_PONG_TIMES = "max_unrec_pong_times";
    public static final String RECONNECT_SECOND = "reconnect_second";

    public static final String SET_SLAVE_IP = "set_slave_ip";
    public static final String SET_MASTER_IP = "set_master_ip";

    public static final String SHUTDOWN_NETWORK = "shutdown_network";
    public static final String STARTUP_NETWORK = "startup_network";

    public static Integer getIntValue(String key){
        return configuration.getIntValue(key);
    }

    public static String getStrValue(String key){
        return configuration.getStrValue(key);
    }
}
