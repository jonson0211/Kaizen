/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    
    //barchart
    @FXML private LineChart<String, Number> weeklyTrendsLineChart;
    //barcharts variables
    @FXML private Button loadGraphButton;
    @FXML private TextField numWeeksTxtField;
    //@FXML private ChoiceBox activityChoiceBox;
    @FXML
    private ChoiceBox<String> activityChoiceBox;        
    ObservableList<String> TSH = FXCollections.observableArrayList("Work", "Projects","Relationships","Wellness","Daily","Relaxation");
    
    //datepicker
    @FXML private DatePicker weeklyChartDtPicker;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
    activityChoiceBox.setValue("Choose Type!");
    activityChoiceBox.setItems(TSH);
    }    

    //choose number of weeks back, choose activity, load graph
    
    
    @FXML
    private void loadGraph(ActionEvent event){
        weeklyTrendsLineChart.getData().clear();
        
        String numWeeksBack = numWeeksTxtField.getText();
        
        int numWeeks = Integer.parseInt(numWeeksBack);
        String activity = activityChoiceBox.getValue().toString();
        
        //CategoryAxis xAxis = (CategoryAxis) weeklyTrendsLineChart.getXAxis();
//        ObservableList<String> categories = weeklyTrendsLineChart.observableArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
//                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
//        );
        //xAxis.setCategories(categories);
        
        
        try{
            LocalDate date = weeklyChartDtPicker.getValue();
            //test date output:
            System.out.println("*"+ date);
            System.out.println("*"+ activity);
            System.out.println("*"+ numWeeks*7);
            
            //XYChart.Series series = new XYChart.Series();
            XYChart.Series<String, Number> weeklySeries = new XYChart.Series<String,Number>();
            
            weeklySeries.setName("Activity1");
            weeklyTrendsLineChart.getData().addAll(weeklySeries);
            
            ResultSet weekly = db.getResultSet("SELECT CATEGORYNAME, DURATION FROM TIMESHEETS "
                    + "WHERE CATEGORYNAME = '" + activity + "'"
                    + "AND DATE BETWEEN date('" + date + "','" + (numWeeks*-7)+ " days') and '" +date + "'"
//                    + "WHERE DATE " 
//                    + "BETWEEN date('" + date + "','"
//                    + "- " + (numWeeks*7) + "days') "
//                    + "and date ('" + date + "')"
//                    + " AND "
//                    + "CATEGORYNAME = "
//                    + "'" + activity + "'"
                    );
           
            
            //ArrayList<String> categoryNameList = new ArrayList();
            ArrayList<Integer> durationList = new ArrayList();
            //ArrayList<Integer> numWeeksCount = new ArrayList();
            
            //test arraylist output:
            //System.out.println(categoryNameList);
            //test output:
            System.out.println(weekly);
            
            while (weekly.next()){
                //categoryNameList.add(weekly.getString(1));
                //numWeeksCount.add((weekly.getInt(1)));
                durationList.add((weekly.getInt(2)));
                
                System.out.println(weekly.getInt(2));
                
                for(int i = 0; i<durationList.size(); i++){
                    weeklySeries.getData().add(new XYChart.Data("Week " + (i+1), durationList.get(i)));
                    System.out.println(durationList.get(i));
                }
                
            }
            //test duration output
            System.out.println(durationList);
            //weeklyTrendsLineChart.getData().addAll(weeklySeries);
        } catch(Exception ex){
            ex.printStackTrace();
        }
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
