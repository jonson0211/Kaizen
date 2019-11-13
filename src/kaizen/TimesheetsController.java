
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.colourDM;
import kaizen.DataModels.learningsDidWell;
import kaizen.DataModels.timesheetsDM;
import java.awt.*;
import java.awt.color.*;

public class TimesheetsController implements Initializable {
 
    @FXML private TextField timeStartHrField;    
    @FXML private TextField timeEndHrField;    
    @FXML private TextField timeStartMinField;
    @FXML private TextField timeEndMinField;   
    @FXML private Label durationLabel;    
    @FXML private TextArea descriptionText;   
    @FXML private Rectangle categoryColourShape;    
    @FXML private Button submit;   
    @FXML private Button back;   
    @FXML private DatePicker DtPicker;   
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> activityComboBox;
    //@FXML private TableView<Item<String>> colourTableView = new TableView<>();
//    private TableView<Colour> ColourTable;
//    private TableColumn<Colour> String>> colourColumn;        
    
    
    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
    ObservableList<colourDM> colourShapeList = FXCollections.observableArrayList();
    
    ToggleGroup toggleGroup = new ToggleGroup();  
    KaizenDatabase addTimesheet = new KaizenDatabase();   
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb){

    durationLabel.setVisible(false);
    categoryColourShape.setVisible(false);
    categoryComboBox.setEditable(true);
    activityComboBox.setEditable(true);
    
    
    
    categoryComboList.setAll(this.getCatChoice());
    for(categoryCombo c : categoryComboList){
        System.out.println(c.getCatChoiceProperty());
        categoryComboBox.getItems().addAll(c.getCatChoice());
    }
    activityComboList.setAll(this.getActChoice());
    for(activityCombo d : activityComboList){
        System.out.println(d.getActChoiceProperty());
        activityComboBox.getItems().addAll(d.getActChoice());
    }
    
    
    }
    //get Category Choice for combo box
    public ObservableList<categoryCombo> getCatChoice(){
        
        ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsCategoryComboTable = addTimesheet.getResultSet("SELECT DISTINCT(CATEGORYNAME) FROM CATEGORY");
            
            while (rsCategoryComboTable.next()){
                categoryComboList.add(new categoryCombo(rsCategoryComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(categoryComboList);
    }
    //get activity choice for combo box
    public ObservableList<activityCombo> getActChoice(){
        
        ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsActivityComboTable = addTimesheet.getResultSet("SELECT DISTINCT(ACTIVITY) FROM TIMESHEETS");
            
            while (rsActivityComboTable.next()){
                activityComboList.add(new activityCombo(rsActivityComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(activityComboList);
    }
    
   public ObservableList<colourDM> getColourChoice(){
        
        ObservableList<colourDM> colourList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsColourList = addTimesheet.getResultSet("SELECT DISTINCT(CATEGORYNAME), COLOUR FROM TIMESHEETS");
            
            while (rsColourList.next()){
                colourShapeList.add(new colourDM(rsColourList.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(colourShapeList);
    }
    
   
//    colourTable.setRowFactory(tv -> new TableRow<CustomItem>() {
//    @Override
//    protected void updateItem(CustomItemitem, boolean empty) {
//        super.updateItem(item, empty);
//        if (item == null || item.getValue() == null)
//            setStyle("");
//        else if (item.getValue() > 0)
//            setStyle("-fx-background-color: #baffba;");
//        else if (item.getValue() < 0)
//            setStyle("-fx-background-color: #ffd7d1;");
//        else
//            setStyle("");
//    }
//});
   
//   private void customiseFactory(TableColumn<CallLogs, String> calltypel) {
//    calltypel.setCellFactory(column -> {
//        return new TableCell<CallLogs, String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//
//                setText(empty ? "" : getItem().toString());
//                setGraphic(null);
//
//                TableRow<CallLogs> currentRow = getTableRow();
//
//                if (!isEmpty()) {
//
//                    if(item.equals("a")) 
//                        currentRow.setStyle("-fx-background-color:lightcoral");
//                    else
//                        currentRow.setStyle("-fx-background-color:lightgreen");
//                }
//            }
//        };
//    });
//}
   
    @FXML
    private void handleInputChangedAction(ActionEvent event) throws SQLException {
        
       String catName = categoryComboBox.getValue();
        ResultSet catColourRs = addTimesheet.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                + "WHERE CATEGORYNAME = '" + catName + "'"
        );
        
        
        String colourShape = catColourRs.getString(2);
        
//        Color newColor;
//        newColor = (Color) colourShape;
        //return colourShape;
        //String colour = '"' +colourShape+ '"';
        System.out.println("*" + colourShape);
        
        //System.out.println(colour);
        //categoryColourShape.setFill(Color.RED);
        //categoryColourShape.setFill(Color.web(colour,1));
        
        if(catName.equals("Work")){
            categoryColourShape.setFill(Color.web("#80bfff"));
        }
        if (catName.equals("Wellness")){
            categoryColourShape.setFill(Color.web("#80ff80"));
        }
        if (catName.equals("Relationships")){
            categoryColourShape.setFill(Color.web("#cc99ff"));
        }
        if (catName.equals("Projects")){
            categoryColourShape.setFill(Color.web("#ccffff"));
        }
        if (catName.equals("Daily")){
            categoryColourShape.setFill(Color.web("#ff80ff"));
        }
        if (catName.equals("Relaxation")){
            categoryColourShape.setFill(Color.web("#ffb84d"));
        }
        //else{ categoryColourShape.setFill(Color.TRANSPARENT);}
        
        
        categoryColourShape.setVisible(true);
        
        //if doesn't work, jsut switch to color.RED etc and change data
        
    }
            
    
    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException {
        
        String catName = categoryComboBox.getValue();
        
        //pull the category's corresponding colour from database
        
        
        String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String timeStartHr = timeStartHrField.getText();
        String timeStartMin = timeStartMinField.getText();
        String timeEndHr = timeEndHrField.getText();
        String timeEndMin = timeEndMinField.getText();
        
        String act = (String) activityComboBox.getValue();
        
        double timeStartHrNum = Double.parseDouble(timeStartHr);
        double timeStartMinNum = Double.parseDouble(timeStartMin);
        double timeEndHrNum = Double.parseDouble(timeEndHr);
        double timeEndMinNum = Double.parseDouble(timeEndMin);
        
        double startCombined = (timeStartHrNum*60 + timeStartMinNum);
        double endCombined = (timeEndHrNum*60 + timeEndMinNum);
        
        double duration = endCombined - startCombined; 
        
        String durationText = Double.toString(duration);
        String desc = descriptionText.getText();
       
        
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
