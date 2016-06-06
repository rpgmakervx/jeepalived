package org.code4j.jeepalived;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Description :
 * Created by code4j on 2016/6/6 0006
 * 15:17
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",6000);
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println("!!");
        ps.flush();
        ps.close();
        socket.close();
    }
}
