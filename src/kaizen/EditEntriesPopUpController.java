
package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.timesheetsDM;

public class EditEntriesPopUpController implements Initializable {
       
    
    @FXML private TextField timeStartHrField;
    
    @FXML private TextField timeEndHrField;
    
    @FXML private TextField timeStartMinField;
    @FXML private TextField timeEndMinField;
    
    @FXML
    private Label durationLabel;
    
    @FXML
    private TextArea descriptionText;
    
    @FXML
    private ComboBox<String> tsCombo;
    
    
    @FXML private Rectangle categoryColourShape;
    
    @FXML
    private Button submit;
    
    @FXML
    private Button back;
    
    @FXML private DatePicker DtPicker;
    
    ToggleGroup toggleGroup = new ToggleGroup(); 
 
    KaizenDatabase addTimesheet = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML ComboBox<String> activityComboBox;
    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
    durationLabel.setVisible(false);
    categoryColourShape.setVisible(false);
    categoryComboBox.setEditable(true);
    activityComboBox.setEditable(true);
    
    }
    public void setData(String string, String category, String date, String description, Integer duration, String timeStartHr, String timeEndHr) {
        activityComboBox.setValue(string);
        categoryComboBox.setValue(category);    
        DtPicker.setUserData(date);
        
        descriptionText.setText(description);
        durationLabel.setUserData(duration);
               
        timeStartHrField.setUserData(timeStartHr);        
        timeEndHrField.setText(timeEndHr);
        
        //To change body of generated methods, choose Tools | Templates.
   
        }    
    
    @FXML
    private void handleSubmit(ActionEvent event) throws SQLException{
        String act = activityComboBox.getValue();
        String category = categoryComboBox.getValue();
        LocalDate date = DtPicker.getValue();
        String desc = descriptionText.getText();
        String dur = durationLabel.getText();
        String stHr = timeStartHrField.getText();
        String stMin = timeStartMinField.getText();
        String endHr = timeEndHrField.getText();
        String endMin = timeEndMinField.getText();
        
        addTimesheet.insertStatement("UPDATE TIMESHEETS"
                + " SET CATEGORYNAME = '"+ category +"'"
                + " AND ACTIVITY = '"+act+"' "
                + "AND DATE = '"+date+"'"
                + "AND DESCR = '"+desc+"'"
                + "AND ");
                
        
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
        pageSwitcher.switcher(event,"AboutScreen.fxml");
    }

    //To change body of generated methods, choose Tools | Templates.
    }

    
 

