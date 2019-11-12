
package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TimesheetsController implements Initializable {
       
    @FXML private AnchorPane anchor;
    @FXML private BorderPane border;
    @FXML private VBox box;
    @FXML private Text text;
       
       
    
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
    @FXML
    private ChoiceBox<String> categoryComboBox;
    
    @FXML private Rectangle categoryColourShape;
    
    @FXML
    private Button submit;
    
    @FXML
    private Button back;
    
    @FXML private DatePicker DtPicker;
    
    ToggleGroup toggleGroup = new ToggleGroup(); 
 
    KaizenDatabase addTimesheet = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    ObservableList<String> tsValues = FXCollections.observableArrayList("Exercise","Job-related","Videography","Socialising","Music","Life organisation","Food-related");
    
ObservableList<String> categoryValues = FXCollections.observableArrayList(""
            + "Work","Projects","Relaxation","Wellness","Relationships","Daily","Food-related");
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    durationLabel.setVisible(false);
    
    categoryColourShape.setVisible(false);
    
    
    tsCombo.setEditable(true);
    tsCombo.setItems(tsValues);
    categoryComboBox.setItems(categoryValues);
    
  
    }
    
    @FXML
    private void handleInputChangedAction(ActionEvent event) throws SQLException {
     String catName = categoryComboBox.getValue();
        ResultSet catColourRs = addTimesheet.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                + "WHERE CATEGORYNAME = '" + catName + "'"
        );
        String colourShape = catColourRs.getString(2);
        String colour = '"' +colourShape+ '"';
        System.out.println("*" + colourShape);
        //System.out.println(colour);
        //categoryColourShape.setFill(Color.RED);
        //categoryColourShape.setFill(Color.web(colour,1));
        categoryColourShape.setFill(Color.web("#80bfff",1));
        categoryColourShape.setVisible(true);
        
        //if doesn't work, jsut switch to color.RED etc and change data

        
        
    }
    
    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException {
        
        String catName = categoryComboBox.getValue();
        
        //pull the category's corresponding colour from database
        
        
        String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        String timeStartHr = timeStartHrField.getText();
        String timeStartMin = timeStartMinField.getText();
        String timeEndHr = timeEndHrField.getText();
        String timeEndMin = timeEndMinField.getText();
        
        String act = (String) tsCombo.getValue();
        
        double timeStartHrNum = Double.parseDouble(timeStartHr);
        double timeStartMinNum = Double.parseDouble(timeStartMin);
        double timeEndHrNum = Double.parseDouble(timeEndHr);
        double timeEndMinNum = Double.parseDouble(timeEndMin);
        
        double startCombined = (timeStartHrNum*60 + timeStartMinNum);
        double endCombined = (timeEndHrNum*60 + timeEndMinNum);
        
        double duration = endCombined - startCombined; 
        
        String durationText = Double.toString(duration);
        String desc = descriptionText.getText();
       
//        Toggle cat = toggleGroup.getSelectedToggle();
//        String catName = null;
//        if (categoryWork.isSelected())
//            catName = "Work";
//        if (categoryWellness.isSelected())
//            catName = "Wellness";
//        if (categoryProjects.isSelected())
//            catName = "Projects";
//        if (categoryRelaxation.isSelected())
//            catName = "Relaxation";
//        if (categoryProjects.isSelected())
//            catName = "Work";
//        if (categoryRelationships.isSelected())
//            catName = "Relationships";
        
        try {
            
            addTimesheet.insertStatement("INSERT INTO TIMESHEETS (CATEGORYNAME, ACTIVITY,DATE, START, END, DURATION, DESCR)"
                    + " VALUES('" + catName + "', '" + act + "', '" + date + "','" + startCombined + "', '"+  endCombined + "', '" +
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
