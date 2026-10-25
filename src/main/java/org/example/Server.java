package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    static String ip = Main.ip;
    static int port = Main.port;
    static Socket superSocket;
    static ServerSocket superServerSocket;
    static PrintWriter out;
    static Input input;

    static void send(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Server started...");
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("ServerSocket created");
        superServerSocket = serverSocket;
        Socket socket = superServerSocket.accept();
        System.out.println("Socket created");
        superSocket = socket;
        PrintWriter printWriter = new PrintWriter(superSocket.getOutputStream(), true);
        out = printWriter;
        Scanner in = new Scanner(superSocket.getInputStream());
        System.out.println("done!");
        while (true){
            System.out.println(in.nextLine());
            input.doAction(in.nextLine());
        }
    }

}
