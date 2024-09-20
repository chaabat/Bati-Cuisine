package com.BatiCuisine.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection instance ;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/cuisine";
    private String username = "postgres";
    private String password = "chaabat";

 private DataBaseConnection() throws SQLException{
     try{
         Class.forName("org.postgresql.Driver");
         this.connection = DriverManager.getConnection(url, username ,password);
     }catch (ClassNotFoundException ex){
         throw new SQLException("PostgreSQL Driver not found");
     }
 }

 public Connection getConnection(){
     return connection;
 }

 public static  DataBaseConnection getInstance() throws SQLException{
     if(instance == null){
         instance = new DataBaseConnection();
     }else if (instance.getConnection().isClosed()){
         instance = new DataBaseConnection();
     }
     return instance;
 }
}
