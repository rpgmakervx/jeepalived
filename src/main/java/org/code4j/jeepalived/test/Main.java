package org.code4j.jeepalived.test;

import org.code4j.jeepalived.server.MonitorServer;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 14:27
 */
public class Main {

    public static void main(String[] args) {
        new MonitorServer().startup(6000);
    }
}
