package com.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class SessionCounterListener implements HttpSessionListener {
    private static int totalSessions;
    private static int activeSessions;
    private static Map<String, Long> sessionLastAccessTime = new HashMap<>();
    private static final long IDLE_TIMEOUT = 10 * 60 * 1000; // 10 minutes in milliseconds

    public static int getTotalSessions() {
        return totalSessions;
    }

    public static int getActiveSessions() {
        return activeSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        totalSessions++;
        activeSessions++;
        String sessionId = se.getSession().getId();
        sessionLastAccessTime.put(sessionId, System.currentTimeMillis());
        System.out.println("Session created. Total sessions: " + totalSessions + ", Active sessions: " + activeSessions);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        totalSessions--;
        activeSessions--;
        String sessionId = se.getSession().getId();
        sessionLastAccessTime.remove(sessionId);
        System.out.println("Session destroyed. Total sessions: " + totalSessions + ", Active sessions: " + activeSessions);
    }

    public static void checkIdleTimeout(HttpServletRequest request) {
        long currentTime = System.currentTimeMillis();
        for (String sessionId : sessionLastAccessTime.keySet()) {
            long lastAccessTime = sessionLastAccessTime.get(sessionId);
            if (currentTime - lastAccessTime > IDLE_TIMEOUT) {
                // Session has exceeded idle timeout, invalidate it
                sessionLastAccessTime.remove(sessionId);
                HttpSession session = getSessionById(sessionId, request); // Retrieve session by ID
                if (session != null) {
                    session.invalidate();
                    totalSessions--;
                    activeSessions--;
                    System.out.println("Session idle timeout. Session ID: " + sessionId);
                }
            }
        }
    }

    private static HttpSession getSessionById(String sessionId, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Do not create a new session if it doesn't exist
        if (session != null && sessionId.equals(session.getId())) {
            return session;
        } else {
            return null;
        }
    }
}
