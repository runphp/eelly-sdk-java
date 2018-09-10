package com.eelly.java.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApiOptions {

    public static String OAUTH_SERVER_TOKEN_URL;
    public static String CLIENT_ID; // 应用id CLIENT_ID
    public static String CLIENT_SECRET; // 应用secret CLIENT_SECRET
    /**
     * 各个模块的服务器
     */
    public static Map<String, String> GATEWAY_MAP = new HashMap<String, String>() {
        {
            put("example", "https://api.eelly.com");
            put("user", "https://api.eelly.com");
            put("activity", "https://api.eelly.com");
            put("cart", "https://api.eelly.com");
            put("contact", "https://api.eelly.com");
            put("data", "https://api.eelly.com");
            put("elastic", "https://api.eelly.com");
            put("eellyOldCode", "https://api.eelly.com");
            put("example", "https://api.eelly.com");
            put("goods", "https://api.eelly.com");
            put("im", "https://api.eelly.com");
            put("live", "https://api.eelly.com");
            put("log", "https://api.eelly.com");
            put("logger", "https://logger_api.eelly.com");
            put("message", "https://api.eelly.com");
            put("moments", "https://api.eelly.com");
            put("oauth", "https://api.eelly.com");
            put("order", "https://api.eelly.com");
            put("pay", "https://api.eelly.com");
            put("service", "https://api.eelly.com");
            put("store", "https://api.eelly.com");
            put("system", "https://api.eelly.com");
            put("user", "https://api.eelly.com");
        }
    };

    static {
        InputStream in = ApiOptions.class.getClassLoader().getResourceAsStream("properties/eelly.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            String env = prop.getProperty("APPLICATION_ENV");
            OAUTH_SERVER_TOKEN_URL = prop.getProperty(env.toUpperCase() + "_OAUTH_SERVER_TOKEN_URL");
            CLIENT_ID = prop.getProperty(env.toUpperCase() + "_CLIENT_ID");
            CLIENT_SECRET = prop.getProperty(env.toUpperCase() + "_CLIENT_SECRET");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
