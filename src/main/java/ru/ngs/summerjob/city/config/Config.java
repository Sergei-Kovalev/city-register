package ru.ngs.summerjob.city.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_LIMIT = "db.limit";

    private static final Properties properties = new Properties();

    public static synchronized String getProperty(String name) {
        if (properties.isEmpty()) {
            try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("dao.properties")) {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return properties.getProperty(name);
    }
}
