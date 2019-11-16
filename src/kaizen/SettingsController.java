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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kaizen.DataModels.categoryTableDM;
import kaizen.DataModels.errorsDM;
import kaizen.UserData.KaizenDatabase;

public class SettingsController implements Initializable {

    KaizenDatabase db = new KaizenDatabase();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    //menu underneath//
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
    private ToggleButton about;
    @FXML
    private ToggleButton timeDashboard;
    @FXML
    private ToggleButton dailyLearnings;

    @FXML
    private Label welcome;
    @FXML
    private Label welcomeSubheading;
    //menu^//

    @FXML
    private Button editActivities;
    @FXML
    private Button editCategories;
    @FXML
    private Button weeklyTrends;

    @FXML
    private Button logInTime;
    @FXML
    private GridPane grid;
    @FXML
    private Button tsBtn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Label status;
    @FXML
    private Button help;
    @FXML
    private Button report;

    @FXML
    public TableView<categoryTableDM> catView;
    public static TableView<categoryTableDM> catView_2;

    @FXML
    private TableColumn<categoryTableDM, String> colourClm;
    @FXML
    private TableColumn<categoryTableDM, String> categoryClm;
    @FXML
    private TableColumn<categoryTableDM, String> IDClm;

    @FXML
    private TableView<errorsDM> errorsView;
    public static TableView<errorsDM> errorsView_2;

    @FXML
    private TableColumn<errorsDM, String> errorIDClm;
    @FXML
    private TableColumn<errorsDM, String> errorNameClm;
    @FXML
    private TableColumn<errorsDM, String> errorDateClm;
    @FXML
    private TableColumn<errorsDM, String> errorDescClm;
    @FXML
    private TableColumn<errorsDM, String> errorPageClm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.setVisible(false);
        initTable();
        loadData();

        errorsView_2 = errorsView;
        errorsView.setVisible(true);
        errorsView.setItems(this.getErrors());

        errorDateClm.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        errorNameClm.setCellValueFactory(cellData -> cellData.getValue().getErrorNameProperty());
        errorPageClm.setCellValueFactory(cellData -> cellData.getValue().getErrorPageProperty());
        errorDescClm.setCellValueFactory(cellData -> cellData.getValue().getErrorDescProperty());
        errorIDClm.setCellValueFactory(cellData -> cellData.getValue().getErrorIDProperty());

    }

    private void initTable() {
        initCols();
    }

    private void initCols() {
        categoryClm.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colourClm.setCellValueFactory(new PropertyValueFactory<>("colour"));
        IDClm.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        editableCols();

    }

    private void editableCols() {
        categoryClm.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryClm.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategoryName(e.getNewValue());
        });

        colourClm.setCellFactory(TextFieldTableCell.forTableColumn());
        colourClm.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setColour(e.getNewValue());
        });

        IDClm.setCellFactory(TextFieldTableCell.forTableColumn());
        IDClm.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategoryID(e.getNewValue());
        });

        catView.setEditable(true);
    }

    private void loadData() {
        ObservableList<categoryTableDM> data_table = FXCollections.observableArrayList();

        try {
            ResultSet rs = db.getResultSet("SELECT * FROM CATEGORY");

            while (rs.next()) {
                data_table.add(new categoryTableDM(rs.getString("CATEGORYID"), rs.getString("CATEGORYNAME"), rs.getString("COLOUR")
                ));
                System.out.println(rs.getString("CATEGORYID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        catView.setItems(data_table);
    }

    @FXML
    private void deleteData(ActionEvent event) throws IOException {

        categoryTableDM selected = catView.getSelectionModel().getSelectedItem();

        try {
            db.insertStatement("DELETE FROM CATEGORY WHERE "
                    + "CATEGORYID = '" + selected.getCategoryID()
                    + "' AND CATEGORYNAME = '" + selected.getCategoryName()
                    + "' AND COLOUR = '" + selected.getColour() + "'");

            status.setText("Category deleted!");
            status.setVisible(true);
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            status.setText("Can't delete from database! Please try again.");
            status.setVisible(true);
            e.printStackTrace();
        }
        try {
            catView.getItems().removeAll(catView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("Can't remove from table");
            status.setText("Can't remove from table! Please try again.");
            status.setVisible(true);
            e.printStackTrace();
        }

    }

    @FXML
    private void editData(ActionEvent event) throws IOException {

        categoryTableDM selected = catView.getSelectionModel().getSelectedItem();

        try {
            db.insertStatement("UPDATE CATEGORY "
                    + "SET CATEGORYNAME = '" + selected.getCategoryName() + "'"
                    + ", COLOUR = '" + selected.getColour() + "'"
                    + " WHERE CATEGORYID = " + selected.getCategoryID());

//            System.out.println(selected.getCategoryName());
//            System.out.println(selected.getColour());
//            System.out.println(selected.getCategoryID());
            status.setText("Category updated!");
            status.setVisible(true);

        } catch (Exception e) {
            System.out.println("No row selected");
            status.setText("Please select row.");
            status.setVisible(true);
            e.printStackTrace();
        }
        try {
            //catView.getItems().removeAll(catView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("Can't update from table");
            e.printStackTrace();
        }

    }

    public ObservableList<errorsDM> getErrors() {

        ObservableList<errorsDM> errors = FXCollections.observableArrayList();

        try {
            ResultSet rsErrors = db.getResultSet("SELECT * FROM ERRORS");

            while (rsErrors.next()) {
                errors.add(new errorsDM(rsErrors.getString("ERRORID"),
                        rsErrors.getString("DATE"), rsErrors.getString("ERRORNAME"),
                        rsErrors.getString("ERRORPAGE"), rsErrors.getString("DESCRIPTION")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(errors);
    }

    //switch to daily learnings
    @FXML
    private void handlePopUpAddCategory(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add category");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load this new window!");
        }
    }

    @FXML
    private void handleReportBug(ActionEvent event) throws IOException {
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
    private void handleHelp(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editing the Category table");
        alert.setHeaderText("To DELETE: Select row and click delete. \n To UPDATE: Double-click an entry, edit, press ENTER, then click UPDATE.");
        alert.showAndWait();
    }

    @FXML
    private void handleDailyBreakdown(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyBreakdown.fxml");
    }

    @FXML
    private void handleWeeklyBreakdown(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "WeeklyBreakdown.fxml");
    }

    @FXML
    private void handleWeeklyTrends(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "WeeklyTrends.fxml");
    }

    @FXML
    private void handleLogInTime(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Timesheets.fxml");
    }

    @FXML
    private void handleKanbanBoard(ActionEvent event) throws IOException {
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
        pageSwitcher.switcher(event, "PieChart.fxml");
    }

    @FXML
    private void handleDailyLearnings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Settings.fxml");
    }

    @FXML
    private void handleTimeDashboard(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PieChart.fxml");
    }

    @FXML
    private void handleEntries(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "EntriesScreen.fxml");
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
        pageSwitcher.switcher(event, "AboutScreen.fxml");
    }

}
