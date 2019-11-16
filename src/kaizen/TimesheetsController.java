package kaizen;

import javafx.scene.shape.Rectangle;
import java.io.IOException;
import static java.lang.System.exit;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
import kaizen.DataModels.activityCombo;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.colourDM;
import java.time.Duration;
import java.util.ArrayList;
import static javafx.application.Platform.exit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimesheetsController implements Initializable {

    @FXML
    private TextField timeStartHrField;
    @FXML
    private TextField timeEndHrField;

    @FXML
    private Label durationLabel;
    @FXML
    private TextArea descriptionText;
    @FXML
    private Rectangle categoryColourShape;
    @FXML
    private Button submit;
    @FXML
    private Button back;
    @FXML
    private DatePicker DtPicker;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> activityComboBox;
    @FXML
    private Label status;
    @FXML
            private Button exit1;

    ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();
    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();
    ObservableList<colourDM> colourShapeList = FXCollections.observableArrayList();

    ToggleGroup toggleGroup = new ToggleGroup();
    KaizenDatabase addTimesheet = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        durationLabel.setVisible(false);
        categoryColourShape.setVisible(false);
        categoryComboBox.setEditable(true);
        activityComboBox.setEditable(true);

        status.setVisible(false);

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

    //get Category Choice for combo box
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

    public ObservableList<colourDM> getColourChoice() {

        ObservableList<colourDM> colourList = FXCollections.observableArrayList();

        try {
            ResultSet rsColourList = addTimesheet.getResultSet("SELECT DISTINCT(CATEGORYNAME), COLOUR FROM TIMESHEETS");

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
        try {
            ResultSet catColourRs = addTimesheet.getResultSet("SELECT CATEGORYNAME, COLOUR from CATEGORY "
                    + "WHERE CATEGORYNAME = '" + catName + "'");
            while (catColourRs.next()) {
                colourList.add(catColourRs.getString(2));
            };

            String colourString = colourList.get(0);
            categoryColourShape.setFill(Color.web(colourString));
            categoryColourShape.setVisible(true);
        } catch (Exception e) {

        }
    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException, ParseException {
        try {
            String catName = categoryComboBox.getValue();
            String act = (String) activityComboBox.getValue();
            String desc = descriptionText.getText();
            String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String start = timeStartHrField.getText();
            String end = timeEndHrField.getText();
            //Handling time
            DateFormat sdf = new SimpleDateFormat("hh:mm aa");
            Date startTime = sdf.parse(start);
            Date endTime = sdf.parse(end);

            double dayDuration = Duration.ofHours(24).toMinutes();

            double diffMs = endTime.getTime() - startTime.getTime();
            System.out.print("*" + diffMs);
            double diffSec = diffMs / 1000;
            //double minCalc = diffSec / 60;

            double minCalc;
            if (diffMs < 0) {
                minCalc = diffSec / 60 + dayDuration;
            } else if (diffMs > 0) {
                minCalc = diffSec / 60;
            } else {
                minCalc = 0;
            }

            String durationText = Double.toString(minCalc);

            try {

                addTimesheet.insertStatement("INSERT INTO TIMESHEETS (CATEGORYNAME, ACTIVITY,DATE, START, END, DURATION, DESCR)"
                        + " VALUES('" + catName + "', '" + act + "', '" + date + "','" + start + "', '" + end + "', '"
                        + minCalc + "', '" + desc + "');");

                durationLabel.setText(durationText + " minutes");
                durationLabel.setVisible(true);
                status.setText("Entry added!");
                status.setVisible(true);

            } catch (Exception ex) {
                System.out.println("Could not add entry. Please check your inputs!");
                status.setText("Could not add entry. Please check your inputs.");
                status.setVisible(true);
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println("Could not add entry. Please check your inputs!");
            status.setText("Could not add entry. Please check your inputs.");
            status.setVisible(true);
            ex.printStackTrace();
        }
    }
//    @FXML
//    private void handleBackAction(ActionEvent event) throws IOException {
//        pageSwitcher.switcher(event, "PieChart.fxml");
//    }
    
    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit1.getScene().getWindow();
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
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen");
    }

}
