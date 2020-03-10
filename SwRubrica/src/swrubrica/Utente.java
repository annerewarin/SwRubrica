/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swrubrica;

import gui.InputProperties;
import gui.Login;
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
 * @author anne
 */
public class Utente {
    private String username;
    private String password;
    private final static String fileProperties = "database_credenziali.properties";
    private final static String fileSchema = "schema_database.sql";
    private static Connection conn = null;
    
    public Utente(String u, String p){
        this.username = u;
        this.password = p;
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
    
    public String toString(){
        return this.username+";"+this.password;
    }
    
    public static void main(String [] args)throws SQLException{
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream("database_credenziali.properties"));
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String ipserver = properties.getProperty("ipserver");
            String database = properties.getProperty("database");
            String porta = properties.getProperty("porta");
            System.out.println(username+" "+password+" "+database+" "+porta);
            if((username.equals("")) || (password.equals("")) || (ipserver.equals("")) || (database.equals("")) || (porta.equals(""))){
                JOptionPane.showMessageDialog(null, "Input properties da compilare.");
                InputProperties ip = new InputProperties();
                ip.setVisible(true);
            }
            else{
                System.out.println("Input properties validi.");
                String url = "jdbc:postgresql://"+ipserver+":"+porta+"/" + database;
                conn = DriverManager.getConnection("jdbc:postgresql://"+ipserver+":"+porta+"/" + database + "?user=" + username + "&password=" + password);
                System.out.println("Test di connessione avvenuto con successo");
                try{
                    String line;
                    
                    BufferedReader input = new BufferedReader(new FileReader("schema_database.sql"));
                    while((line = input.readLine())!= null){
                        Statement stat = conn.createStatement();
                        stat.executeUpdate(line);
                        //System.out.println(line);
                    }
                    input.close();        
                    }
                    catch(Exception err){
                        JOptionPane.showMessageDialog(null, err.getMessage());
                        properties.setProperty("username", "");
                        properties.setProperty("password", "");
                        properties.setProperty("ipserver", "");
                        properties.setProperty("database", "");
                        properties.setProperty("porta", "");
                        try{
                            properties.store(new FileOutputStream("database_credenziali.properties"), null);
                        }
                        catch(IOException err2){
                            JOptionPane.showMessageDialog(null, err2.getMessage());
                        }
                    }
                    conn.close();
                
                Login l = new Login();
                l.setVisible(true);
            }
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
                       
        }
    }
}
