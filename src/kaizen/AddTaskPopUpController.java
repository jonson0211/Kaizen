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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.colourDM;
import kaizen.DataModels.learningsDidWell;
import kaizen.DataModels.timesheetsDM;
import java.awt.*;
import java.awt.color.*;
import java.time.Duration;
import java.time.LocalDate;
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

        //if doesn't work, jsut switch to color.RED etc and change data
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
