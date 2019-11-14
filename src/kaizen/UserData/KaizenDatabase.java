package kaizen.UserData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class KaizenDatabase {

    public static Connection conn;

    public static Connection openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:KaizenDatabase.db");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return conn;
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
                        + "CATEGORYID STRING PRIMARY KEY,"
                        + "CATEGORYNAME TEXT,"
                        + "COLOUR TEXT"                        
                        +");");
                createCategoryTable.execute();
                System.out.println("CATEGORY table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ('"
                        + "1',"
                        + "'Work',"
                        + "'RED')"
                        //+ "'#80bfff')"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ('"
                        + "2',"
                        + "'Wellness',"
                        +"'GREEN')"
                        //+ "'#80ff80')"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ('"
                        + "3',"
                        + "'Relationships',"
                        + "'BLUE'"
                        //+ "'#cc99ff'"
                        + ")");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ('"
                        + "4',"
                        + "'Projects',"
                        +"'YELLOW'"
                        //+ "'#ccffff'"
                        + ")");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ("
                        + "'5',"
                        + "'Daily',"
                        +"'MAGENTA'"
                        //+ "'#ff80ff'"
                        + ")");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO CATEGORY(CATEGORYID,CATEGORYNAME, COLOUR) "
                        + "VALUES ("
                        + "'6',"
                        + "'Relaxation',"
                        +"'PURPLE'"
                        //+ "'#ffb84d'"
                        + ")");
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
        PreparedStatement dropTable = null;
        
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
                        + "'2019-11-18', "
                        + "'2019-11-19',"
                        + "'70'"
                        + ");");
                createDummyTasks.execute();
                
                //Insert dummy data 2
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'ACF Call', "
                        + "'Work',"
                        + "'Discuss with the team on how we will handle presentation', "
                        + "'2019-11-18', "
                        + "'2019-11-18', "
                        + "'100'"
                        + ");");
                createDummyTasks.execute();
                
                
                //insert dummy data 3
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Watch new My Hero Academia new episode', "
                        + "'Relaxation',"
                        + "'Watch new episode 4 of My Hero Academia season 4', "
                        + "'2019-11-19', "
                        + "'2019-11-19', "
                        + "'20'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 4
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Decide on movie for date', "
                        + "'Relationships',"
                        + "'Decide on what movie to watch for date with Kara on her birthday', "
                        + "'2019-11-20', "
                        + "'2019-11-23', "
                        + "'40'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 5
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Apply for Microsoft', "
                        + "'Work',"
                        + "'Apply for an internship with Microsoft', "
                        + "'2019-11-21', "
                        + "'2019-11-25', "
                        + "'70'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 6
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Learn how to code', "
                        + "'Projects',"
                        + "'Self learn the basics of Java coding', "
                        + "'2019-11-22', "
                        + "'2019-11-25', "
                        + "'80'"
                        + ");");
                createDummyTasks.execute();
                
                //insert dummy data 7
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Finish Zelda', "
                        + "'Relaxation',"
                        + "'Finish the game The Legend of Zelda: Breath of the Wild on Nintendo Switch', "
                        + "'2019-11-23', "
                        + "'2019-11-25', "
                        + "'10'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 8
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Form Oztag group', "
                        + "'Wellness',"
                        + "'Gather people who are interested in forming an Oztag team', "
                        + "'2019-11-18', "
                        + "'2019-11-25', "
                        + "'40'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 9
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Organise Interfaculty Party', "
                        + "'Work',"
                        + "'Plan and organise the logistics of the Annual Interfactulty Student Society Party ', "
                        + "'2019-11-23', "
                        + "'2019-11-24', "
                        + "'60'"
                        + ");");
                createDummyTasks.execute();
                
                 //insert dummy data 10
                createDummyTasks = conn.prepareStatement("INSERT INTO TASKS (USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY) "
                        + " VALUES ("
                        + "'lienzhu', "
                        + "'Shopping for groceries', "
                        + "'Daily',"
                        + "'Shopping for eggs, milk, bread, lettuce', "
                        + "'2019-11-19', "
                        + "'2019-11-19', "
                        + "'80'"
                        + ");");
                createDummyTasks.execute();
                
            } else {
               System.out.println("TASKS table already exists");
//        dropTable = conn.prepareStatement("DROP TABLE TASKS");
//        dropTable.execute();
//                System.out.println("dropped");
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
                        + "CATEGORYNAME TEXT PRIMARYKEY, "
                        + "ACTIVITY TEXT, "
                        + "DATE TEXT, "
                        + "START TEXT, " //integer
                        + "END TEXT, " //integer
                        + "DURATION INTEGER," 
                        + "DESCR TEXT,"
                        + "FOREIGN KEY (CATEGORYNAME)"
                        + "REFERENCES CATEGORY(CATEGORYNAME)"
                        + ");");
                createTimesheetsTable.execute();
                System.out.println("Timesheets table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME, ACTIVITY, DATE,START,END,DURATION,DESCR) "
                        + "VALUES ('Work', "
                        + "'Videography',"
                        + "'2019-11-14',"
                        + "'05:00 am',"
                        + "'06:00 am',"
                        + "'60',"
                        + " 'Today I had a productive day at the office!'"
                        + ");"
                        );
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                        + "VALUES ('Work', "
                        + "'Networking', "
                        + "'2019-11-14',"
                        + "'11:20 am',"
                        + "'03:20 pm',"
                        + "'240',"
                        + " 'Today I had a productive day at the office launch!'"
                        + ");"
                        );
                
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) " 
                        + "VALUES ('Relaxation',"
                        + "'Gaming', "
                        + "'2019-11-14',"
                        + "'02:00 pm',"
                        + "'04:00 pm',"
                        + "'120',"
                        + "'Today I played League with my friends'"
                        + ");"
                        );
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                + "VALUES('Wellness', "
                + "'Exercise',"
                + "'2019-11-15',"
                + "'10:20 am',"
                + "'11:05 am',"
                + "'45',"
                + "'I ran with to the park with my dog'"
                + ");"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                + "VALUES('Projects',"
                + "'Painting', "
                + "'2019-11-15',"
                + "'09:20 pm',"
                + "'11:00 pm',"
                + "'120',"
                + "'I completed my painting of my 2D girlfriend!'"
                + ");"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                + "VALUES('Projects',"
                + "'DIY', "
                + "'2019-11-16',"
                + "'12:00 pm',"
                + "'02:30 pm',"
                + "'90',"
                + "'I made a wooden kennel for my dog!'"
                + ");"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                + "VALUES('Daily',"
                + "'Chores',"
                + "'2019-11-17',"
                + "'07:20 am',"
                + "'08:40 am',"
                + "'140',"
                + "'I went to Woolies to buy some groceries'"
                + ");"
                );
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR) "
                + "VALUES('Relationships',"
                + "'Family', "
                + "'2019-11-17',"
                + "'08:20 pm',"
                + "'09:40 pm',"
                + "'140',"
                + "'I had dinner with my family tonight'"
                + ");"
                );
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME,ACTIVITY,DATE,START,END,DURATION,DESCR)  "
                        + "VALUES ('Projects',"
                        + "'DIY', "
                        + "'2019-11-18',"
                        + "'01:00 pm',"
                        + "'04:00 pm',"
                        + "'360',"
                        + "'Today I learnt how to carve wood'"
                        + ");"
                        );
                
                createDemoInstance.execute();
                
                createDemoInstance = conn.prepareStatement("INSERT INTO TIMESHEETS(CATEGORYNAME, ACTIVITY, DATE,START,END,DURATION,DESCR) "
                        + "VALUES ('Work', "
                        + "'Networking',"
                        + "'2019-11-10',"
                        + "'08:30 am',"
                        + "'04:30 pm',"
                        + "'480', "
                        + "'I successfully presented our team strategy to the board!'"
                        + ");"
                        );
                createDemoInstance.execute();
                
            } else {
                System.out.println("Timesheets table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createLearnings(){
        PreparedStatement createLearnings;
        PreparedStatement insertDemoData;
        ResultSet rs;
        openConnection();
        try{
            System.out.println("Checking LEARNINGS table");
            DatabaseMetaData db = conn.getMetaData();
            rs = db.getTables(null,null,"LEARNINGS", null);
            if(!rs.next()){
            createLearnings = conn.prepareStatement("CREATE TABLE IF NOT EXISTS LEARNINGS("
                        + "LEARNINGS_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT NOT NULL"
                        + ", DID_WELL TEXT NOT NULL"
                        + ", BE_BETTER TEXT NOT NULL)");
            createLearnings.execute();
            System.out.println("LEARNINGS table created!");
            insertDemoData = conn.prepareStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('01/01/2019', 'went to the gym', 'spend time with family');");
            insertDemoData.execute();
            insertDemoData = conn.prepareStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('02/01/2019', 'went to the gym', 'picked up java earlier');");
            insertDemoData.execute();
            insertDemoData = conn.prepareStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('03/01/2019', 'went to the work', 'study 2605');");
            insertDemoData.execute();           
        } else{
                System.out.println("LEARNINGS table exists!");
                }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
   
    


/*      public static void createDailyLearningsTable() {
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
                        + "VALUES ('lienzhu', TO_DATE('03/11/2019','DDMMYYY'), "
                        + "'Today I spent 40 minutes exercising intensely', "
                        + "'Today I didn't watch the newest episode of MHA...');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','03/11/2019', 'Today I spent 20 minutes exercising intensely', "
                        + "'Today I could have spent more time with my family');");
                createDemoInstance.execute();
            } else {
                System.out.println("Daily Learnings table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    
        }*/
    
    /*
        public static void createDailyLearnings() {
        PreparedStatement createDailyLearnings= null;
        PreparedStatement createDemoInstance = null;
        ResultSet rs = null;
        openConnection();
        try {
            System.out.println("Checking Learnings table ");
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, null, "DAILY_LEARNINGS", null);
            if (rs.next()) {
                createDailyLearnings = conn.prepareStatement("DROP TABLE DAILY_LEARNINGS ("
                        + "USERNAME TEXT NOT NULL "
                        + ", ENTRY_DATE TEXT NOT NULL"
                        + ", DID_WELL TEXT NOT NULL "
                        + ", BE_BETTER TEXT NOT NULL "
                        + ");");
                createDailyLearnings.execute();
                System.out.println("Daily Learnings table created");
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','01/02/2019', 'Today I spent 40 minutes exercising intensely', "
                        + "'Today I didn't watch the newest episode of MHA...');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','02/02/2019', 'Today I spent 40 minutes exercising intensely', "
                        + "'Today I didn't watch the newest episode of MHA...');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','03/02/2019', 'Today I spent 40 minutes exercising intensely', "
                        + "'Today I didn't watch the newest episode of MHA...');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','04/02/2019', 'I went to the gym', "
                        + "'Today I didn't watch the newest episode of NBA...');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','05/02/2019', 'I played the piano', "
                        + "'Today I didn't spend enough time with my family');");
                createDemoInstance.execute();
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','06/02/2019', 'I played the violin', "
                        + "'Today I didn't spend enough time with my family');");
                createDemoInstance = conn.prepareStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) "
                        + "VALUES ('lienzhu','07/02/2019', 'I gave good peer reviews', "
                        + "'Today I didn't spend enough time with my family');");
                createDemoInstance.execute();
            } else {
                System.out.println("Daily Learnings table exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    
        }
        
        public static void insertLearnings(){
            PreparedStatement pst;
            openConnection();
            System.out.println("Pre-insertion of learnings");
        try {
            pst = conn.prepareStatement("INSERT INTO LEARNINGS VALUES(?,?,?,?)");
            pst.setString(1,"lienzhu");
            pst.setString(2,"01/11/2019");
            pst.setString(3,"I went to the gym today");
            pst.setString(4,"I didn't walk the dog");
            pst.setString(1,"lienzhu");
            pst.setString(2,"02/11/2019");
            pst.setString(3,"I went to the gym today");
            pst.setString(4,"I didn't walk the dog");
            pst.setString(1,"lienzhu");
            pst.setString(2,"31/11/2019");
            pst.setString(3,"I went to the gym today");
            pst.setString(4,"I didn't walk the dog");
            
            System.out.println("Learnings inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
        }
    }*/

    
    
