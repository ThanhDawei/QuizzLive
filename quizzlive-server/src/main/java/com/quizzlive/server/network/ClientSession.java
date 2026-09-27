package com.quizzlive.server.network;

import com.quizzlive.shared.protocol.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSession implements Runnable {
    private final Socket socket;
    private final SessionManager sessionManager;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String userId;
    private boolean running = true;

    public ClientSession(Socket socket, SessionManager sessionManager) {
        this.socket = socket;
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            while (running) {
                Packet packet = (Packet) input.readObject();
                System.out.println("[Server] Received packet OpCode: " + packet.getOpCode() + " Payload: " + packet.getPayloadJson());
                
                // Echo or handle packet
                sessionManager.broadcast(packet);
            }
        } catch (Exception e) {
            System.out.println("[Server] Session ended for: " + socket.getRemoteSocketAddress());
        } finally {
            sessionManager.removeSession(this);
            close();
        }
    }

    public synchronized void sendPacket(Packet packet) {
        try {
            if (output != null) {
                output.writeObject(packet);
                output.flush();
            }
        } catch (IOException e) {
            System.err.println("[Server] Error sending packet to " + userId + ": " + e.getMessage());
        }
    }

    public void close() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {}
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
