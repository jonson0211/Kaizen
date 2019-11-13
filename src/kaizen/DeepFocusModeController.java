/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import kaizen.UserData.KaizenDatabase;

/**
 * FXML Controller class
 *
 * @author Raymond
 */
public class DeepFocusModeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    KaizenDatabase userDatabase = new KaizenDatabase();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @FXML
    private ToggleButton settings;

    @FXML
    private ToggleButton kanbanBoard;

    @FXML
    private ToggleButton timeDashboard;

    @FXML
    private ToggleButton about;

    @FXML
    private Button signOut;

    @FXML
    private ToggleButton moodOne;

    @FXML
    private ToggleButton moodTwo;

    @FXML
    private ToggleButton moodThree;

    @FXML
    private ToggleButton taskTracker;

    @FXML
    private ChoiceBox<String> selectTaskCategory;

    @FXML
    private ChoiceBox<String> selectTask;

    @FXML
    private ToggleButton dailyLearnings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Loading DeepFocusMode Default Screen");

    }

    @FXML
    private void handleKanbanBoard(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
    }

    @FXML
    private void handleTaskTracker(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "TaskTracker.fxml");//TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    private void handleDailyLearnings(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleTimeDashboard(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PieChart.fxml");
    }

    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen.fxml");
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
    private void handleMoodOne(ActionEvent event) throws IOException {
        System.out.println("Mood One Selected");

        moodOne.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodTwo.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodThree.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");

        moodOne.getStyleClass().add("current-mood-toggle");
        moodTwo.getStyleClass().add("mood-toggle");
        moodThree.getStyleClass().add("mood-toggle");

        MusicPlaybackHelper.playMusic("moodMusicOne.mp3");

    }

    @FXML
    private void handleMoodTwo(ActionEvent event) throws IOException {
        System.out.println("Mood Two Selected");
        moodOne.getStyleClass().add("mood-toggle");

        moodOne.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodTwo.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodThree.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");

        moodOne.getStyleClass().add("mood-toggle");
        moodTwo.getStyleClass().add("current-mood-toggle");
        moodThree.getStyleClass().add("mood-toggle");

        MusicPlaybackHelper.playMusic("moodMusicTwo.mp3");

    }

    @FXML
    private void handleMoodThree(ActionEvent event) throws IOException {
        System.out.println("Mood Three Selected");

        moodOne.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodTwo.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodThree.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");

        moodOne.getStyleClass().add("mood-toggle");
        moodTwo.getStyleClass().add("mood-toggle");
        moodThree.getStyleClass().add("current-mood-toggle");

        MusicPlaybackHelper.playMusic("moodMusicThree.mp3");
    }

}
