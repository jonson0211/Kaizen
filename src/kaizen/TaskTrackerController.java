package kaizen;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kaizen.UserData.KaizenDatabase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonso
 */
public class TaskTrackerController {
    
      PageSwitchHelper psh = new PageSwitchHelper();
    
    @FXML
    private TableView<Task> taskList;
    @FXML
    private TableColumn<Task, String> titleColumn;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, String> categoryNameColumn;
    @FXML
    private TableColumn<Task, String> doDateColumn;
    @FXML
    private TableColumn<Task, String> dueDateColumn;
    @FXML
    private TableColumn<Task, String> priorityColumn;
    
    KaizenDatabase database = new KaizenDatabase();
    
    @FXML
    public void initialize(){
        
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().getTitle());
        descriptionColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDescription());
        categoryNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().getCategory());
        doDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDoDate());
        dueDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().getDueDate());
        priorityColumn.setCellValueFactory(
                cellData -> cellData.getValue().getPriority());
        
        taskList.setItems(this.getTaskListData());
    }
    
    private ObservableList<Task> getTaskListData() {
        List<Task> taskListToReturn = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT TITLE, DESCRIPTION, CATEGORYNAME, DO_DATE, DUE_DATE, PRIORITY FROM TASKS");
            while (rs.next()) {
                taskListToReturn.add(
                        new Task(rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("CATEGORYNAME"), rs.getString("DO_DATE"), rs.getString("DUE_DATE"), rs.getString("PRIORITY"))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("returning task data");
        return FXCollections.observableArrayList(taskListToReturn);
    }
    

    
    //method to change the scene from due date mode back to the default do date mode
    @FXML
    public void HandleKanbanBoard(ActionEvent event) throws IOException {
        psh.switcher(event, "KanbanBoard.fxml");
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
    public void handleTimesheets(ActionEvent event) throws IOException{
        psh.switcher(event, "Timesheets.fxml");
    }
    }

