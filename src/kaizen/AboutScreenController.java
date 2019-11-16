package kaizen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kaizen.UserData.KaizenDatabase;

public class AboutScreenController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        pageSwitcher.switcher(event, "PieChart.fxml");
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
}
