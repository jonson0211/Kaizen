/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import kaizen.DataModels.activityCombo;
import kaizen.UserData.KaizenDatabase;


public class WeeklyTrendsController implements Initializable {

    KaizenDatabase db = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    //menu buttons
    @FXML private Button signOutButton;
    @FXML private ToggleButton settingsButton;
    @FXML private ToggleButton kanbanBoard;
    @FXML private ToggleButton deepFocus;
    @FXML private ToggleButton taskTracker;
    @FXML private ToggleButton about;
    @FXML private ToggleButton timeDashboard;
    @FXML private ToggleButton dailyLearnings;
    //chart buttons
    @FXML private Button dailyBreakdown;
    @FXML private Button weeklyBreakdown;
    @FXML private Button weeklyTrends;
    @FXML private Button backTimeDashboard;
    @FXML private Button backBtn;
    //barchart
    @FXML private LineChart<String, Number> weeklyTrendsLineChart;
    //barcharts variables
    @FXML private Button loadGraphButton;
    @FXML private TextField numWeeksTxtField;
    //@FXML private ChoiceBox activityChoiceBox;
    @FXML
    private ChoiceBox<String> activityChoiceBox;        
    
    
    //datepicker
    @FXML private DatePicker weeklyChartDtPicker;
    
    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
        
    activityComboList.setAll(this.getActChoice());
    for(activityCombo d : activityComboList){
        System.out.println(d.getActChoiceProperty());
        activityChoiceBox.getItems().addAll(d.getActChoice());
    }    
    activityChoiceBox.setValue("Choose Type!");
    
    }    

    //choose number of weeks back, choose activity, load graph
    
    
    @FXML
    private void loadGraph(ActionEvent event) throws SQLException{
        weeklyTrendsLineChart.getData().clear();
        
        String numWeeksBack = numWeeksTxtField.getText();
        
        int numWeeks = Integer.parseInt(numWeeksBack);
        String activity = activityChoiceBox.getValue().toString();
   
        try{
            LocalDate date = weeklyChartDtPicker.getValue();

            XYChart.Series<String, Number> weeklySeries = new XYChart.Series<String,Number>();
            
            weeklySeries.setName(activity);
            weeklyTrendsLineChart.getData().addAll(weeklySeries);
            
            //Calculate duration of activities during selected time period
            ArrayList<Double> durationList = new ArrayList();
            ArrayList<Double> durationWeeklyList = new ArrayList();
            
            for(int i = 0; i<numWeeks; i++){
                    
        ResultSet weeklyTotalDuration = db.getResultSet("SELECT SUM(DURATION) FROM TIMESHEETS "
                    + "WHERE DATE BETWEEN date('" + date + "','" + ((i+1)*-7)+ " days') "
                            + "and date('" + date + "','" + (i*-7)+ " days')"
                    );
            
                
            while (weeklyTotalDuration.next()){
                durationWeeklyList.add((weeklyTotalDuration.getDouble(1)));
            System.out.println("*"+durationWeeklyList);   
            }
            System.out.println("*"+durationWeeklyList);
            
        ResultSet weekly = db.getResultSet("SELECT ACTIVITY, DURATION FROM TIMESHEETS "
                    + "WHERE ACTIVITY = '" + activity + "'"
                    + "AND DATE BETWEEN date('" + date + "','" + ((i+1)*-7)+ " days') "
                            + "and date('" + date + "','" + (i*-7)+ " days')"
                    );
            
            while (weekly.next()){
                durationList.add((weekly.getDouble(2)));
            }
            }
            try{
        
                for(int n = 0; n<durationList.size(); n++){
                    weeklySeries.getData().add(new XYChart.Data("Week " + (n+1), Math.round((durationList.get(n)/durationWeeklyList.get(1))*100)));
                    
                    System.out.println("*"+Math.round(durationList.get(n)/durationWeeklyList.get(n)));
                }   
            
            System.out.println("*" + durationList);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
    } catch(Exception ex){
            ex.printStackTrace();}  }
    
    public ObservableList<activityCombo> getActChoice(){
        
        ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
        
        try {
            ResultSet rsActivityComboTable = db.getResultSet("SELECT DISTINCT(ACTIVITY) FROM TIMESHEETS");
            
            while (rsActivityComboTable.next()){
                activityComboList.add(new activityCombo(rsActivityComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(activityComboList);
    }
    
    
    @FXML 
    private void handlePieChart(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "PieChart.fxml");
    }
        
    @FXML
    private void handleDailyBreakdown(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "DailyBreakdown.fxml");
    }
    @FXML
    private void handleWeeklyBreakdown(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "WeeklyBreakdown.fxml");
    }
    @FXML
    private void handleWeeklyTrends(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "WeeklyTrends.fxml");
    }
    @FXML
    private void handleLogInTime(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event, "Timesheets.fxml");
    }
    @FXML
    private void handleKanbanBoard(ActionEvent event) throws IOException{
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
    private void handleTimeDashboard(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"PieChart.fxml"); 
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
