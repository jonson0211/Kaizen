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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

     public ResultSet getResultSet(String sqlstatement) throws SQLException {
        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery(sqlstatement);
        return RS;
    }
     
     public void insertStatement(String insert_query) throws SQLException {
        java.sql.Statement stmt = null;
        openConnection();
        try {
            System.out.println("database opened");
            stmt = conn.createStatement();
            System.out.println("the query was: " + insert_query);
            stmt.executeUpdate(insert_query);
            stmt.close();
        } catch(Exception e){
            System.err.println(e.getClass().getName() + ": " +e.getMessage());
            System.exit(0);
        }
        stmt.close();
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
                createUserTable = conn.prepareStatement("CREATE TABLE LOGIN ("
                        + "FNAME TEXT NOT NULL,"
                        + "LNAME TEXT NOT NULL,"
                        + "USERNAME TEXT PRIMARY KEY NOT NULL,"
                        + "PASSWORD TEXT NOT NULL)");
                createUserTable.execute();
                System.out.println("User table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO LOGIN(FNAME, LNAME, USERNAME, PASSWORD) "
                        + "VALUES ('Lien', 'Zhu', 'lienzhu','blairwangisbae')");
                createDemoInstance.execute();
            } else {
                System.out.println("LOGIN table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createCategoryTable() {
        PreparedStatement createCategoryTable = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking CATEGORY Table");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "CATEGORY", null);
            if (!rs.next()) {
                createCategoryTable = conn.prepareStatement("CREATE TABLE CATEGORY ("
                        + "CATEGORYNAME TEXT PRIMARY KEY"                        
                        +");");
                createCategoryTable.execute();
                System.out.println("CATEGORY table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYNAME) "
                        + "VALUES ('Work')");
                createDemoInstance.execute();
            } else {
                System.out.println("CATEGORY table exists");
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
                        + ", CATEGORYNAME TEXT NOT NULL"
                        
                        + ", DESCRIPTION TEXT NOT NULL"
                        + ", DO_DATE TEXT NOT NULL"
                        + ", DUE_DATE TEXT NOT NULL"
                        + ", PRIORITY TEXT NOT NULL"    
                        + ", FOREIGN KEY (USERNAME)"
                        + " REFERENCES USER(USERNAME)"
                        + ", FOREIGN KEY (CATEGORYNAME)"
                        + " REFERENCES CATEGORY(CATEGORYNAME)"
                        + ");");
                createTasksTable.execute();
                System.out.println("TASKS table created");

                //Insert dummy data 1
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Finish ACF homework', "
                        + "'Work',"
                        + "'Finish the weekly homework for ACF Topic 7', "
                        + "15/11/2019, "
                        + "18/11/2019, "
                        + "'LOW'"
                        + ");");
                createDummyTasks.execute();
                
                //Insert dummy data 2
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'ACF Call', "
                        + "'Work',"
                        + "'Discuss with the team on how we will handle presentation', "
                        + "02/11/2019, "
                        + "02/11/2019, "
                        + "'URGENT'"
                        + ");");
                createDummyTasks.execute();
                
                
                //insert dummy data 3
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Watch new My Hero Academia new episode', "
                        + "'Relaxation',"
                        + "'Watch new episode 4 of My Hero Academia season 4', "
                        + "07/11/2019, "
                        + "011/11/2019, "
                        + "'LOW'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 4
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Decide on movie for date', "
                        + "'Relationships',"
                        + "'Decide on what movie to watch for date with Kara on 05/11', "
                        + "02/11/2019, "
                        + "04/11/2019, "
                        + "'HIGH'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 5
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Apply for vacationer intern position', "
                        + "'Work',"
                        + "'Apply for the vacationer program in the Product & Tech division of Super Bank', "
                        + "02/11/2019, "
                        + "020/11/2019, "
                        + "'MEDIUM'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 6
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Learn how to code', "
                        + "'Projects',"
                        + "'Self learn the basics of Java coding', "
                        + "06/11/2019, "
                        + "17/11/2019, "
                        + "'MEDIUM'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 7
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Finish Zelda', "
                        + "'Relaxation',"
                        + "'Finish the game The Legend of Zelda: Breath of the Wild on Nintendo Switch', "
                        + "03/11/2019, "
                        + "29/11/2019, "
                        + "'LOW'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 8
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Form oztag group', "
                        + "'Wellness',"
                        + "'Gather people who are interested in forming an oztag team', "
                        + "05/11/2019, "
                        + "30/11/2019, "
                        + "'MEDIUM'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 9
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Organise Interfaculty Party', "
                        + "'Work',"
                        + "'Plan and organise the logistics of the Annual Interfactulty Student Society Party ', "
                        + "08/11/2019, "
                        + "18/11/2019, "
                        + "'HIGH'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 10
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Shopping for groceries', "
                        + "'Daily',"
                        + "'Shopping for eggs, milk, bread, lettuce', "
                        + "03/11/2019, "
                        + "03/11/2019, "
                        + "'HIGH'"
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
        PreparedStatement createTimesheetsTable = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Timesheets table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "TIMESHEETS", null);
            if (!rs.next()) {
                createTimesheetsTable = conn.prepareStatement("CREATE TABLE TIMESHEETS ("
                        + "CATEGORYNAME TEXT,"
                        + "START TEXT, " //integer
                        + "END TEXT, " //integer
                        + "DURATION INTEGER," 
                        + "DESCR TEXT,"
                        + "FOREIGN KEY (CATEGORYNAME)"
                        + "REFERENCES CATEGORY(CATEGORYNAME)"
                        + ");");
                createTimesheetsTable.execute();
                System.out.println("Timesheets table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR) "
                        + "VALUES ('Work', "
                        + "'540',"
                        + "'600',"
                        + "'0',"
                        + " 'Today I had a productive day at the office!'"
                        + ");"
                        );
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR) "
                        + "VALUES ('Work', "
                        + "'120',"
                        + "'360',"
                        + "'240',"
                        + " 'Today I had a productive day at the office launch!'"
                        + ");"
                        );
                
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR)" 
                        + "VALUES ('Relaxation',"
                        + "'660',"
                        + "'780',"
                        + "'120',"
                        + "'Today I played League with my friends'"
                        + ");"
                        );
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR)"
                + "VALUES('Wellness',"
                + "'1020',"
                + "'1140',"
                + "'120',"
                + "'I ran with to the park with my dog'"
                + ");"
                );
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR)"
                + "VALUES('Projects',"
                + "'1020',"
                + "'1140',"
                + "'120',"
                + "'I completed my painting of my 2D girlfriend!'"
                + ");"
                );
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR)"
                + "VALUES('Daily',"
                + "'1020',"
                + "'1140',"
                + "'120',"
                + "'I went to Woolies to buy some groceries'"
                + ");"
                );
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,START,END,DURATION,DESCR)"
                + "VALUES('Relationships',"
                + "'1020',"
                + "'1140',"
                + "'120',"
                + "'I had dinner with my family tonight'"
                + ");"
                );
                
            } else {
                System.out.println("Timesheets table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public static void createDailyLearningsTable() {
        PreparedStatement createDailyLearningsTable = null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Learnings table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "LEARNINGS", null);
            if (!rs.next()) {
                createDailyLearningsTable = conn.prepareStatement("CREATE TABLE IF NOT EXISTS LEARNINGS (USERNAME TEXT NOT NULL "
                        + ", ENTRY_DATE TEXT NOT NULL"
                        + ", DID_WELL TEXT NOT NULL "
                        + ", BE_BETTER TEXT NOT NULL "
                        + ");");
                createDailyLearningsTable.execute();
                System.out.println("Daily Learnings table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu', TO_DATE('03/11/2019','DDMMYYY'), 'Today I spent 40 minutes exercising intensely', "
                        + "'Today I didn't watch the newest episode of MHA...');");
                createDemoInstance.execute();
            } else {
                System.out.println("Daily Learnings table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
}
    
    
