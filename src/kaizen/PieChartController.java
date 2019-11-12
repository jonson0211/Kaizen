package kaizen;

import com.sun.javafx.charts.Legend;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    @FXML private ToggleButton dailyLearnings;
    @FXML private ToggleButton settings;
    
    @FXML private Label welcome;
    @FXML private Label welcomeSubheading;
    //menu^//
    
    @FXML private Button dailyBreakdown;
    @FXML private Button weeklyBreakdown;
    @FXML private Button weeklyTrends;
    
    
    @FXML private Label workLabel;
    @FXML private Label relationshipsLabel;
    @FXML private Label projectsLabel;
    @FXML private Label wellnessLabel;
    @FXML private Label dailyLabel;
    @FXML private Label relaxationLabel;
    @FXML private Button logInTime;
    @FXML private GridPane grid;
    @FXML private Button tsBtn;
    
    @FXML public PieChart lifePieChart;
    
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
    ResultSet workRs = db.getResultSet("SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Work' "
                    );
        int workCount = workRs.getInt(1);
            //workLabel.setText(String.valueOf(workRs.getInt(1)));
    
    ResultSet relationshipsRs = db.getResultSet("SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Relationships' "
                    );
        int relationshipCount = relationshipsRs.getInt(1);
            //relationshipsLabel.setText(String.valueOf(relationshipsRs.getInt(1)));

    ResultSet wellnessRs = db.getResultSet(
            "SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Wellness' "
                    );
        int wellnessCount = wellnessRs.getInt(1);
            //wellnessLabel.setText(String.valueOf(wellnessRs.getInt(1))); 
    
    ResultSet relaxationRs = db.getResultSet(
            "SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Relaxation' "
                    );
        int relaxationCount = relaxationRs.getInt(1);
            //relaxationLabel.setText(String.valueOf(relaxationRs.getInt(1)));
                
    ResultSet projectsRs = db.getResultSet(
            "SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Projects' "
                    );
        int projectsCount = projectsRs.getInt(1);
            //projectsLabel.setText(String.valueOf(projectsRs.getInt(1))); 
            
    ResultSet dailyRs = db.getResultSet(
            "SELECT SUM(DURATION) from TIMESHEETS "
                    + "WHERE CATEGORYNAME = 'Daily' "
                    );
        int dailyCount = dailyRs.getInt(1);
            //dailyLabel.setText(String.valueOf(dailyRs.getInt(1)));        
        
    double totalDuration = (workRs.getInt(1)
            +relationshipsRs.getInt(1)
            +projectsRs.getInt(1)
            +wellnessRs.getInt(1)
            +dailyRs.getInt(1)
            +relaxationRs.getInt(1));
    
    System.out.println( "*" + Math.round((workRs.getInt(1)/totalDuration)*100) );
    
    ResultSet totalDuration1=db.getResultSet("SELECT SUM(DURATION) from TIMESHEETS");
                double totalDurationSum = totalDuration1.getInt(1);
    System.out.println(workRs.getInt(1));
    System.out.println(workRs.getInt(1)/totalDurationSum);
    System.out.println(totalDuration);
    System.out.println("*" + projectsRs.getInt(1));
    
    
        try {
                lifePieChart.getData().clear();
                ObservableList<PieChart.Data> lifePieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Work " + (Math.round((workRs.getInt(1)/totalDuration)*100)) + "%"
                            + " (" + (Math.round((workRs.getInt(1)/60))) + " hours)", workRs.getInt(1)),
                    new PieChart.Data("Relationships " + Math.round((relationshipsRs.getInt(1)/totalDuration)*100) + "%"
                            + " (" + (Math.round((relationshipsRs.getInt(1)/60))) + " hours)", relationshipsRs.getInt(1)),
                    new PieChart.Data("Projects " + Math.round((projectsRs.getInt(1)/totalDuration)*100) + "%"
                            + " (" + (Math.round((projectsRs.getInt(1)/60))) + " hours)", projectsRs.getInt(1)),
                    new PieChart.Data("Wellness " + Math.round((wellnessRs.getInt(1)/totalDuration)*100) + "%"
                            + " (" + (Math.round((wellnessRs.getInt(1)/60))) + " hours)", wellnessRs.getInt(1)),
                    new PieChart.Data("Daily "+ Math.round((dailyRs.getInt(1)/totalDuration)*100) + "%"
                            + " (" + (Math.round((dailyRs.getInt(1)/60))) + " hours)", dailyRs.getInt(1)),
                    new PieChart.Data("Relaxation " + Math.round((relaxationRs.getInt(1)/totalDuration)*100) + "%"
                            + " (" + (Math.round((relaxationRs.getInt(1)/60))) + " hours)", relaxationRs.getInt(1)));
                                      
                    //System.out.println("Test");
                lifePieChart.setData(lifePieChartData);
        
//colors - please REFERENCE BLAIR'S DEMO IN THE ABOUT SCREEN WHEN WE FINISH- taken straight from the demo:       
        String[] pieColors = {"#80bfff", "#cc99ff", "#ccffff", "#80ff80", "#ff80ff","#ffb84d"};
        int i = 0;
        for (PieChart.Data data : lifePieChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
        i = 0;
        for (Node n : lifePieChart.getChildrenUnmodifiable()) {
            if (n instanceof Legend) {
                Legend l = (Legend) n;
                for (Legend.LegendItem li : l.getItems()) {
                    Node thisNode = li.getSymbol();
                    thisNode.setStyle(
                            "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
                    );
                    i++;
                }
            }
        }   
            } catch (Exception e) {
                System.out.println("Unable to produce Pie Chart!");
                e.printStackTrace();

        }
    return lifePieChart;
    }
    
    
    //switch to daily learnings
    @FXML
    private void handlePopUpScreenAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timesheets.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New entry");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load this new window!");
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
        pageSwitcher.switcher(event,"PieChart.fxml"); 
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
    private void handleEntries(ActionEvent event) throws IOException{
        pageSwitcher.switcher(event,"EntriesScreen.fxml"); 
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
