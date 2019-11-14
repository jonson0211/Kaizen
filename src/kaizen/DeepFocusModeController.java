package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import kaizen.DataModels.taskCategoryChoice;
import kaizen.DataModels.taskChoice;
import kaizen.UserData.KaizenDatabase;

public class DeepFocusModeController implements Initializable {

    KaizenDatabase db = new KaizenDatabase();

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
    @FXML
    private Label displayMoonPhase;

    ObservableList<taskCategoryChoice> taskCategoryChoiceList = FXCollections.observableArrayList();
    ObservableList<taskChoice> taskChoiceList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Print feedback
        System.out.println("Loading DeepFocusMode Default Screen");

        //Set labels visible
        displayTime.setVisible(true);
        displayMoonPhase.setVisible(true);

        //Establish format for clock and AM/PM label
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
        final DateTimeFormatter moonPhaseFormat = DateTimeFormatter.ofPattern("a");

        //Create timeline for dynamic time display
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String currentTime = ((java.time.LocalTime.now()).format(timeFormat));
                String moonPhase = ((java.time.LocalTime.now()).format(moonPhaseFormat));
                displayTime.setText(currentTime);
                displayMoonPhase.setText(moonPhase);
            }
        }));

        //Execute timeline
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //Set up task category:
        taskCategoryChoiceList.setAll(this.getTaskCategoryChoice());
        for (taskCategoryChoice a : taskCategoryChoiceList) {
            System.out.println(a.getTaskCategoryChoiceProperty());
            selectTaskCategory.getItems().addAll(a.getTaskCategoryChoice());
        }
    }

    //Get Task Category 
    public ObservableList<taskCategoryChoice> getTaskCategoryChoice() {

        ObservableList<taskCategoryChoice> taskCategoryChoiceList = FXCollections.observableArrayList();

        try {
            ResultSet rsTaskCategoryChoiceTable = db.getResultSet("SELECT DISTINCT(CATEGORYNAME) FROM CATEGORY");

            while (rsTaskCategoryChoiceTable.next()) {
                taskCategoryChoiceList.add(new taskCategoryChoice(rsTaskCategoryChoiceTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeepFocusModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(taskCategoryChoiceList);
    }

    //Get Task Choice 
    public ObservableList<taskChoice> getTaskChoice() {

        ObservableList<taskChoice> taskChoiceList = FXCollections.observableArrayList();
        String selectedCategory = selectTaskCategory.getValue();

        try {
            ResultSet rsTaskChoiceTable = db.getResultSet("SELECT DISTINCT(TITLE) FROM TASKS WHERE (CATEGORYNAME)"
                    + " = '" + selectedCategory + "'");

            while (rsTaskChoiceTable.next()) {
                taskChoiceList.add(new taskChoice(rsTaskChoiceTable.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeepFocusModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(taskChoiceList);
    }

    @FXML
    private void handleTaskChoice(ActionEvent event) throws IOException {
        //Set up task choice:
        taskChoiceList.setAll(this.getTaskChoice());
        for (taskChoice a : taskChoiceList) {
            System.out.println(a.getTaskChoiceProperty());
            selectTask.getItems().addAll(a.getTaskChoice());
        }
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
