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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kaizen.DataModels.activityCombo;
import kaizen.UserData.KaizenDatabase;

public class WeeklyTrendsController implements Initializable {

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
    @FXML
    private Label status;
    //chart buttons
    @FXML
    private Button dailyBreakdown;
    @FXML
    private Button weeklyBreakdown;
    @FXML
    private Button weeklyTrends;
    @FXML
    private Button backTimeDashboard;
    @FXML
    private Button backBtn;
    //barchart
    @FXML
    private LineChart<String, Number> weeklyTrendsLineChart;
    //barcharts variables
    @FXML
    private Button loadGraphButton;
    @FXML
    private TextField numWeeksTxtField;
    //@FXML private ChoiceBox activityChoiceBox;
    @FXML
    private ChoiceBox<String> activityChoiceBox;
    @FXML
    private ChoiceBox<String> activityChoiceBox2;
    @FXML
    private ChoiceBox<String> activityChoiceBox3;

    //@FXML private ChoiceBox<Integer> numOptionsChoiceBox;
    //datepicker
    @FXML
    private DatePicker weeklyChartDtPicker;

    ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
        status.setVisible(false);

        activityComboList.setAll(this.getActChoice());
        for (activityCombo d : activityComboList) {
            System.out.println(d.getActChoiceProperty());
            activityChoiceBox.getItems().addAll(d.getActChoice());
            activityChoiceBox2.getItems().addAll(d.getActChoice());
            activityChoiceBox3.getItems().addAll(d.getActChoice());
        }
        activityChoiceBox.setValue("Choose Type!");

