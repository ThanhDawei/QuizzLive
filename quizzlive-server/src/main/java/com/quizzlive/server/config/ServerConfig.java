package com.quizzlive.server.config;

import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
    private static int port = 8888;
    private static int threadPoolSize = 20;
    private static boolean sslEnabled = false;
    private static String keyStorePath = "";
    private static String keyStorePassword = "";

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = ServerConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                port = Integer.parseInt(prop.getProperty("server.port", "8888"));
                threadPoolSize = Integer.parseInt(prop.getProperty("server.thread-pool-size", "20"));
                sslEnabled = Boolean.parseBoolean(prop.getProperty("server.ssl.enabled", "false"));
                keyStorePath = prop.getProperty("server.ssl.key-store-path", "");
                keyStorePassword = prop.getProperty("server.ssl.key-store-password", "");
            }
        } catch (Exception e) {
            System.out.println("[ServerConfig] Could not load application.properties, using defaults.");
        }
    }

    public static int getPort() { return port; }
    public static int getThreadPoolSize() { return threadPoolSize; }
    public static boolean isSslEnabled() { return sslEnabled; }
    public static String getKeyStorePath() { return keyStorePath; }
    public static String getKeyStorePassword() { return keyStorePassword; }
}
