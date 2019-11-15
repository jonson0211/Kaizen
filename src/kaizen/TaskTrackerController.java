package kaizen;

import kaizen.DataModels.TaskDM;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import kaizen.DataModels.timesheetsDM;
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
public class TaskTrackerController implements Initializable {
    
    PageSwitchHelper psh = new PageSwitchHelper();
    
    @FXML private Button addTask;
    
    @FXML
    private TableView<TaskDM> taskList;
    public static TableView<TaskDM> taskList2;
    
    @FXML
    private TableColumn<TaskDM, String> titleColumn;
    @FXML
    private TableColumn<TaskDM, String> descriptionColumn;
    @FXML
    private TableColumn<TaskDM, String> categoryNameColumn;
    @FXML
    private TableColumn<TaskDM, String> doDateColumn;
    @FXML
    private TableColumn<TaskDM, String> dueDateColumn;
    @FXML
    private TableColumn<TaskDM, String> priorityColumn;
    
    @FXML private TableColumn<TaskDM, String> IDColumn;
    
    
    KaizenDatabase database = new KaizenDatabase();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        taskList2 = taskList;
        taskList.setItems(this.getTaskListData());
        taskList.setVisible(true);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        categoryNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
        doDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDoDateProperty());
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDueDateProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().getPriorityProperty());
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().getTaskIDProperty());
         
    }
    
    public ObservableList<TaskDM> getTaskListData() {
         
        ObservableList<TaskDM> taskListToReturn = FXCollections.observableArrayList();
         
        
        
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM TASKS");
            
            while (rs.next()){
                taskListToReturn.add(new TaskDM(rs.getString("TASK_ID"), 
                        rs.getString("TITLE"), rs.getString("DESCRIPTION"), 
                        rs.getString("CATEGORYNAME"), rs.getString("DO_DATE"), 
                        rs.getString("DUE_DATE"), rs.getString("PRIORITY")
                        ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(taskListToReturn);
        
    }
    
    @FXML
    private void handleAddTaskPopUp(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTaskPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New entry");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) { 
            e.printStackTrace();
            
            System.out.println("Cannot load this new window!!");
            
        }
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