        activityChoiceBox2.setValue("Choose Type!");
        activityChoiceBox3.setValue("Choose Type!");

    }

    //choose number of weeks back, choose activity, load graph
    @FXML
    private void loadGraph(ActionEvent event) throws SQLException {
        weeklyTrendsLineChart.getData().clear();
        boolean isMyBoxEmpty0 = activityChoiceBox.getSelectionModel().isEmpty();
        boolean isMyBoxEmpty = activityChoiceBox2.getSelectionModel().isEmpty();
        boolean isMyBoxEmpty2 = activityChoiceBox3.getSelectionModel().isEmpty();

        //int numOptions = numOptionsChoiceBox.getValue();
        String numWeeksBack = numWeeksTxtField.getText();

        int numWeeks = Integer.parseInt(numWeeksBack);
        String activity = activityChoiceBox.getValue().toString();

        try {
            LocalDate date = weeklyChartDtPicker.getValue();

            XYChart.Series<String, Number> weeklySeries = new XYChart.Series<String, Number>();

            weeklySeries.setName(activity);
            weeklyTrendsLineChart.getData().addAll(weeklySeries);

            //Calculate duration of activities during selected time period
            ArrayList<Double> durationList = new ArrayList();
            ArrayList<Double> durationWeeklyList = new ArrayList();
            ArrayList<Double> durationPercentageList = new ArrayList();

            for (int i = 0; i < numWeeks; i++) {

                ResultSet weeklyTotalDuration = db.getResultSet("SELECT SUM(DURATION) FROM TIMESHEETS "
                        + "WHERE DATE BETWEEN date('" + date + "','" + ((i + 1) * -7) + " days') "
                        + "and date('" + date + "','" + (i * -7) + " days') "
                );

                while (weeklyTotalDuration.next()) {
                    durationWeeklyList.add((weeklyTotalDuration.getDouble("SUM(DURATION)")));
                }

                ResultSet weekly = db.getResultSet("SELECT ACTIVITY, SUM(DURATION) FROM TIMESHEETS "
                        + "WHERE ACTIVITY = '" + activity + "'"
                        + "AND DATE BETWEEN date('" + date + "','" + ((i + 1) * -7) + " days') "
                        + "and date('" + date + "','" + (i * -7) + " days') GROUP BY ACTIVITY"
                );

                while (weekly.next()) {
                    durationList.add((weekly.getDouble(2)));
                }
            }

            try {

                for (int n = 0, i = 0; n < durationList.size() && i < numWeeks; n++, i++) {
                    weeklySeries.getData().add(new XYChart.Data("Week " + (n + 1), Math.round((durationList.get(n) / durationWeeklyList.get(i)) * 100)));
                }

                //System.out.println("*" + durationList);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            status.setText("Graph loaded!");
            status.setVisible(true);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

//load Activity 2 line        
        if (isMyBoxEmpty != true) {
            String activity2 = activityChoiceBox2.getValue().toString();

            try {
                LocalDate date = weeklyChartDtPicker.getValue();

                XYChart.Series<String, Number> weeklySeries2 = new XYChart.Series<String, Number>();

                weeklySeries2.setName(activity2);
                weeklyTrendsLineChart.getData().addAll(weeklySeries2);

                //Calculate duration of activities during selected time period
                ArrayList<Double> durationList2 = new ArrayList();
                ArrayList<Double> durationWeeklyList2 = new ArrayList();
                ArrayList<Double> durationPercentageList2 = new ArrayList();

                for (int j = 0; j < numWeeks; j++) {

                    ResultSet weeklyTotalDuration2 = db.getResultSet("SELECT SUM(DURATION) FROM TIMESHEETS "
                            + "WHERE DATE BETWEEN date('" + date + "','" + ((j + 1) * -7) + " days') "
                            + "and date('" + date + "','" + (j * -7) + " days') "
                    );

                    while (weeklyTotalDuration2.next()) {
                        durationWeeklyList2.add((weeklyTotalDuration2.getDouble("SUM(DURATION)")));
                    }

                    ResultSet weekly2 = db.getResultSet("SELECT ACTIVITY, SUM(DURATION) FROM TIMESHEETS "
                            + "WHERE ACTIVITY = '" + activity2 + "'"
                            + "AND DATE BETWEEN date('" + date + "','" + ((j + 1) * -7) + " days') "
                            + "and date('" + date + "','" + (j * -7) + " days') GROUP BY ACTIVITY"
                    );

                    while (weekly2.next()) {
                        durationList2.add((weekly2.getDouble(2)));
                    }
                }
                try {

                    for (int k = 0, j = 0; k < durationList2.size() && j < numWeeks; k++, j++) {
                        weeklySeries2.getData().add(new XYChart.Data("Week " + (k + 1), Math.round((durationList2.get(k) / durationWeeklyList2.get(j)) * 100)));

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                status.setText("Graph loaded!");
                status.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

//Loading Activity 2 line
            if (isMyBoxEmpty2 != true) {
                String activity3 = activityChoiceBox3.getValue().toString();

                try {
                    LocalDate date = weeklyChartDtPicker.getValue();

                    XYChart.Series<String, Number> weeklySeries3 = new XYChart.Series<String, Number>();

                    weeklySeries3.setName(activity3);
                    weeklyTrendsLineChart.getData().addAll(weeklySeries3);

                    //Calculate duration of activities during selected time period
                    ArrayList<Double> durationList3 = new ArrayList();
                    ArrayList<Double> durationWeeklyList3 = new ArrayList();
                    ArrayList<Double> durationPercentageList3 = new ArrayList();

                    for (int j = 0; j < numWeeks; j++) {

                        ResultSet weeklyTotalDuration3 = db.getResultSet("SELECT SUM(DURATION) FROM TIMESHEETS "
                                + "WHERE DATE BETWEEN date('" + date + "','" + ((j + 1) * -7) + " days') "
                                + "and date('" + date + "','" + (j * -7) + " days') "
                        );

                        while (weeklyTotalDuration3.next()) {
                            durationWeeklyList3.add((weeklyTotalDuration3.getDouble("SUM(DURATION)")));
                        }

                        ResultSet weekly3 = db.getResultSet("SELECT ACTIVITY, SUM(DURATION) FROM TIMESHEETS "
                                + "WHERE ACTIVITY = '" + activity3 + "'"
                                + "AND DATE BETWEEN date('" + date + "','" + ((j + 1) * -7) + " days') "
                                + "and date('" + date + "','" + (j * -7) + " days') GROUP BY ACTIVITY"
                        );

                        while (weekly3.next()) {
                            durationList3.add((weekly3.getDouble(2)));
                        }
                    }

                    try {

                        for (int m = 0, p = 0; m < durationList3.size() && p < numWeeks; m++, p++) {
                            weeklySeries3.getData().add(new XYChart.Data("Week " + (m + 1), Math.round((durationList3.get(m) / durationWeeklyList3.get(p)) * 100)));
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    status.setText("Graph loaded!");
                    status.setVisible(true);
                } catch (Exception ex) {
                    status.setText("Could not load graph! Please check your inputs. Ensure activities are selected in order of 1 to 3.");
                    status.setVisible(true);
                    ex.printStackTrace();
                }
            }
        }
    }

    public ObservableList<activityCombo> getActChoice() {

        ObservableList<activityCombo> activityComboList = FXCollections.observableArrayList();

        try {
            ResultSet rsActivityComboTable = db.getResultSet("SELECT DISTINCT(ACTIVITY) FROM TIMESHEETS");

            while (rsActivityComboTable.next()) {
                activityComboList.add(new activityCombo(rsActivityComboTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(activityComboList);
    }

    @FXML
    private void handlePieChart(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PieChart.fxml");
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
        pageSwitcher.switcher(event, "Timesheets.fxml");
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
