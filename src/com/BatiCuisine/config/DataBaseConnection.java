package com.BatiCuisine.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static DataBaseConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DataBaseConnection() throws SQLException {
        try {
            // Charger les propriétés depuis le classpath
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/BatiCuisine/ressources/application.properties");

            if (inputStream == null) {
                throw new RuntimeException("Fichier application.properties introuvable.");
            }

            properties.load(inputStream);
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");

            // Charger le driver PostgreSQL
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | IOException ex) {
            throw new SQLException("Erreur de chargement des configurations ou du driver PostgreSQL.", ex);
        }
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }

    // Singleton pour garantir une seule instance de la connexion
    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DataBaseConnection();
        }
        return instance;
    }
}
