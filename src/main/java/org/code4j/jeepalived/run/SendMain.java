package org.code4j.jeepalived.run;

import org.code4j.jeepalived.bootstrap.ReceiveBootStrap;
import org.code4j.jeepalived.bootstrap.SendBootStrap;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 01:19
 */
public class SendMain {

    public static void main(String[] args) throws Exception {
//        MonitorClient monitor = new MonitorClient();
//        monitor.connect("127.0.0.1", 6000);
//        monitor.listen();
        SendBootStrap bootStrap = new SendBootStrap();
        bootStrap.startup(args[0]);
    }
}
