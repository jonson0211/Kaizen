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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kaizen.DataModels.learningsDidWell;
import kaizen.DataModels.learningsDoBetter;
import kaizen.UserData.KaizenDatabase;
import java.time.format.DateTimeFormatter;
/**
 * FXML Controller class
 *
 * @author wongad1
 */
public class DailyLearningsController implements Initializable {

    KaizenDatabase userLearn = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    ObservableList<String> answerOnes = FXCollections.observableArrayList("I went to the gym today", "I played the piano", "I didn't procrastinate", "I gave a good peer review");
    ObservableList<String> answerTwos = FXCollections.observableArrayList("I want to spend more time with my family", "I could have given better marks to my peers", "I should not have marked too harshly");
    
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
    
    @FXML
    private TableView<learningsDidWell> didWellView;
    
    @FXML
    private TableView<learningsDoBetter> doBetterView;
    
    @FXML
    private TableColumn<learningsDidWell, String> didWellColumn;
    
    @FXML
    private TableColumn<learningsDidWell, Number> didWellCount;
    
    @FXML
    private TableColumn<learningsDoBetter, String> doBetterColumn;
    
    @FXML
    private TableColumn<learningsDoBetter, Number> doBetterCount;  
    
    @FXML
    private Button viewPast;       
               
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
        //FillComboOne();
        //FillComboTwo();
        confirmEntry.setVisible(false);
        viewPast.setVisible(true);
    }    
    
    //return observable list of done well and do betters
 /*   
    public ObservableList<learningsDidWell> getLearningsDidWell(){
        
        ObservableList<learningsDidWell> didWellList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsDidWellTable = userLearn.getResultSet("SELECT DID_WELL, COUNT (*) FROM LEARNINGS "
                    + "GROUP BY DID_WELL"
                    + "HAVING COUNT(*) >1"
                    + "ORDER BY COUNT(*) "
                    + "WHERE USERNAME = '" + LoginScreenController.loginUsername + "';");
            
            while (rsDidWellTable.next()){
                didWellList.add(new learningsDidWell(rsDidWellTable.getString(1), rsDidWellTable.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(didWellList);
    }
    
        public ObservableList<learningsDoBetter> getLearningsDoBetter(){
        
        ObservableList<learningsDoBetter> doBetterList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsDoBetterTable = userLearn.getResultSet("SELECT DO_BETTER, COUNT (*) FROM LEARNINGS "
                    + "GROUP BY DID_WELL"
                    + "HAVING COUNT(*) >1"
                    + "ORDER BY COUNT(*) "
                    + "WHERE USERNAME = '" + LoginScreenController.loginUsername + "';");
            
            while (rsDoBetterTable.next()){
                doBetterList.add(new learningsDoBetter(rsDoBetterTable.getString(1), rsDoBetterTable.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(doBetterList);
    }
    
        private void loadTable(){
        didWellColumn.setCellValueFactory(new PropertyValueFactory<learningsDidWell, String>("didWell"));
        doBetterColumn.setCellValueFactory(new PropertyValueFactory<learningsDoBetter, String>("DO_BETTER"));
        didWellCount.setCellValueFactory(new PropertyValueFactory<learningsDidWell, Number>("didWellCount"));
        doBetterCount.setCellValueFactory(new PropertyValueFactory<learningsDoBetter, Number>("COUNT(*)"));
        
        didWellView.setItems(getLearningsDidWell());
        doBetterView.setItems(getLearningsDoBetter());
    }*/
    //input learnings into the table summary
    //update learnings
    @FXML
    private void updateAnswers(ActionEvent event) throws SQLException{
        String answerOneString = (String) answerOne.getValue();
        String answerTwoString = (String) answerTwo.getValue();
        String date = datePick.getValue().format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
        
        userLearn.insertStatement("INSERT INTO DAILY_LEARNINGS(USERNAME, ENTRY_DATE, DID_WELL, BE_BETTER) VALUES (" + LoginScreenController.loginUsername + "," + date + "', " + answerOneString + ", " + answerTwoString + "');");
        System.out.println("Entered in learnings");
        confirmEntry.setVisible(true);
        
    //to do - update combo box values
    }
    //see if user has already entered in previous answers
    //
    @FXML
    private void updateComboOneValue(ActionEvent event){
        try{
            ResultSet currentAnswerOne = userLearn.getResultSet("SELECT USERNAME, DID_WELL FROM DAILY_LEARNINGS"
                    + "WHERE USERNAME = " + LoginScreenController.loginUsername + " ");
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
    private void handlePopUpScreenAction(ActionEvent event) throws IOException{
        try{
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("PopUpLearnings.fxml"));
            Parent r1 = (Parent) fxmlloader.load();
            Stage stage = new Stage();
            stage.setTitle("Past 30 days Report");
            stage.setScene(new Scene(r1));
            stage.show();
            
        } catch (Exception e){
            System.out.println("Can't display window");
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
    @FXML
    private void handlePopUpScreen(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"PopUpLearnings.fxml");
    }
}
                