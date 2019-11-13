/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label displayTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Loading DeepFocusMode Default Screen");
        displayTime.setVisible(true);
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
       
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentTime = ((java.time.LocalTime.now()).format(timeFormat));
                displayTime.setText(currentTime);
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    @FXML
    private void handleKanbanBoard(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
    }

    @FXML
    private void handleTaskTracker(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "TaskTracker.fxml");//TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    private void handleDailyLearnings(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleTimeDashboard(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "PieChart.fxml");
    }

    @FXML
    private void handleAboutScreen(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "AboutScreen.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
        pageSwitcher.switcher(event, "Settings.fxml"); //TO CHANGE WHEN PAGE IS MADE
    }

    @FXML
    private void handleSignOut(ActionEvent event) throws IOException {
        MusicPlaybackHelper.stopMusic();
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
