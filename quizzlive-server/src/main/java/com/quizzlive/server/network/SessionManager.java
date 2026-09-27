package com.quizzlive.server.network;

import com.quizzlive.shared.protocol.Packet;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionManager {
    private final CopyOnWriteArrayList<ClientSession> sessions = new CopyOnWriteArrayList<>();

    public void addSession(ClientSession session) {
        sessions.add(session);
        System.out.println("[SessionManager] Client connected. Total active sessions: " + sessions.size());
    }

    public void removeSession(ClientSession session) {
        sessions.remove(session);
        System.out.println("[SessionManager] Client disconnected. Total active sessions: " + sessions.size());
    }

    public void broadcast(Packet packet) {
        for (ClientSession session : sessions) {
            session.sendPacket(packet);
        }
    }

    public int getActiveCount() {
        return sessions.size();
    }
}
