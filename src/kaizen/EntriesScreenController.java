package kaizen;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kaizen.DataModels.timesheetsDM;
import kaizen.UserData.KaizenDatabase;

public class EntriesScreenController implements Initializable {

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    KaizenDatabase db = new KaizenDatabase();

    @FXML
    private ToggleButton handleKb;

    @FXML
    private ToggleButton deepFocus;
    @FXML
    private ToggleButton taskTracker;
    @FXML
    private ToggleButton timesheets;
    @FXML
    private ToggleButton dailyLearnings;
    @FXML
    private ToggleButton settings;
    @FXML
    private Button signOut;
    @FXML
    private Button update;
    @FXML
    private Label status;

    @FXML
    private TableView<timesheetsDM> entriesView;
    public static TableView<timesheetsDM> entriesView_2;

    @FXML
    private TableColumn<timesheetsDM, String> dateClm;
    @FXML
    private TableColumn<timesheetsDM, String> categoryClm;
    @FXML
    private TableColumn<timesheetsDM, String> activityClm;
    @FXML
    private TableColumn<timesheetsDM, String> startClm;
    @FXML
    private TableColumn<timesheetsDM, String> endClm;
    @FXML
    private TableColumn<timesheetsDM, Number> durationClm;
    @FXML
    private TableColumn<timesheetsDM, String> descriptionClm;
    @FXML
    private TableColumn<timesheetsDM, String> IDClm;
    @FXML
    private Button backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.setVisible(false);
        entriesView_2 = entriesView;
        entriesView.setVisible(true);
        entriesView.setItems(this.getEntries());

        dateClm.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        categoryClm.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
        activityClm.setCellValueFactory(cellData -> cellData.getValue().getActivityProperty());
        startClm.setCellValueFactory(cellData -> cellData.getValue().getStartProperty());
        endClm.setCellValueFactory(cellData -> cellData.getValue().getEndProperty());
        durationClm.setCellValueFactory(cellData -> cellData.getValue().getDurationProperty());
        descriptionClm.setCellValueFactory(cellData -> cellData.getValue().getDescProperty());
        IDClm.setCellValueFactory(cellData -> cellData.getValue().getTimesheetIDProperty());

    }

    public ObservableList<timesheetsDM> getEntries() {

        ObservableList<timesheetsDM> entries = FXCollections.observableArrayList();

        try {
            ResultSet rs = db.getResultSet("SELECT * FROM TIMESHEETS");

            while (rs.next()) {
                entries.add(new timesheetsDM(rs.getString("TIMESHEETID"),
                        rs.getString("ACTIVITY"), rs.getString("CATEGORYNAME"),
                        rs.getString("DATE"), rs.getString("DESCR"),
                        rs.getInt("DURATION"), rs.getString("START"),
                        rs.getString("END")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(entries);
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        timesheetsDM selected = entriesView.getSelectionModel().getSelectedItem();

        try {
            db.insertStatement("DELETE FROM TIMESHEETS WHERE DATE = '" + selected.getDate() + "' "
                    + "AND CATEGORYNAME = '" + selected.getCategory() + "'"
                    + " AND ACTIVITY = '" + selected.getActivity() + "'"
                    + " AND TIMESHEETID = '" + selected.getTimesheetID() + "'");
            status.setText("Entry deleted!");
            status.setVisible(true);
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            status.setText("Can't delete from database! Please try again.");
            status.setVisible(true);
            e.printStackTrace();
        }
        try {
            entriesView.getItems().removeAll(entriesView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("can't remove from table");
            status.setText("Can't remove from table. Please try again.");
            status.setVisible(true);

            e.printStackTrace();
        }
    }

    @FXML
    private void editRow(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("EditEntriesPopUp.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();

            Logger.getLogger(EntriesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

                EditEntriesPopUpController a = Loader.getController();
                try{
                a.setData(""+ entriesView.getSelectionModel().getSelectedItem().getTimesheetID(),
                        entriesView.getSelectionModel().getSelectedItem().getActivity(),
                        entriesView.getSelectionModel().getSelectedItem().getCategory(), 
                        entriesView.getSelectionModel().getSelectedItem().getDate(), 
                        entriesView.getSelectionModel().getSelectedItem().getDesc(), 
                        entriesView.getSelectionModel().getSelectedItem().getDuration(), 
                        entriesView.getSelectionModel().getSelectedItem().getStart(),
                        ""+entriesView.getSelectionModel().getSelectedItem().getEnd());
                Parent p = Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
                } catch(Exception ex){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No entry selected");
            alert.setHeaderText("Please select an entry!");
            alert.showAndWait();
                }

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
    private void handleAbout(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen.fxml");
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
    private void handleLearnings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleTimeSheets(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PieChart.fxml");
    }
}
