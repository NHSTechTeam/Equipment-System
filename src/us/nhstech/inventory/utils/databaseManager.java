package us.nhstech.inventory.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Devin on 4/9/2016.
 */
public class databaseManager {
    public static boolean syncing = false;

    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://160.153.34.72/equipmentsystem";
            String username = "tech";
            String password = "superce11";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        } return null;
    }

    public static void add(int ref, String name, boolean avail, String user, Boolean perm){
        try{
            Connection conn = getConnection();
            PreparedStatement add = conn.prepareStatement("INSERT INTO equipment (Reference, name, availability, User, permission) VALUES ('"+ref+"', '"+name+"', '"+avail+"', '"+user+"', '"+perm+"')");
            add.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
