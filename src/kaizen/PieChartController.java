package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import kaizen.UserData.KaizenDatabase;

public class PieChartController implements Initializable {
    
    KaizenDatabase db = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    //menu underneath//
    @FXML
    private Button signOutButton;
    @FXML
    private ToggleButton settingsButton;
    @FXML
    private ToggleButton kanbanBoard;
    @FXML private ToggleButton deepFocus;
    @FXML private ToggleButton taskTracker;
    @FXML private ToggleButton about;
    @FXML private ToggleButton timeDashboard;
    
    @FXML private Label welcome;
    @FXML private Label welcomeSubheading;
    //menu^//
    
    @FXML public PieChart lifePieChart;
    
    @FXML private Button dailyBreakdown;
    
    @FXML private Button weeklyBreakdown;
    
    @FXML private Button weeklyTrends;
    
    @FXML private Label workLabel;
    @FXML private Label relationshipsLabel;
    @FXML private Label projectsLabel;
    @FXML private Label wellnessLabel;
    @FXML private Label dailyLabel;
    @FXML private Label relaxationLabel;
    @FXML private GridPane grid;
            
    ObservableList<PieChart.Data> lifePieChartData;
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buildPie();
        } catch (SQLException ex) {
            Logger.getLogger(PieChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
       PieChart lifePieChart = new PieChart(lifePieChartData);
       
  
    }
   
        //Getting time spent data for piechart
    public PieChart buildPie() throws SQLException{    
    ResultSet workRs = db.getResultSet("SELECT SUM(START) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Work' "
                    );
        int workCount = workRs.getInt(1);
            //workLabel.setText(String.valueOf(workRs.getInt(1)));
    
    ResultSet relationshipsRs = db.getResultSet("SELECT SUM(START) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Relationships' "
                    );
        int relationshipCount = relationshipsRs.getInt(1);
            //relationshipsLabel.setText(String.valueOf(relationshipsRs.getInt(1)));

    ResultSet wellnessRs = db.getResultSet(
            "SELECT SUM(START) from TIMESHEETS "
                    //+ "WHERE CATEGORYNAME = 'Wellness' "
                    );
        int wellnessCount = wellnessRs.getInt(1);
            //wellnessLabel.setText(String.valueOf(wellnessRs.getInt(1))); 
    
    ResultSet relaxationRs = db.getResultSet(
            "SELECT SUM(START) from TIMESHEETS "
                    //+ "WHERE CATEGORYNAME = 'Relaxation' "
                    );
        int relaxationCount = relaxationRs.getInt(1);
            //relaxationLabel.setText(String.valueOf(relaxationRs.getInt(1)));
                
    ResultSet projectsRs = db.getResultSet(
            "SELECT SUM(START) from TIMESHEETS "
                    //+ "WHERE CATEGORYNAME = 'Projects' "
                    );
        int projectsCount = projectsRs.getInt(1);
            //projectsLabel.setText(String.valueOf(projectsRs.getInt(1))); 
            
    ResultSet dailyRs = db.getResultSet(
            "SELECT SUM(START) from TIMESHEETS "
                    //+ "WHERE CATEGORYNAME = 'Daily' "
                    );
        int dailyCount = dailyRs.getInt(1);
            //dailyLabel.setText(String.valueOf(dailyRs.getInt(1)));        
        
        try {
                lifePieChart.getData().clear();
                ObservableList<PieChart.Data> lifePieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Work", workRs.getInt(1)),
                    new PieChart.Data("Relationships", relationshipsRs.getInt(1)),
                    new PieChart.Data("Projects", projectsRs.getInt(1)));
                    new PieChart.Data("Wellness", wellnessRs.getInt(1));
                    new PieChart.Data("Daily", dailyRs.getInt(1));
                    new PieChart.Data("Relaxtion", relaxationRs.getInt(1));
                                      
                    System.out.println("Test");
                lifePieChart.setData(lifePieChartData);
                   
            } catch (Exception e) {
                System.out.println("Unable to produce Pie Chart!");
                e.printStackTrace();

        }
    return lifePieChart;
    }
    
    
    //switch to daily learnings
       
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
        //pageSwitcher.switcher(event,"Timesheets.fxml"); 
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
