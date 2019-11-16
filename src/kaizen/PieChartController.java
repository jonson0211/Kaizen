package kaizen;

import com.sun.javafx.charts.Legend;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kaizen.DataModels.pieChartDM;
import kaizen.UserData.KaizenDatabase;

public class PieChartController implements Initializable {

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
    private ToggleButton settings;

    @FXML
    private Label welcome;
    @FXML
    private Label welcomeSubheading;
    //menu^//

    @FXML
    private Button dailyBreakdown;
    @FXML
    private Button weeklyBreakdown;
    @FXML
    private Button weeklyTrends;

    @FXML
    private Label workLabel;
    @FXML
    private Label relationshipsLabel;
    @FXML
    private Label projectsLabel;
    @FXML
    private Label wellnessLabel;
    @FXML
    private Label dailyLabel;
    @FXML
    private Label relaxationLabel;
    @FXML
    private Button logInTime;
    @FXML
    private GridPane grid;
    @FXML
    private Button tsBtn;

    @FXML
    public PieChart lifePieChart;

    ObservableList<PieChart.Data> lifePieChartData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buildPie();
        } catch (SQLException ex) {
            Logger.getLogger(PieChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PieChart lifePieChart = new PieChart(lifePieChartData);

    }

    //Getting time spent data for piechart
    public PieChart buildPie() throws SQLException {

        ArrayList<String> catData = new ArrayList();
        ArrayList<Double> durationData = new ArrayList();
        ArrayList<Double> durationTotal = new ArrayList();

        ResultSet rsCategoryData = db.getResultSet("SELECT DISTINCT(CATEGORYNAME)"
                + " FROM TIMESHEETS GROUP BY CATEGORYNAME");
        while (rsCategoryData.next()) {
            catData.add(rsCategoryData.getString(1));
        }

        ResultSet rsCategoryData1 = db.getResultSet("SELECT CATEGORYNAME, SUM(DURATION)"
                + " FROM TIMESHEETS GROUP BY CATEGORYNAME");
        while (rsCategoryData1.next()) {
            durationData.add(rsCategoryData1.getDouble(2));
        }

        ResultSet totalDuration1 = db.getResultSet("SELECT SUM(DURATION) from TIMESHEETS");
        while (totalDuration1.next()) {
            durationTotal.add(totalDuration1.getDouble(1));
        }

        double totalDurationSum = durationTotal.get(0);

        ObservableList<pieChartDM> categoryDurationList = FXCollections.observableArrayList();

        //ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            lifePieChart.getData().clear();
            ObservableList<PieChart.Data> lifePieChartData = FXCollections.observableArrayList();
            for (int i = 0; i < catData.size(); i++) {

                lifePieChartData.add(new PieChart.Data(
                        catData.get(i) + " - "
                        + Math.round(durationData.get(i) / 60)
                        + " hours ("
                        + Math.round((durationData.get(i) / totalDurationSum) * 100)
                        + "%)",
                        durationData.get(i)
                ));
            }
            lifePieChart.setData(lifePieChartData);

//colors - please REFERENCE BLAIR'S DEMO IN THE ABOUT SCREEN WHEN WE FINISH- taken straight from the demo:  
            ArrayList<String> pieColours = new ArrayList<String>();

//    try{
//    ResultSet colourRs = db.getResultSet("SELECT COLOUR FROM CATEGORY");    
//    while(colourRs.next()){
//        pieColours.add(colourRs.getString(0));
//    }} catch (Exception ex) {
//            
//        }
//     String[] pieColors = pieColours.toArray(new String[pieColours.size()]);
            //System.out.println(pieColors);
//         System.out.println(pieColors.length);
            String[] pieColors = {"#80bfff", "#cc99ff", "#ccffff", "#80ff80", "#ff80ff", "#ffb84d", "#9999ff"};
            int i = 0;
            for (PieChart.Data data : lifePieChartData) {
                data.getNode().setStyle(
                        "-fx-pie-color: " + pieColors[i
                        % pieColors.length] + ";"
                );
                i++;
            }
            i = 0;
            for (Node n : lifePieChart.getChildrenUnmodifiable()) {
                if (n instanceof Legend) {
                    Legend l = (Legend) n;
                    for (Legend.LegendItem li : l.getItems()) {
                        Node thisNode = li.getSymbol();
                        thisNode.setStyle(
                                "-fx-pie-color: " + pieColors[i
                                % pieColors.length] + ";"
                        );
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to produce Pie Chart!");
            e.printStackTrace();

        }
        return lifePieChart;
    }

    public ObservableList<pieChartDM> getPieData() {

        ObservableList<pieChartDM> categoryDurationList = FXCollections.observableArrayList();

        try {
            ResultSet rsCategoryComboTable = db.getResultSet("SELECT CATEGORYNAME, SUM(DURATION)"
                    + " FROM TIMESHEETS GROUP BY CATEGORYNAME");

            while (rsCategoryComboTable.next()) {
                categoryDurationList.add(new pieChartDM(rsCategoryComboTable.getString(1), rsCategoryComboTable.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(categoryDurationList);
    }

    @FXML
    private void handlePopUpScreenAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Timesheets.fxml"));
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
