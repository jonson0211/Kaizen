
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TimesheetsController implements Initializable {
       
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
    
    @FXML private DatePicker DtPicker;
    
    ToggleGroup toggleGroup = new ToggleGroup(); 
 
    KaizenDatabase addTimesheet = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    durationLabel.setVisible(false);
  
    }
    
    
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        
        String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
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
       
        Toggle cat = toggleGroup.getSelectedToggle();
        String catName = null;
        if (categoryWork.isSelected())
            catName = "Work";
        if (categoryWellness.isSelected())
            catName = "Wellness";
        if (categoryProjects.isSelected())
            catName = "Projects";
        if (categoryRelaxation.isSelected())
            catName = "Relaxation";
        if (categoryProjects.isSelected())
            catName = "Work";
        if (categoryRelationships.isSelected())
            catName = "Relationships";
        
        try {
            
            addTimesheet.insertStatement("INSERT INTO TIMESHEETS (CATEGORYNAME,DATE, START, END, DURATION, DESCR)"
                    + " VALUES('" + catName + "', '" + date + "','" + startCombined + "', '"+  endCombined + "', '" +
                    duration + "', '" + desc + "');");
            durationLabel.setText(durationText + " minutes");
            durationLabel.setVisible(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     
        
    }
//    @FXML
//    private void handleBackAction(ActionEvent event) throws IOException {
//        pageSwitcher.switcher(event, "PieChart.fxml");
//    }    
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
