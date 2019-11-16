package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;

public class EditEntriesPopUpController implements Initializable {

    @FXML
    private TextField timeStartHrField;

    @FXML
    private TextField timeEndHrField;

    @FXML
    private TextField timeStartMinField;
    @FXML
    private TextField timeEndMinField;
    @FXML
    private TextField timeIDField;

    @FXML
    private Label durationLabel;

    @FXML
    private TextArea descriptionText;

    @FXML
    private ComboBox<String> tsCombo;

    @FXML
    private Rectangle categoryColourShape;

    @FXML
    private Button submit;

    @FXML
    private Button back;

    @FXML
    private DatePicker DtPicker;

    @FXML
    private Label success;

    @FXML
    private Button exit;

    ToggleGroup toggleGroup = new ToggleGroup();

    KaizenDatabase addTimesheet = new KaizenDatabase();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    ComboBox<String> activityComboBox;
    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        success.setVisible(false);
        durationLabel.setVisible(false);
        categoryColourShape.setVisible(false);
        categoryComboBox.setEditable(true);
        activityComboBox.setEditable(true);

        categoryComboList.setAll(this.getCatChoice());
        for (categoryCombo c : categoryComboList) {
            System.out.println(c.getCatChoiceProperty());
            categoryComboBox.getItems().addAll(c.getCatChoice());
        }
        activityComboList.setAll(this.getActChoice());
        for (activityCombo d : activityComboList) {
            System.out.println(d.getActChoiceProperty());
            activityComboBox.getItems().addAll(d.getActChoice());
        }

    }

    public void setData(String ID, String string, String category, String date, String description, Integer duration, String start, String end) {
        activityComboBox.setValue(string);
        categoryComboBox.setValue(category);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateParsed = LocalDate.parse(date, formatter);
        DtPicker.setUserData(dateParsed);
        timeIDField.setText(ID);
        descriptionText.setText(description);
        durationLabel.setUserData(duration);

        timeStartHrField.setText(start);
        timeEndHrField.setText(end);

        //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void handleUpdate(ActionEvent event) throws SQLException, ParseException {
        String timeID = timeIDField.getText();
        String act = activityComboBox.getValue();
        String category = categoryComboBox.getValue();
        String desc = descriptionText.getText();
        String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String start = timeStartHrField.getText();
        String end = timeEndHrField.getText();
        DateFormat sdf = new SimpleDateFormat("hh:mm aa");
        Date startTime = sdf.parse(start);
        Date endTime = sdf.parse(end);

        double dayDuration = Duration.ofHours(24).toHours();

        double diffMs = endTime.getTime() - startTime.getTime();
        System.out.print("*" + diffMs);
        double diffSec = diffMs / 1000;
        double minCalc = diffSec / 60;

        System.out.println("The difference is " + minCalc + " minutes");

        String durationText = Double.toString(minCalc);
        try {
            addTimesheet.insertStatement("UPDATE TIMESHEETS"
                    + " SET CATEGORYNAME = '" + category + "'"
                    + ", ACTIVITY = '" + act + "' "
                    + ", DATE = '" + date + "'"
                    + ", DESCR = '" + desc + "'"
                    + "WHERE TIMESHEETID = '" + timeID + "'"
            );
            success.setText("Entry updated!");
            success.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not update database! Please check your inputs.");
            success.setText("Could not update database! Please check your inputs.");
        }

    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleInputChangedAction(ActionEvent event) throws SQLException {
        ArrayList<String> colourList = new ArrayList<String>();
        //getColourChoice();
        String catName = categoryComboBox.getValue();
        ResultSet catColourRs = addTimesheet.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                + "WHERE CATEGORYNAME = '" + catName + "'");
        while (catColourRs.next()) {
            colourList.add(catColourRs.getString(2));
        };

        String colourString = colourList.get(0);
        categoryColourShape.setFill(Color.web(colourString));
        categoryColourShape.setVisible(true);

    }

    public ObservableList<categoryCombo> getCatChoice() {

        ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();

        try {
            ResultSet rsCategoryComboTable = addTimesheet.getResultSet("SELECT DISTINCT(CATEGORYNAME) FROM CATEGORY");

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
            ResultSet rsActivityComboTable = addTimesheet.getResultSet("SELECT DISTINCT(ACTIVITY) FROM TIMESHEETS");

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
        pageSwitcher.switcher(event, "TaskTracker.fxml");//TO CHANGE WHEN PAGE IS MADE
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

    //To change body of generated methods, choose Tools | Templates.
}
