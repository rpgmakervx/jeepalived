package org.code4j.jeepalived.test;

import org.code4j.jeepalived.server.MonitorReceive;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 14:27
 */
public class ServerMain {

    public static void main(String[] args) {
        new MonitorReceive().startup();
    }
}
