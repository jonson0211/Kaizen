package kaizen.UserData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
        PreparedStatement createUserTable = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Database Table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "LOGIN", null);
            if (!rs.next()) {
                createUserTable = conn.prepareStatement("CREATE TABLE LOGIN (USERNAME VARCHAR(150), PASSWORD VARCHAR(150))");
                createUserTable.execute();
                System.out.println("User table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO LOGIN(USERNAME,PASSWORD) "
                        + "VALUES ('Kaizen','Innovations')");
                createDemoInstance.execute();
            } else {
                System.out.println("LOGIN table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        
        

    

    public static void createTasksTable() {

    }

    public static void createTimesheetsTable() {
        PreparedStatement createTimesheetsTable = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Timesheets table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "MUSICLIST", null);
            if (!rs.next()) {
                createTimesheetsTable = conn.prepareStatement("CREATE TABLE TIMESHEETS (CATID INT PRIMARY KEY IDENTITY, CATNAME VARCHAR(50), START TIME, END TIME, DESCR VARCHAR(300))");
                createTimesheetsTable.execute();
                System.out.println("Timesheets table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATNAME,START,END,DESCR) "
                        + "VALUES ('Work', , NOT NULL,'Today I had a productive day at the office!'), "
                        );
                createDemoInstance.execute();
            } else {
                System.out.println("Timesheets table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    }

    public ResultSet getResultSet(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
