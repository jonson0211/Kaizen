package kaizen;

import com.sun.javafx.charts.Legend;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import kaizen.DataModels.timesheetsDM;
import static kaizen.EntriesScreenController.entriesView_2;
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
    @FXML private Button deleteButton;
    @FXML private Button editButton;

    @FXML
    public TableView<categoryTableDM> catView;
    public static TableView<categoryTableDM> catView_2;

    @FXML
    private TableColumn<categoryTableDM, String> colourClm;
    @FXML
    private TableColumn<categoryTableDM, String> categoryClm;
    @FXML private TableColumn<categoryTableDM, String> IDClm;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTable();
        loadData();
        
//        catView_2 = catView;
//        catView.setVisible(true);
//        catView.setItems(this.getCategoryData());
//
//        categoryClm.setCellValueFactory(cellData -> cellData.getValue().getCategoryNameProperty());
//        colourClm.setCellValueFactory(cellData -> cellData.getValue().getCategoryColourProperty());

    }

//    @FXML
//    private void deleteRow(ActionEvent event) {
//        categoryTableDM selected = catView.getSelectionModel().getSelectedItem();
//
//        try {
//            db.insertStatement("DELETE FROM CATEGORY WHERE CATEGORYNAME = '" + selected.getCategoryNameProperty() + "' "
//                    + "AND COLOUR = '" + selected.getCategoryColourProperty() + "'"
//                    );
//        } catch (Exception e) {
//            System.out.println("Can't delete from database!");
//            e.printStackTrace();
//        }
//        try {
//            catView.getItems().removeAll(catView.getSelectionModel().getSelectedItem());
//        } catch (Exception e) {
//            System.out.println("Can't remove from table");
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    private void editRow(ActionEvent event) {
//        FXMLLoader Loader = new FXMLLoader(getClass().getResource("EditEntriesPopUp.fxml"));
//
//        try {
//            Loader.load();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//
//            Logger.getLogger(EntriesScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        EditEntriesPopUpController a = Loader.getController();
//        a.setData("" + entriesView.getSelectionModel().getSelectedItem().getDate(),
//                entriesView.getSelectionModel().getSelectedItem().getActivity(),
//                entriesView.getSelectionModel().getSelectedItem().getStart(),
//                entriesView.getSelectionModel().getSelectedItem().getEnd(),
//                entriesView.getSelectionModel().getSelectedItem().getDuration(),
//                entriesView.getSelectionModel().getSelectedItem().getDesc(),
//                "" + entriesView.getSelectionModel().getSelectedItem().getCategory());
//        Parent p = Loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(p));
//        stage.show();
//
//    }

//    public ObservableList<categoryTableDM> getCategoryData() {
//
//        ObservableList<categoryTableDM> cat = FXCollections.observableArrayList();
//
//        try {
//            ResultSet rs = db.getResultSet("SELECT * FROM CATEGORY");
//
//            while (rs.next()) {
//                cat.add(new categoryTableDM(rs.getString("CATEGORYNAME"), rs.getString("COLOUR")
//                ));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return FXCollections.observableArrayList(cat);
//    }

    private void initTable(){
        initCols();
    }
    private void initCols(){
        categoryClm.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colourClm.setCellValueFactory(new PropertyValueFactory<>("colour"));
        IDClm.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        editableCols();
        
    }
    private void editableCols(){
        categoryClm.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryClm.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategoryName(e.getNewValue());
        });
        
        colourClm.setCellFactory(TextFieldTableCell.forTableColumn());
        colourClm.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setColour(e.getNewValue());
        });
        
        IDClm.setCellFactory(TextFieldTableCell.forTableColumn());
        IDClm.setOnEditCommit(e->{
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategoryID(e.getNewValue());
        });
        
        catView.setEditable(true);
    }
    
    private void loadData(){
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
    private void deleteData(ActionEvent event)throws IOException{
    
            categoryTableDM selected = catView.getSelectionModel().getSelectedItem();
        
        try{
            db.insertStatement("DELETE FROM CATEGORY WHERE "
                    +"CATEGORYID = '" + selected.getCategoryID() 
                    + "' AND CATEGORYNAME = '"+ selected.getCategoryName()
                    + "' AND COLOUR = '"+selected.getColour()+ "'");
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            e.printStackTrace();
        }
        try{
            catView.getItems().removeAll(catView.getSelectionModel().getSelectedItem());
        } catch(Exception e){
            System.out.println("can't remove from table");
            e.printStackTrace();
        }
            
                
    }
    @FXML
    private void editData(ActionEvent event)throws IOException{
    
            categoryTableDM selected = catView.getSelectionModel().getSelectedItem();
            
        try{
            db.insertStatement("UPDATE CATEGORY "
                    + "SET CATEGORYNAME = '" + selected.getCategoryName()+ "'"
                    + ", COLOUR = '" +selected.getColour()+ "'"
                    + " WHERE CATEGORYID = " + selected.getCategoryID());
            
            System.out.println(selected.getCategoryName());
            System.out.println(selected.getColour());
            System.out.println(selected.getCategoryID());
            
        } catch (Exception e) {
            System.out.println("Can't update from database!");
            e.printStackTrace();
        }
        try{
            //catView.getItems().removeAll(catView.getSelectionModel().getSelectedItem());
        } catch(Exception e){
            System.out.println("Can't update from table");
            e.printStackTrace();
        }
            
                
    }
    
    //switch to daily learnings
    @FXML
    private void handlePopUpEditCategories(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timesheets.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New entry");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load this new window!");
        }
    }

    @FXML
    private void handlePopUpEditActivities(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timesheets.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New entry");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load this new window!");
        }
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
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }

    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen.fxml");
    }

}
