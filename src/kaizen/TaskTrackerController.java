package kaizen;

import kaizen.DataModels.TaskDM;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import kaizen.UserData.KaizenDatabase;

public class TaskTrackerController implements Initializable {

    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    private Button addTask;
    @FXML
    private Button updateTask;
    @FXML
    private Button deleteTask;

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

    @FXML
    private TableColumn<TaskDM, String> IDColumn;

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

            while (rs.next()) {
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

    @FXML
    private void editRow(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("EditTaskPopUp.fxml"));

                try {
                    Loader.load();
            EditTaskPopUpController a = Loader.getController();
                a.setTaskData(""+ taskList.getSelectionModel().getSelectedItem().getTaskID(),
                        taskList.getSelectionModel().getSelectedItem().getTitle(),
                        taskList.getSelectionModel().getSelectedItem().getCategory(), 
                        taskList.getSelectionModel().getSelectedItem().getDoDate(), 
                        taskList.getSelectionModel().getSelectedItem().getDueDate(), 
                        taskList.getSelectionModel().getSelectedItem().getDescription(), 
                        ""+taskList.getSelectionModel().getSelectedItem().getPriority());
                Parent p = Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No entry selected");
            alert.setHeaderText("Please select an entry!");
            alert.showAndWait();
                 ex.printStackTrace();

                    Logger.getLogger(EntriesScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

                

    }

    @FXML
    private void deleteRow(ActionEvent event) {
        TaskDM selected = taskList.getSelectionModel().getSelectedItem();

        try {
            database.insertStatement("DELETE FROM TASKS WHERE"
                    + " TITLE = '" + selected.getTitle() + "'"
                    + "AND CATEGORYNAME = '" + selected.getCategory() + "'"
                    + "AND DESCRIPTION = '" + selected.getDescription() + "'"
                    + "AND DO_DATE = '" + selected.getDoDate() + "'"
                    + "AND DUE_DATE = '" + selected.getDueDate() + "'"
                    + "AND PRIORITY = '" + selected.getPriority() + "'"
                    + "AND TASK_ID = '" + selected.getTaskID() + "'"
            );

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No entry selected");
            alert.setHeaderText("Please select an entry!");
            alert.showAndWait();
            System.out.println("Can't delete from database!");
            e.printStackTrace();
        }
        try {
            taskList.getItems().removeAll(taskList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("can't remove from table");
            e.printStackTrace();
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReportBugPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Report a bug");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load this new window!");
        }
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
    private void handleSettings(ActionEvent event) throws IOException {
        psh.switcher(event, "Settings.fxml"); //TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    public void handleTimesheets(ActionEvent event) throws IOException {
        psh.switcher(event, "Timesheets.fxml");
    }
}
