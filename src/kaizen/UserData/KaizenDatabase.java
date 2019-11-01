package kaizen.UserData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KaizenDatabase {
    
    
    

    public static Connection conn;

    public static void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:KaizenDatabase.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createUserTable() {
        PreparedStatement createLoginInstance = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Database Table");
            
        }
        
        

    }

    public static void createTasksTable() {

    }

    public static void createTimesheetsTable() {

    }

    public ResultSet getResultSet(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
