package kaizen.UserData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("Checking LOGIN Table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "LOGIN", null);
            if (!rs.next()) {
                createUserTable = conn.prepareStatement("CREATE TABLE LOGIN (USERNAME VARCHAR(150), PASSWORD VARCHAR(150))");
                createUserTable.execute();
                System.out.println("User table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO LOGIN(USERNAME,PASSWORD) "
                        + "VALUES ('lienzhu','blairwangisbae')");
                createDemoInstance.execute();
            } else {
                System.out.println("LOGIN table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTasksTable() {
        PreparedStatement createTasksTable = null;
        PreparedStatement createDummyTasks = null;
        ResultSet rs = null;
        openConnection();
        try {
            //Creating table if not exists
            System.out.println("Checking TASKS Table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "TASKS", null);
            if (!rs.next()) {
                createTasksTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS TASKS ("
                        + " TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ", USERNAME TEXT NOT NULL"
                        + ", TITLE TEXT NOT NULL"
                        + ", DESCRIPTION TEXT NOT NULL"
                        + ", DO_DATE TEXT NOT NULL"
                        + ", DUE_DATE TEXT NOT NULL"
                        + ", PRIORITY TEXT NOT NULL"
                        + ", FOREIGN KEY (USERNAME)"
                        + " REFERENCES USER(USERNAME)"
                        + ");");
                createTasksTable.execute();
                System.out.println("TASKS table created");

                //Insert dummy data 1
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Finish ACF homework', "
                        + "'Finish the weekly homework for ACF Topic 7', "
                        + "15/11/2019, "
                        + "18/11/2019, "
                        + "'LOW'"
                        + ");");
                createDummyTasks.execute();
                
                //Insert dummy data 2
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'ACF Call', "
                        + "'Discuss with the team on how we will handle presentation', "
                        + "02/11/2019, "
                        + "02/11/2019, "
                        + "'URGENT'"
                        + ");");
                createDummyTasks.execute();
            } else {
                System.out.println("TASKS table already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void createTimesheetsTable() {

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

    public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery(sqlstatement);
        return RS;
    }
}
