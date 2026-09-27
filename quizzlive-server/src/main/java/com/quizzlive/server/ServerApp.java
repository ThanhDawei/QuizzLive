package com.quizzlive.server;

import com.quizzlive.server.config.ServerConfig;
import com.quizzlive.server.network.SocketServer;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      QUIZZLIVE SERVER - STARTING        ");
        System.out.println("=========================================");

        SocketServer server = new SocketServer(ServerConfig.getPort());
        server.start();
    }
}
