package org.code4j.jeepalived;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 15:17
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",6000);
        final PrintStream ps = new PrintStream(socket.getOutputStream());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ps.println("ping");
            }
        },0,5000);
//        ps.close();
//        socket.close();
    }
}
