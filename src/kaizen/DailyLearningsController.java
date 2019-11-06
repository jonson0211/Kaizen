/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import kaizen.UserData.KaizenDatabase;
import static kaizen.UserData.KaizenDatabase.conn;

/**
 * FXML Controller class
 *
 * @author wongad1
 */
public class DailyLearningsController implements Initializable {

    ObservableList<String> answerOnes = FXCollections.observableArrayList("I went to the gym today", "I played the piano");
    ObservableList<String> answerTwos = FXCollections.observableArrayList("I want to spend more time with my family");
    
    @FXML
    private ComboBox<String> answerOne;
    
    @FXML
    private ComboBox<String> answerTwo;
       
    @FXML
    private Button addLearning;
    
    @FXML
    private ToggleButton kbBoard;
    
    @FXML
    private ToggleButton deepFocus;
    
    @FXML
    private ToggleButton taskTracker;
    
    @FXML
    private ToggleButton timeDashboard;
       
    @FXML
    private ToggleButton settings;
    
    @FXML
    private ToggleButton about;
    
    @FXML
    private ToggleButton signOut;
    
    @FXML
    private DatePicker datePick;
    
    @FXML
    private Label confirmEntry;
    
    KaizenDatabase userLearn = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    PreparedStatement pst;
    
    Connection conn;
    
    ResultSet rs;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        answerOne.setValue("Your lesson today...");
        answerOne.setEditable(true);
        answerOne.setItems(answerOnes);
        answerTwo.setValue("Your lesson today...");
        answerTwo.setEditable(true);
        answerTwo.setItems(answerTwos);
        FillComboOne();
        FillComboTwo();
        confirmEntry.setVisible(false);
    }    
    
    
    //update learnings
    @FXML
    private void updateAnswers(ActionEvent event) throws SQLException{
        String answerOneString = (String) answerOne.getValue();
        String answerTwoString = (String) answerTwo.getValue();
        String date = datePick.getValue().format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
        userLearn.insertStatement("INSERT INTO LEARNINGS (USERNAME, DATE, DID_WELL, BE_BETTER) VALUES (" + LoginScreenController.loggedUser + "," + date + "', " + answerOneString + ", " + answerTwoString + "');");
        System.out.println("Entered in learnings");
        confirmEntry.setVisible(true);
        
    //to do - update combo box values
    }
    //see if user has already entered in previous answers
    //
    @FXML
    private void updateComboOneValue(ActionEvent event){
        try{
            ResultSet currentAnswerOne = userLearn.getResultSet("SELECT USERNAME, DID_WELL FROM LEARNINGS"
                    + "WHERE USERNAME = " + LoginScreenController.loggedUsername + " ");
            answerOne.setValue(String.valueOf(currentAnswerOne.getString(3)));
        } catch(Exception e){
            System.out.println("New user");
            e.printStackTrace();
        }
    }
    //populating combobox
    private void FillComboOne() {
        try {
            String queryOne = "SELECT * FROM LEARNINGS";
            pst = conn.prepareStatement(queryOne);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String didwell = rs.getString("DID_WELL");
                answerOne.setValue(rs.getString(didwell));
            } 
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    private void FillComboTwo(){
        String queryTwo = "SELECT * FROM LEARNINGS";
        try {
            pst = conn.prepareStatement(queryTwo);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String bebetter = rs.getString("BE_BETTER");
                answerTwo.setValue(rs.getString(bebetter));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    @FXML
    private void handleKbBoard(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
    }
    @FXML
    private void handleDeepFocus(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"DeepFocusMode.fxml");  
    }
    @FXML
    private void handleTaskTracker(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"TaskTracker.fxml");//TO CHANGE WHEN PAGE IS MADE
    }
    @FXML
    private void handleTimeSheets(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"Timesheets.fxml"); 
    }
    @FXML
    private void handleDailyLearnings(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"DailyLearnings.fxml");
    }
    @FXML
    private void handleSettings(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"Settings.fxml"); //TO CHANGE WHEN PAGE IS MADE
    }
    @FXML
    private void handleSignOut(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"LoginScreen.fxml");
    }
    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"AboutScreen.fxml");
    }
}
                