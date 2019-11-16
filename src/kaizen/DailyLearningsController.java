package kaizen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kaizen.DataModels.learningsDidWell;
import kaizen.DataModels.learningsDoBetter;
import kaizen.UserData.KaizenDatabase;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import kaizen.DataModels.learningsCombo;

public class DailyLearningsController {

    KaizenDatabase userLearn = new KaizenDatabase();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    ObservableList<learningsCombo> answerOnes = FXCollections.observableArrayList();
    ObservableList<learningsCombo> answerTwos = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> answerOne;

    @FXML
    private ComboBox<String> answerTwo;

    @FXML
    private Button addLearning;

    @FXML
    private ToggleButton kbBoard;

    @FXML
    private ToggleButton deepFocus;

    @FXML
    private ToggleButton taskTracker;

    @FXML
    private ToggleButton timeDashboard;

    @FXML
    private ToggleButton settings;

    @FXML
    private ToggleButton about;

    @FXML
    private ToggleButton signOut;

    @FXML
    private DatePicker datePick;

    @FXML
    private Label confirmEntry;

    @FXML
    private TableView<learningsDidWell> didWellView;

    @FXML
    private TableView<learningsDoBetter> doBetterView;

    @FXML
    private TableColumn<learningsDidWell, String> didWellColumn;

    @FXML
    private TableColumn<learningsDidWell, Number> didWellCount;

    @FXML
    private TableColumn<learningsDoBetter, String> doBetterColumn;

    @FXML
    private TableColumn<learningsDoBetter, Number> doBetterCount;

    @FXML
    private Button viewPast;
    @FXML
    private Button check;
    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private Label checkLabel;

    PreparedStatement pst;

    Connection conn;

    ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        // TODO
        answerOne.setEditable(true);
        answerTwo.setEditable(true);
        //FillComboOne();
        //FillComboTwo();
        confirmEntry.setVisible(false);
        viewPast.setVisible(true);
        didWellColumn.setCellValueFactory(cellData -> cellData.getValue().getDidWellProperty());
        didWellCount.setCellValueFactory(cellData -> cellData.getValue().getDidWellCountProperty());
        didWellView.setItems(this.getLearningsDidWell());
        doBetterColumn.setCellValueFactory(cellData -> cellData.getValue().getBeBetterProperty());
        doBetterCount.setCellValueFactory(cellData -> cellData.getValue().getBeBetterCountProperty());
        doBetterView.setItems(this.getLearningsDoBetter());

