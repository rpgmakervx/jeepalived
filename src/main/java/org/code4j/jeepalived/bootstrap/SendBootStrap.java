package org.code4j.jeepalived.bootstrap;

import org.apache.log4j.Logger;
import org.code4j.jeepalived.client.MonitorSend;
import org.code4j.jeepalived.config.Config;
import org.code4j.jeepalived.run.ReceiveMain;
import org.code4j.jeepalived.server.MonitorReceive;

/**
 * Description :
 * Created by code4j on 2016/6/8 0008
 * 13:38
 */
public class SendBootStrap {
    private Logger logger = Logger.getLogger(SendBootStrap.class);

    public void startup(String param){
        logger.debug("configuration file path :"+param);
        new Config(param);
        MonitorSend send = new MonitorSend();
        try {
            send.connect();
//            ReceiveBootStrap bootStrap1 = new ReceiveBootStrap();
//            bootStrap1.startup(param);
            send.listen();
        } catch (Exception e) {
            e.printStackTrace();
            send.closeChannel();
        }
    }
}
