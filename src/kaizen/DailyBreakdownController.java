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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import kaizen.UserData.KaizenDatabase;


public class DailyBreakdownController implements Initializable {

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
    @FXML private Button logInTime;
    
    //barchart
    @FXML private BarChart<String, Number> dailyBarChart;
    //datepicker
    @FXML private DatePicker dailyChartDtPicker;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    private void loadGraph(ActionEvent event){
        dailyBarChart.getData().clear();
        
        try{
            LocalDate date = dailyChartDtPicker.getValue();
            //test date output:
            System.out.println("*"+ date);
            XYChart.Series<String, Number> dailySeries = new XYChart.Series<String,Number>();
            //if(date.equals(today);
            ResultSet daily = db.getResultSet("SELECT CATEGORYNAME, DURATION FROM TIMESHEETS "
                    + "WHERE DATE = '" + date + "'");
           
            
            ArrayList<String> categoryNameList = new ArrayList();
            ArrayList<Integer> durationList = new ArrayList();
            //test arraylist output:
            System.out.println(categoryNameList);
            //test output:
            System.out.println(daily);
            
            while (daily.next()){
                categoryNameList.add(daily.getString(1));
                durationList.add(daily.getInt(2));
                
                for(int i = 0; i<categoryNameList.size(); i++){
                    dailySeries.getData().add(new XYChart.Data(categoryNameList.get(i), durationList.get(i)));
                }
            //test categoryNameList output
            System.out.println("*" + categoryNameList);
            System.out.println("*" + durationList);
            
            
            }
            dailyBarChart.getData().addAll(dailySeries);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    
    
    @FXML
    private void handleDailyBreakdown(ActionEvent event) throws IOException{
        //pageSwitcher.switcher(event, "DailyBreakdown.fxml");
    }
    @FXML
    private void handleWeeklyBreakdown(ActionEvent event) throws IOException{
        //pageSwitcher.switcher(event, "WeeklyBreakdown.fxml");
    }
    @FXML
    private void handleWeeklyTrends(ActionEvent event) throws IOException{
        //pageSwitcher.switcher(event, "WeeklyTrends.fxml");
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