        try {
            answerOnes.setAll(this.getComboOne());
            for (learningsCombo c : answerOnes) {
                System.out.println(c.getDwProperty());
                answerOne.getItems().addAll(c.getDw());
            }
            answerTwos.setAll(this.getComboTwo());
            for (learningsCombo d : answerTwos) {
                System.out.println(d.getDwProperty());
                answerTwo.getItems().addAll(d.getDw());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

    }

    @FXML
    private void handleNew(ActionEvent event) throws IOException, SQLException {
        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        try {
            userLearn.insertStatement("DELETE FROM LEARNINGS");
            didWellColumn.setCellValueFactory(cellData -> cellData.getValue().getDidWellProperty());
            didWellCount.setCellValueFactory(cellData -> cellData.getValue().getDidWellCountProperty());
            didWellView.setItems(this.getLearningsDidWell());
            doBetterColumn.setCellValueFactory(cellData -> cellData.getValue().getBeBetterProperty());
            doBetterCount.setCellValueFactory(cellData -> cellData.getValue().getBeBetterCountProperty());
            doBetterView.setItems(this.getLearningsDoBetter());

            try {
                answerOnes.setAll(this.getComboOne());
                for (learningsCombo c : answerOnes) {
                    System.out.println(c.getDwProperty());
                    answerOne.getItems().removeAll(c.getDw());
                }
                answerTwos.setAll(this.getComboTwo());
                for (learningsCombo d : answerTwos) {
                    System.out.println(d.getDwProperty());
                    answerTwo.getItems().removeAll(d.getDw());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        no.setVisible(false);
        yes.setDisable(true);

    }

    @FXML
    private void handleOld(ActionEvent event) throws IOException {
        datePick.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        try {
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('2019-11-12', 'Went to the gym', 'Spend time with family');");
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('2019-11-13', 'Gave good peer reviews', 'Picked up java earlier');");
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('2019-11-14', 'Gave good peer reviews', 'Picked up java earlier');");
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('2019-11-15', 'Gave good peer reviews', 'Picked up SQL earlier');");
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('2019-11-16', 'Gave fantastic peer reviews', 'Picked up java earlier');");
            System.out.println("Inserted dummy data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System Status");
            alert.setHeaderText("Old user data inserted!");
            alert.showAndWait();
            didWellColumn.setCellValueFactory(cellData -> cellData.getValue().getDidWellProperty());
            didWellCount.setCellValueFactory(cellData -> cellData.getValue().getDidWellCountProperty());
            didWellView.setItems(this.getLearningsDidWell());
            doBetterColumn.setCellValueFactory(cellData -> cellData.getValue().getBeBetterProperty());
            doBetterCount.setCellValueFactory(cellData -> cellData.getValue().getBeBetterCountProperty());
            doBetterView.setItems(this.getLearningsDoBetter());
            try {
                answerOnes.setAll(this.getComboOne());
                for (learningsCombo c : answerOnes) {
                    System.out.println(c.getDwProperty());
                    answerOne.getItems().addAll(c.getDw());
                }
                answerTwos.setAll(this.getComboTwo());
                for (learningsCombo d : answerTwos) {
                    System.out.println(d.getDwProperty());
                    answerTwo.getItems().addAll(d.getDw());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        yes.setVisible(false);
        no.setDisable(true);
    }

    //return observable list of done well and do betters
    @FXML
    private void checkMissing(ActionEvent event) throws SQLException {
        //finding most recent learnings_ID
        try {
            ResultSet rs = userLearn.getResultSet("SELECT MAX(LEARNINGS_ID), DATE FROM LEARNINGS");
            //converting the string to a date
            LocalDate date = LocalDate.parse(rs.getString("DATE"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //finding the current date
            LocalDate currentDate = LocalDate.now();
            //converting the date format to ours
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //parsing the date as a String
            String currentDateString = currentDate.format(formatter);
            //
            LocalDate currentDateParsed = LocalDate.parse(currentDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            long daysBetween = ChronoUnit.DAYS.between(currentDateParsed, date);
            if (daysBetween != 0) {
                try {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Missing entres!");
                    alert.setHeaderText("You have not entered in learnings since " + date);
                    alert.showAndWait();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No missing entries!");
                    alert.setHeaderText("You have no missing entries.");
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Up to date entres!");
                alert.setHeaderText("Your learnings are up to date!");
                alert.showAndWait();
                System.out.println("User has inputted all entries to date!");
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No missing entries!");
            alert.setHeaderText("You have no missing entries.");
            alert.showAndWait();
        }
    }

    public ObservableList<learningsDidWell> getLearningsDidWell() {

        ObservableList<learningsDidWell> didWellList = FXCollections.observableArrayList();

        try {
            ResultSet rsDidWellTable = userLearn.getResultSet("SELECT DID_WELL, COUNT(DID_WELL) FROM LEARNINGS GROUP BY DID_WELL ORDER BY COUNT(DID_WELL) DESC LIMIT 7");

            while (rsDidWellTable.next()) {
                didWellList.add(new learningsDidWell(rsDidWellTable.getString("DID_WELL"), rsDidWellTable.getInt("COUNT(DID_WELL)")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(didWellList);
    }

    public ObservableList<learningsDoBetter> getLearningsDoBetter() {

        ObservableList<learningsDoBetter> doBetterList = FXCollections.observableArrayList();

        try {
            ResultSet rsDoBetterTable = userLearn.getResultSet("SELECT BE_BETTER, COUNT(BE_BETTER) FROM LEARNINGS GROUP BY BE_BETTER ORDER BY COUNT(DID_WELL) DESC LIMIT 7");

            while (rsDoBetterTable.next()) {
                doBetterList.add(new learningsDoBetter(rsDoBetterTable.getString("BE_BETTER"), rsDoBetterTable.getInt("COUNT(BE_BETTER)")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FXCollections.observableArrayList(doBetterList);
    }

    //input learnings into the table summary
    //update learnings
    public ObservableList<learningsCombo> getComboOne() throws SQLException {
        try {
            ResultSet rs = userLearn.getResultSet("SELECT DISTINCT(DID_WELL) FROM LEARNINGS GROUP BY DID_WELL");
            while (rs.next()) {
                answerOnes.add(new learningsCombo(rs.getString("DID_WELL")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return FXCollections.observableArrayList(answerOnes);
    }

    public ObservableList<learningsCombo> getComboTwo() throws SQLException {
        try {
            ResultSet rs = userLearn.getResultSet("SELECT DISTINCT(BE_BETTER) FROM LEARNINGS GROUP BY BE_BETTER");
            while (rs.next()) {
                answerTwos.add(new learningsCombo(rs.getString("BE_BETTER")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return FXCollections.observableArrayList(answerTwos);
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String date = datePick.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String did_well = answerOne.getValue();
        String be_better = answerTwo.getValue();

        try {
            userLearn.insertStatement("INSERT INTO LEARNINGS (DATE, DID_WELL, BE_BETTER) "
                    + "VALUES ('" + date + "','" + did_well + "','" + be_better + "');");

            confirmEntry.setVisible(true);
        } catch (Exception e) {
            System.out.println("learnings update failed");
            e.printStackTrace();
        }

        /*    @FXML
         private void updateAnswers(ActionEvent event) throws SQLException{
         String answerOneString = (String) answerOne.getValue();
         String answerTwoString = (String) answerTwo.getValue();
         String date = datePick.getValue().format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
        
         userLearn.insertStatement("INSERT INTO LEARNINGS(DATE, DID_WELL, BE_BETTER) VALUES ('" + date + "', '" + answerOneString + "', '" + answerTwoString + "');");
         System.out.println("Entered in learnings");
         confirmEntry.setVisible(true);*/
        //to do - update combo box values
    }
    //see if user has already entered in previous answers
    //

    //populating combobox
    /* private void FillComboOne() {
     try {
     String queryOne = "SELECT DID_WELL FROM LEARNINGS";
     pst = conn.prepareStatement(queryOne);
     rs = pst.executeQuery();
            
     while(rs.next()){
     String didwell = rs.getString("DID_WELL");
     answerOne.setValue(rs.getString(didwell));
     } 
     pst.close();
     rs.close();
     } catch (SQLException ex) {
     Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
     }   
     }
     private void FillComboTwo(){
     String queryTwo = "SELECT BE_BETTER FROM LEARNINGS";
     try {
     pst = conn.prepareStatement(queryTwo);
     rs = pst.executeQuery();
            
     while(rs.next()){
     String bebetter = rs.getString("BE_BETTER");
     answerTwo.setValue(rs.getString(bebetter));
     }
     } catch (SQLException ex) {
     Logger.getLogger(DailyLearningsController.class.getName()).log(Level.SEVERE, null, ex);
     }
                
     }*/
    @FXML
    private void handlePopUpScreenAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUpLearnings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("New entry");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't display window");
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DailyLearnings.fxml");
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
    private void handleAboutScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutScreen.fxml");
    }

    @FXML
    private void handlePopUpScreen(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "PopUpLearnings.fxml");
    }
}
