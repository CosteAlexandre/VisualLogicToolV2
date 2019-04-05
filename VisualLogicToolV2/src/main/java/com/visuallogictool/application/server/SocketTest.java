package com.visuallogictool.application.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SocketTest {

	public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8060);
        System.out.println("Listening for connection on port 8090 ....");
        while (true) {
            try (Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }

    }
	
	
}
