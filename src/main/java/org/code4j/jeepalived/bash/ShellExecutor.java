package org.code4j.jeepalived.bash;

import org.code4j.jeepalived.config.Init;

import java.io.IOException;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 17:08
 */
public class ShellExecutor {

    public void execute(String command){
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
