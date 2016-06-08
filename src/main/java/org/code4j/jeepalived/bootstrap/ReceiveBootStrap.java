package org.code4j.jeepalived.bootstrap;

import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.server.MonitorReceive;

/**
 * Description :
 * Created by code4j on 2016/6/8 0008
 * 13:38
 */
public class ReceiveBootStrap {

    public void startup(String param){
        new Config(param);
        MonitorReceive receive = new MonitorReceive();
        receive.startup();
    }
}
