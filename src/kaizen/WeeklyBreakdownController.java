package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import kaizen.UserData.KaizenDatabase;

public class WeeklyBreakdownController implements Initializable {

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
    private BarChart<String, Number> weeklyBarChart;
    //datepicker
    @FXML
    private DatePicker weeklyChartDtPicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void loadGraph(ActionEvent event) {
        weeklyBarChart.getData().clear();

        try {
            LocalDate date = weeklyChartDtPicker.getValue();
            //test date output:
            System.out.println("*" + date);
            XYChart.Series<String, Number> weeklySeries = new XYChart.Series<String, Number>();
            //if(date.equals(today);
            //db.insertStatement(UPDATE TIMSHEETS);
            ResultSet weekly = db.getResultSet("SELECT DISTINCT(ACTIVITY), SUM(DURATION) FROM TIMESHEETS "
                    + "WHERE DATE BETWEEN date('" + date + "') and date('" + date + "','+7 days')"
                    + "GROUP BY ACTIVITY ORDER BY DURATION LIMIT 5");

            ArrayList<String> categoryNameList = new ArrayList();
            ArrayList<Integer> durationList = new ArrayList();
            //test arraylist output:
            System.out.println(categoryNameList);
            //test output:
            System.out.println(weekly);

            while (weekly.next()) {
                categoryNameList.add(weekly.getString(1));
                durationList.add((weekly.getInt(2)) / 60);

                for (int i = 0; i < categoryNameList.size(); i++) {
                    weeklySeries.getData().add(new XYChart.Data(categoryNameList.get(i), durationList.get(i)));
                }

            }
            weeklyBarChart.getData().addAll(weeklySeries);
        } catch (Exception ex) {
            ex.printStackTrace();
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
