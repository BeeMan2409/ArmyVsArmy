package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    static String ip = Main.ip;
    static int port = Main.port;
    static Socket superSocket;
    static PrintWriter out;

    static void send(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    static public void main(String args[]) throws IOException, InterruptedException {
        Socket socket = new Socket(ip, port);
        superSocket = socket;
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        out = printWriter;
        while (true){

        }
    }

}
