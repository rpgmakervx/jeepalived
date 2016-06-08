package org.code4j.jeepalived.bootstrap;

import org.code4j.jeepalived.client.MonitorSend;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.server.MonitorReceive;

/**
 * Description :
 * Created by code4j on 2016/6/8 0008
 * 13:38
 */
public class SendBootStrap {

    public void startup(String param){
        new Config(param);
        MonitorSend send = new MonitorSend();
        try {
            send.connect();
            send.listen();
        } catch (Exception e) {
            e.printStackTrace();
            send.closeChannel();
        }
    }
}
