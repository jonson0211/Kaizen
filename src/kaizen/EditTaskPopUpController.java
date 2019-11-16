package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.colourDM;

public class EditTaskPopUpController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private DatePicker DoDtPicker;
    @FXML
    private DatePicker DueDtPicker;
    @FXML
    private TextArea descriptionText;
    @FXML
    private TextField priorityTextField;
    @FXML
    private Rectangle categoryColourShape;
    @FXML
    private TextField IDTextField;
    @FXML
    private Button update;
    @FXML
    private Button exit;
    @FXML
    private Label success;

    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<colourDM> colourShapeList = FXCollections.observableArrayList();

    KaizenDatabase database = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        success.setVisible(false);
        categoryColourShape.setVisible(false);
        categoryComboBox.setEditable(true);

        categoryComboList.setAll(this.getCatChoice());
        for (categoryCombo c : categoryComboList) {
            System.out.println(c.getCatChoiceProperty());
            categoryComboBox.getItems().addAll(c.getCatChoice());
        }

    }

    public void setTaskData(String ID, String title, String category, String doDate, String dueDate, String description, String priority) {
        titleTextField.setText(title);
        categoryComboBox.setValue(category);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate doDateParsed = LocalDate.parse(doDate, formatter);
        DoDtPicker.setValue(doDateParsed);
        LocalDate dueDateParsed = LocalDate.parse(dueDate, formatter);
        DueDtPicker.setValue(dueDateParsed);
        IDTextField.setText(ID);
        descriptionText.setText(description);
        priorityTextField.setText(priority);

    }

    @FXML
    private void handleUpdate(ActionEvent event) throws SQLException, ParseException {
        String taskID = IDTextField.getText();
        String title = titleTextField.getText();
        String category = categoryComboBox.getValue();
        String desc = descriptionText.getText();
        String doDate = DoDtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dueDate = DueDtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String priority = priorityTextField.getText();

        try {

            database.insertStatement(
                    "UPDATE TASKS SET "
                    + "TITLE = '" + title + "'"
                    + ", CATEGORYNAME = '" + category + "'"
                    + ", DESCRIPTION = '" + desc + "'"
                    + ", DO_DATE = '" + doDate + "'"
                    + ", DUE_DATE = '" + dueDate + "'"
                    + ", PRIORITY = '" + priority + "'"
                    + " WHERE TASK_ID = '" + taskID + "'"
            );

            success.setVisible(true);

        } catch (Exception ex) {
            System.out.println("Could not add entry. Please check your inputs!");
            ex.printStackTrace();
        }

    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleInputChangedAction(ActionEvent event) throws SQLException {

        String catName = categoryComboBox.getValue();
        ResultSet catColourRs = database.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                + "WHERE CATEGORYNAME = '" + catName + "'"
        );

        String colourShape = catColourRs.getString(2);

        if (catName.equals("Work")) {
            categoryColourShape.setFill(Color.web("#80bfff"));
        }
        if (catName.equals("Wellness")) {
            categoryColourShape.setFill(Color.web("#80ff80"));
        }
        if (catName.equals("Relationships")) {
            categoryColourShape.setFill(Color.web("#cc99ff"));
        }
        if (catName.equals("Projects")) {
            categoryColourShape.setFill(Color.web("#ccffff"));
        }
        if (catName.equals("Daily")) {
            categoryColourShape.setFill(Color.web("#ff80ff"));
        }
        if (catName.equals("Relaxation")) {
            categoryColourShape.setFill(Color.web("#ffb84d"));
        }
        //else{ categoryColourShape.setFill(Color.TRANSPARENT);}

        categoryColourShape.setVisible(true);

    }

    public ObservableList<categoryCombo> getCatChoice() {

        ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();

        try {
            ResultSet rsCategoryComboTable = database.getResultSet("SELECT DISTINCT(CATEGORYNAME) FROM CATEGORY");

            while (rsCategoryComboTable.next()) {
                categoryComboList.add(new categoryCombo(rsCategoryComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(categoryComboList);
    }

    //get activity choice for combo box
    public ObservableList<activityCombo> getActChoice() {

        ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();

        try {
            ResultSet rsActivityComboTable = database.getResultSet("SELECT DISTINCT(ACTIVITY) FROM TIMESHEETS");

            while (rsActivityComboTable.next()) {
                activityComboList.add(new activityCombo(rsActivityComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(activityComboList);
    }

    @FXML
    private void handleKbBoard(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
    }

    @FXML
    private void handleDeepFocus(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DeepFocusMode.fxml");
    }

    @FXML
    private void handleTaskTracker(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "TaskTracker.fxml");
    }

    @FXML
    private void handleTimeSheets(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Timesheets.fxml");
    }

    @FXML
    private void handleDailyLearnings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Settings.fxml"); //TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    private void handleSignOut(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "ReportBugPopUp.fxml");
    }

    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen.fxml");
    }

}
