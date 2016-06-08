package org.code4j.jeepalived.bootstrap;

import org.apache.log4j.Logger;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.run.ReceiveMain;
import org.code4j.jeepalived.server.MonitorReceive;

/**
 * Description :
 * Created by code4j on 2016/6/8 0008
 * 13:38
 */
public class ReceiveBootStrap {
    private Logger logger = Logger.getLogger(ReceiveBootStrap.class);
    public void startup(String param){
        logger.debug("配置文件路径："+param);
        new Config(param);
        MonitorReceive receive = new MonitorReceive();
        receive.startup();
    }
}
