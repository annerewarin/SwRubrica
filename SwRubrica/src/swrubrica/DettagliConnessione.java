/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swrubrica;

import gui.InputProperties;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author babya
 */
public class DettagliConnessione {
    private String username;
    private String password;
    private String url;
    private static String fileProperties = "database_credenziali.properties";
    private static Connection conn = null;
    
    public DettagliConnessione(){
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void settaDettagli(){
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream("database_credenziali.properties"));
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            String ipserver = properties.getProperty("ipserver");
            String database = properties.getProperty("database");
            String porta = properties.getProperty("porta");
            url = "jdbc:postgresql://"+ipserver+":"+porta+"/" + database;
            System.out.println(username+" "+password+" "+ipserver+" "+database+" "+porta);
            this.setUsername(username);
            this.setPassword(password);
            this.setUrl(url);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }
    }
}
