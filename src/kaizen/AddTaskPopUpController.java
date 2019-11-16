package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.colourDM;

import java.util.ArrayList;

import javafx.stage.Stage;

public class AddTaskPopUpController implements Initializable {

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
    private Button submit;
    @FXML
    private Button exit;
    @FXML
    private Label success;

    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<colourDM> colourShapeList = FXCollections.observableArrayList();

    KaizenDatabase database = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

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

    //get Category Choice for combo box
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

    public ObservableList<colourDM> getColourChoice() {

        ObservableList<colourDM> colourList = FXCollections.observableArrayList();

        try {
            ResultSet rsColourList = database.getResultSet("SELECT DISTINCT(CATEGORYNAME), COLOUR FROM TIMESHEETS");

            while (rsColourList.next()) {
                colourShapeList.add(new colourDM(rsColourList.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(colourShapeList);
    }

    @FXML
    private void handleInputChangedAction(ActionEvent event) throws SQLException {

        ArrayList<String> colourList = new ArrayList<String>();
        //getColourChoice();
        String catName = categoryComboBox.getValue();

        ResultSet catColourRs = database.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                + "WHERE CATEGORYNAME = '" + catName + "'");
        while (catColourRs.next()) {
            colourList.add(catColourRs.getString(2));
        };

        String colourString = colourList.get(0);
        categoryColourShape.setFill(Color.web(colourString));

        categoryColourShape.setVisible(true);

    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException, ParseException {

        String title = titleTextField.getText();
        String catName = categoryComboBox.getValue();
        String desc = descriptionText.getText();
        String priority = priorityTextField.getText();

        String doDate = DoDtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dueDate = DueDtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {

            database.insertStatement(
                    "INSERT INTO TASKS ("
                    + "TITLE, CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY)"
                    + " VALUES('" + title + "', '"
                    + catName + "', '" + desc + "','"
                    + doDate + "', '" + dueDate + "', '"
                    + priority + "');"
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
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }

    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen");
    }

}
