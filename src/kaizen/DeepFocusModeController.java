package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.util.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
    @FXML
    private TextArea displayTitle;
    @FXML
    private TextArea displayDescription;
    @FXML
    private Button button;
    @FXML
    private Label catchLabel;
    @FXML
    private Label secondsDisplay;

    //@FXML
    //private Button button2;
    ObservableList<taskCategoryChoice> taskCategoryChoiceList = FXCollections.observableArrayList();
    // ObservableList<taskChoice> taskChoiceList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        catchLabel.setVisible(false);

        //Print feedback
        System.out.println("Loading DeepFocusMode Default Screen");

        //Set labels visible
        displayTime.setVisible(true);
        displayMoonPhase.setVisible(true);
        secondsDisplay.setVisible(true);

        //Establish format for clock and AM/PM label
        final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm");
        final DateTimeFormatter secondsFormat = DateTimeFormatter.ofPattern("ss");
        final DateTimeFormatter moonPhaseFormat = DateTimeFormatter.ofPattern("a");

        //Create timeline for dynamic time display
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String currentTime = ((java.time.LocalTime.now()).format(timeFormat));
                String moonPhase = ((java.time.LocalTime.now()).format(moonPhaseFormat));
                String seconds = ((java.time.LocalTime.now()).format(secondsFormat));
                displayTime.setText(currentTime);
                displayMoonPhase.setText(moonPhase);
                secondsDisplay.setText(seconds);
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

    //Display current Task Title and Task Description
    public void getTaskText() {
        try {
            displayTitle.setVisible(true);
            String currentTask = selectTask.getValue();
            System.out.println(currentTask);
            displayTitle.setText(currentTask);
            displayDescription.setVisible(true);
            ResultSet rsDescription = db.getResultSet("SELECT (DESCRIPTION) FROM TASKS WHERE (TITLE)"
                    + " = '" + currentTask + "'");
            displayDescription.setText(rsDescription.getString("DESCRIPTION"));
        } catch (SQLException ex) {
            catchLabel.setText("Choose a task.");
            catchLabel.setVisible(true);
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
        catchLabel.setVisible(false);
        ObservableList<taskChoice> taskChoiceList = FXCollections.observableArrayList();
        System.out.println("1:" + selectTaskCategory.getValue());
        try {
            if (!selectTask.getItems().isEmpty()) {
                System.out.println("its not empty!");
                selectTask.getItems().clear();
            }
            ResultSet rsTaskChoiceTable = db.getResultSet("SELECT DISTINCT(TITLE) FROM TASKS WHERE (CATEGORYNAME)"
                    + " = '" + selectTaskCategory.getValue() + "'");
            while (rsTaskChoiceTable.next()) {
                System.out.println(rsTaskChoiceTable.getString("TITLE"));
                taskChoice T = new taskChoice(rsTaskChoiceTable.getString("TITLE"));
                taskChoiceList.add(T);
            }
            for (taskChoice task : taskChoiceList) {
                selectTask.getItems().addAll(task.getTaskChoice());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeepFocusModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(taskChoiceList);
    }

    /*@FXML
    private void handleTaskChoice(ActionEvent event) throws IOException {
        //Set up task choice:
        System.out.println("anything");
        taskChoiceList.setAll(this.getTaskChoice());
        for (taskChoice a : taskChoiceList) {
            System.out.println(a.getTaskChoiceProperty());
            selectTask.getItems().addAll(a.getTaskChoice());
            System.out.println("finished");
        }
    }
     */
    public void testClass() {
        System.out.println("Test Complete");
    }

    public void categoryOptionClicked() {
        System.out.println("Click has been registered");
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
    private void handleMoodOne(ActionEvent event) throws IOException {
        System.out.println("Mood One Selected");
        MusicPlaybackHelper.stopMusic();
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
        MusicPlaybackHelper.stopMusic();
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
        MusicPlaybackHelper.stopMusic();
        moodOne.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodTwo.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");
        moodThree.getStyleClass().removeAll("mood-toggle", "current-mood-toggle");

        moodOne.getStyleClass().add("mood-toggle");
        moodTwo.getStyleClass().add("mood-toggle");
        moodThree.getStyleClass().add("current-mood-toggle");

        MusicPlaybackHelper.playMusic("moodMusicThree.mp3");
    }
}
