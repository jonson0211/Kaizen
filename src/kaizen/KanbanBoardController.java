/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import kaizen.UserData.KaizenDatabase;
import static kaizen.UserData.KaizenDatabase.conn;

/**
 *
 * @author Raymond
 */
public class KanbanBoardController implements Initializable {

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

    @FXML
    private GridPane grid;

    KaizenDatabase KanbanDatabase = new KaizenDatabase();

//    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Loading Kanban board");

//        DISPLAY ITEMS IN GRIDPANE BASED ON DATABASE
        try {
            ResultSet rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE = '2019-11-18'");
            makeElements(rs, 0);
            
            rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE = '2019-11-19'");
            makeElements(rs, 2);
            
            rs = KanbanDatabase.getResultSet("SELECT * FROM TASKS WHERE DO_DATE > '2019-11-19'");
            makeElements(rs, 3);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void makeElements(ResultSet rs, int num) {
        for (int i = 0; i < 7; i++) {
            try {
                if (rs.next()) {

                } else {
                    break;
                }
                //need to edit
                VBox vbox = new VBox();
                vbox.getChildren().addAll(new Label(rs.getString("TITLE")), new Label("Do: " + rs.getString("DO_DATE")), new Label("Due: " + rs.getString("DUE_DATE")), new Label("Priority: " + rs.getString("PRIORITY")));
                Button b = new Button("", vbox);
                grid.add(b, num, i);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }


    //method to change the scene from do date mode to due date mode
    @FXML
    public void handleDueDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDueDateView.fxml");
    }

    //method to change the scene from due date mode back to the default do date mode
    @FXML
    public void handleDoDateView(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoardDoDateView.fxml");
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

    @FXML
    public void handleTaskTracker(ActionEvent event) throws IOException {
        psh.switcher(event, "TaskTracker.fxml");
    }

    //switch to time dashboard
    @FXML
    public void handleTimeDashboard(ActionEvent event) throws IOException {
        psh.switcher(event, "PieChart.fxml");
    }

    @FXML
    public void handleTimesheets(ActionEvent event) throws IOException {
        psh.switcher(event, "Timesheets.fxml");
    }

}
