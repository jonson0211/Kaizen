
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TimesheetsController implements Initializable {
   
    /**@FXML
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
    **/
    
    @FXML private AnchorPane anchor;
    @FXML private BorderPane border;
    @FXML private VBox box;
    @FXML private Text text;
    
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
    /**
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
     **/
    @FXML
    private ToggleButton categoryWork;
    
    @FXML
    private ToggleButton categoryDaily;
    
    @FXML
    private ToggleButton categoryRelationships;
    
    @FXML
    private ToggleButton categoryWellness;
    
    @FXML
    private ToggleButton categoryRelaxation;
    
    @FXML
    private ToggleButton categoryProjects;
    
    
    
    @FXML private TextField timeStartHrField;
    
    @FXML private TextField timeEndHrField;
    
    @FXML private TextField timeStartMinField;
    @FXML private TextField timeEndMinField;
    
    @FXML
    private Label durationLabel;
    
    
    @FXML
    private TextArea descriptionText;
    
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
        //timeStartHourComboBox.setItems(TSH);
        //timeStartMinComboBox.setItems(TSM);
        //timeEndHourComboBox.setItems(TEH);
        //timeEndMinComboBox.setItems(TEM);
    /**TextArea description = new TextArea();
    description.getText();
    System.out.print(description);
    * **/
    //durationLabel.setVisible(false);
       //TextArea description = new TextArea();
         
    }
    
    
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        /** String start = timeStart.getText();
        String end = timeEnd.getText(); **/
        
        //String startHR = timeStartHourComboBox.getValue();
        //String endHR = timeEndHourComboBox.getValue();
        //String startMin = timeStartMinComboBox.getValue(); 
        //String endMin = timeEndMinComboBox.getValue();
        
        //String combinedStart = startHR.concat(":"+ startMin);
        //String combinedEnd = endHR.concat(":"+ endMin);
        
        //int startHRTime = Integer.parseInt(startHR);
        //int endHRTime = Integer.parseInt(endHR);
        //int startMinTime = Integer.parseInt(startMin);
        //int endMinTime = Integer.parseInt(endMin);
        
        //int startSum = (startHRTime*60 + startMinTime);
        //int endSum =(endHRTime*60 + endMinTime);
        //int duration = (endSum - startSum);
        
        Toggle catName = toggleGroup.getSelectedToggle();
        String timeStartHr = timeStartHrField.getText();
        String timeStartMin = timeStartMinField.getText();
        String timeEndHr = timeEndHrField.getText();
        String timeEndMin = timeEndMinField.getText();
        
        double timeStartHrNum = Double.parseDouble(timeStartHr);
        double timeStartMinNum = Double.parseDouble(timeStartMin);
        double timeEndHrNum = Double.parseDouble(timeEndHr);
        double timeEndMinNum = Double.parseDouble(timeEndMin);
        
        double startCombined = (timeStartHrNum*60 + timeStartMinNum);
        double endCombined = (timeEndHrNum*60 + timeEndMinNum);
        
        double duration = endCombined - startCombined; 
        
       
        String durationText = Double.toString(duration);
        String desc = descriptionText.getText();
        
        //Toggle cat;
        //cat = toggleGroup.getSelectedToggle();
        
        //desc = description.getText();
        //System.out.println(desc);

        try {
            System.out.print(duration);
            System.out.print(startCombined);
            System.out.print(endCombined);
            System.out.print(catName);
            System.out.println(desc);
            System.out.println(duration);
            
            addTimesheet.insertStatement("INSERT INTO TIMESHEETS (CATEGORYNAME, START, END, DURATION, DESCR)"
                    + " VALUES('"+ catName + "', '"+ startCombined + "', '"+  endCombined + "', '" +
                    duration + "', '" + desc + "');");
            durationLabel.setText(durationText);
            durationLabel.setVisible(true);
            
            
       //**test if duration is calculated**// 
       
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
