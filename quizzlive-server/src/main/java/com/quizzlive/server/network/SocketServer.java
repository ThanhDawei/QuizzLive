package com.quizzlive.server.network;

import com.quizzlive.server.config.ServerConfig;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private final int port;
    private final SessionManager sessionManager = new SessionManager();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(ServerConfig.getThreadPoolSize());
    private volatile boolean isRunning = false;

    public SocketServer(int port) {
        this.port = port;
    }

    public void start() {
        isRunning = true;
        try {
            ServerSocketFactory factory;
            if (ServerConfig.isSslEnabled()) {
                System.out.println("[Quizzlive Server] Enabling SSL/TLS Encryption...");
                SSLContext sslContext = SSLContextFactory.createSSLContext(
                        ServerConfig.getKeyStorePath(),
                        ServerConfig.getKeyStorePassword()
                );
                factory = sslContext.getServerSocketFactory();
            } else {
                factory = ServerSocketFactory.getDefault();
            }

            try (ServerSocket serverSocket = factory.createServerSocket(port)) {
                System.out.println("[Quizzlive Server] Started listening on port " + port 
                        + " (SSL: " + ServerConfig.isSslEnabled() + ")");

                while (isRunning) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[Quizzlive Server] Accepted connection from: " + clientSocket.getRemoteSocketAddress());

                    ClientSession session = new ClientSession(clientSocket, sessionManager);
                    sessionManager.addSession(session);
                    threadPool.execute(session);
                }
            }
        } catch (Exception e) {
            System.err.println("[Quizzlive Server] Server error: " + e.getMessage());
        } finally {
            stop();
        }
    }

    public void stop() {
        isRunning = false;
        threadPool.shutdown();
        System.out.println("[Quizzlive Server] Stopped.");
    }
}
