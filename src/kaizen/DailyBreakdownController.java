package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kaizen.DataModels.categoryCombo;
import kaizen.DataModels.dailyBreakdownDM;
import kaizen.UserData.KaizenDatabase;

public class DailyBreakdownController implements Initializable {

    KaizenDatabase db = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    //menu buttons
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
    //chart buttons
    @FXML
    private Button dailyBreakdown;
    @FXML
    private Button weeklyBreakdown;
    @FXML
    private Button weeklyTrends;
    @FXML
    private Button backTimeDashboard;

    //barchart
    @FXML
    private BarChart<String, Number> dailyBarChart;
    //datepicker
    @FXML
    private DatePicker dailyChartDtPicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadGraph(ActionEvent event) {
        dailyBarChart.getData().clear();

        ArrayList<String> categoryComboList = new ArrayList();
        ArrayList<Integer> durationList = new ArrayList();

        try {
            LocalDate date = dailyChartDtPicker.getValue();

            XYChart.Series<String, Number> dailySeries = new XYChart.Series<String, Number>();
            ResultSet daily = db.getResultSet("SELECT ACTIVITY, SUM(DURATION) FROM TIMESHEETS "
                    + "WHERE DATE = '" + date + "' "
                    + "GROUP BY ACTIVITY ORDER BY DURATION LIMIT 5");

//            ArrayList<String> categoryNameList = new ArrayList();
//            ArrayList<Integer> durationList = new ArrayList();
            while (daily.next()) {
                categoryComboList.add(daily.getString(1));
                durationList.add((daily.getInt(2)) / 60);

                for (int i = 0; i < categoryComboList.size(); i++) {
                    dailySeries.getData().add(new XYChart.Data(categoryComboList.get(i), durationList.get(i)));
                }
            }
            dailyBarChart.getData().addAll(dailySeries);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ObservableList<categoryCombo> getCatChoice() {

        ObservableList<categoryCombo> categoryComboList = FXCollections.observableArrayList();

        try {
            ResultSet rsCategoryComboTable = db.getResultSet("SELECT DISTINCT(CATEGORYNAME) FROM CATEGORY");

            while (rsCategoryComboTable.next()) {
                categoryComboList.add(new categoryCombo(rsCategoryComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(categoryComboList);
    }
    //getting duration from DB

    public ObservableList<dailyBreakdownDM> getDuration() {

        ObservableList<dailyBreakdownDM> durationResultList = FXCollections.observableArrayList();

        try {
            ResultSet rsDurationTable = db.getResultSet("SELECT ACTIVITY,DURATION,DATE FROM TIMESHEETS");

            while (rsDurationTable.next()) {
                durationResultList.add(new dailyBreakdownDM(rsDurationTable.getString(1),
                        rsDurationTable.getInt(2), rsDurationTable.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(durationResultList);
    }

//     public ObservableList<XYChart.Series<String, Number>> getItemGraphStatistics(){
//    ObservableList<XYChart.Series<String, Number>> dailySeries = FXCollections.observableArrayList();
//    try{
//            LocalDate date = dailyChartDtPicker.getValue();
//            
//            //XYChart.Series<String, Number> dailySeries = new XYChart.Series<String,Number>();
//            ResultSet daily = db.getResultSet("SELECT CATEGORYNAME, DURATION FROM TIMESHEETS "
//                    + "WHERE DATE = '" + date + "'");
//           
//            
////            ArrayList<String> categoryNameList = new ArrayList();
////            ArrayList<Integer> durationList = new ArrayList();
//
////            while (daily.next()){
////                categoryComboList.add(daily.getString(1));
////                durationList.add((rsDurationTable.getInt(2))/60);
////                
////                for(int i = 0; i<categoryComboList.size(); i++){
////                    dailySeries.getData().add(new XYChart.Series(categoryComboList.get(i), durationList.get(i)));
////                }
//////            }
//            dailyBarChart.getData().addAll(dailySeries);
//        } catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
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
        pageSwitcher.switcher(event, "Settings.fxml"); //TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    private void handleTimeDashboard(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PieChart.fxml");
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
