package org.code4j.jeepalived.test;

import org.code4j.jeepalived.client.MonitorSend;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 01:19
 */
public class ClientMain {

    public static void main(String[] args) throws Exception {
//        MonitorClient monitor = new MonitorClient();
//        monitor.connect("127.0.0.1", 6000);
//        monitor.listen();
        MonitorSend middleware = new MonitorSend();
        middleware.connect();
        middleware.listen();
    }
}
