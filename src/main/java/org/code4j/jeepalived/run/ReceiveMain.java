package org.code4j.jeepalived.run;

import org.code4j.jeepalived.bootstrap.ReceiveBootStrap;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 14:27
 */
public class ReceiveMain {

    public static void main(String[] args) {
        System.out.println("配置文件路径："+args[0]);
        ReceiveBootStrap bootStrap = new ReceiveBootStrap();
        bootStrap.startup(args[0]);
    }
}
