
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class TimesheetsController implements Initializable {
    @FXML
    private ComboBox<String> timeStartHourComboBox;        
    ObservableList<String> TSH = FXCollections.observableArrayList("1", "2","3","4","5","6","7",
            "8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
    @FXML
    private ComboBox<String> timeStartMinComboBox;
    ObservableList<String> TSM = FXCollections.observableArrayList("0","5","10","15","20","25","30",
            "35","40", "45","50","55");
    @FXML
    private ComboBox<String> timeEndHourComboBox;        
    ObservableList<String> TEH = FXCollections.observableArrayList("1", "2","3","4","5","6","7",
            "8","9","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24");
    @FXML
    private ComboBox<String> timeEndMinComboBox;
    ObservableList<String> TEM = FXCollections.observableArrayList("0","5","10","15","20","25","30",
            "35","40", "45","50","55");
      
    @FXML
    private ToggleButton kbBoard;
    
    @FXML
    private ToggleButton deepFocus;
    
    @FXML
    private ToggleButton taskTracker;
    
    @FXML
    private ToggleButton timeDashboard;
    
    @FXML
    private ToggleButton dailyLearnings;
    
    @FXML
    private ToggleButton settings;
    
    @FXML
    private ToggleButton about;  
    @FXML
    private ToggleButton signOut;  
    @FXML
    private RadioButton categoryWork;
    @FXML
    private RadioButton categoryDaily;
    
    @FXML
    private RadioButton categoryRelationships;
    
    @FXML
    private RadioButton categoryWellness;
    
    @FXML
    private RadioButton categoryRelaxation;
    
    @FXML
    private RadioButton categoryProjects;
    
    @FXML private TextField timeStart;
    
    @FXML private TextField timeEnd;
    
    @FXML
    private Label duration;
    
    
    @FXML
    private TextArea description;
    
    @FXML
    private Button submit;
    
    @FXML
    private Button back;
    
    
    
    ToggleGroup toggleGroup = new ToggleGroup();
 
    KaizenDatabase addTimesheet = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeStartHourComboBox.setItems(TSH);
        timeStartMinComboBox.setItems(TSM);
        timeEndHourComboBox.setItems(TEH);
        timeEndMinComboBox.setItems(TEM);
    
       //TextArea description = new TextArea();
         
    }
   
    
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        /** String start = timeStart.getText();
        String end = timeEnd.getText(); **/
        
        String startHR = timeStartHourComboBox.getValue();
        String endHR = timeEndHourComboBox.getValue();
        String startMin = timeStartMinComboBox.getValue(); 
        String endMin = timeEndMinComboBox.getValue();
        
        String combinedStart = startHR.concat(":"+ startMin);
        String combinedEnd = endHR.concat(":"+ endMin);
        
        int startHRTime = Integer.parseInt(startHR);
        int endHRTime = Integer.parseInt(endHR);
        int startMinTime = Integer.parseInt(startMin);
        int endMinTime = Integer.parseInt(endMin);
        
        int startSum = (startHRTime*60 + startMinTime);
        int endSum =(endHRTime*60 + endMinTime);
        int duration = (endSum - startSum);
        
        
       
        Toggle cat = toggleGroup.getSelectedToggle();
        String desc = description.getText();
        

        try {
            addTimesheet.insertStatement("INSERT INTO TIMESHEETS (START, END, DURATION, DESCR, CATEGORYNAME)"
                    + " VALUES(" + combinedStart + ", "+  combinedEnd + ", " + duration+ ", " + desc + ", "+ cat);
       //**test if duration is calculated**// 
       System.out.println(duration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     
        
    }
    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
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
        pageSwitcher.switcher(event,"AboutScreen");
    }
 
}
