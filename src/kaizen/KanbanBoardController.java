/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author Raymond
 */
public class KanbanBoardController implements Initializable{
    
    
    PageSwitchHelper psh = new PageSwitchHelper();
    
    @FXML
    private Button signOutButton;
    @FXML
    private ToggleButton settingsButton;
    @FXML
    private ToggleButton kanbanBoard;
    @FXML
    private ToggleButton deepFocus;
    @FXML
    private ToggleButton taskTracker;
    @FXML
    private ToggleButton dailyLearnings;
    @FXML
    private ToggleButton about;
    @FXML
    private ToggleButton timeDashboard;
    @FXML
    private ToggleButton doDateView;
    @FXML
    private ToggleButton dueDateView;
    @FXML
    private Label welcome;
    @FXML
    private Label welcomeSubheading;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Loeading Kanban board");
        
    }
    
    
    //method to change the scene from do date mode to due date mode
    @FXML
    public void handleDueDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDueDateView.fxml");
    }
    
    //method to change the scene from due date mode back to the default do date mode
    @FXML
    public void handleDoDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDateView.fxml");
    }
    
    //switch to about
    @FXML
    public void handleAboutScreen(ActionEvent event) throws IOException {
        psh.switcher(event, "AboutScreen.fxml");
    }
    
    //switch to daily learnings
    @FXML
    public void handleDailyLearnings(ActionEvent event) throws IOException {
        psh.switcher(event, "DailyLearnings.fxml");
    }
    
    //switch to deep focus mode
    @FXML
    public void handleDeepFocusMode(ActionEvent event) throws IOException {
        psh.switcher(event, "DeepFocusMode.fxml");
    }
    
    //switch to register screen
    @FXML
    public void handleSignOut(ActionEvent event) throws IOException {
        psh.switcher(event, "LoginScreen.fxml");
    }
    
    //switch to time dashboard
    @FXML
    public void handleTimeDashboard(ActionEvent event) throws IOException {
        psh.switcher(event, "PieChart.fxml");
    }
    
    @FXML
    public void handleTaskTracker(ActionEvent event) throws IOException{
        psh.switcher(event, "Timesheets.fxml");
    }
    
    @FXML
    public void initialize() {
        //for every entry in database, display data
        System.out.println("OAIdfjif");
    }
    
}
