package kaizen;

import java.io.IOException;
import kaizen.DataModels.learningsEntryDM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaizen.UserData.KaizenDatabase;

public class PopUpLearningsController {

    @FXML
    private Button add;
    @FXML
    private Button refresh;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private TableView<learningsEntryDM> entries;
    @FXML
    private TableColumn<learningsEntryDM, String> date;
    @FXML
    private TableColumn<learningsEntryDM, String> achievements;
    @FXML
    private TableColumn<learningsEntryDM, String> improvements;
    @FXML
    private TableColumn<learningsEntryDM, String> id;
    @FXML
    private Button exit;

    @FXML
    DatePicker datePicker;
    @FXML
    private ComboBox achieveBox;
    @FXML
    private ComboBox improveBox;
    @FXML
    private Button submit;
    @FXML
    private Label confirm;

    @FXML
    private TextField pKey;

    KaizenDatabase db = new KaizenDatabase();

    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    public void initialize() {
        date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        achievements.setCellValueFactory(cellData -> cellData.getValue().getAchievementsProperty());
        improvements.setCellValueFactory(cellData -> cellData.getValue().getImprovementsProperty());
        id.setCellValueFactory(cellData -> cellData.getValue().getPkProperty());

        entries.setItems(this.getReport());
        confirm.setVisible(false);
    }

    public ObservableList<learningsEntryDM> getReport() {
        List<learningsEntryDM> report = FXCollections.observableArrayList();

        try {
            ResultSet tableRs = db.getResultSet("SELECT * FROM LEARNINGS ORDER BY DATE DESC");

            while (tableRs.next()) {
                report.add(new learningsEntryDM(tableRs.getString("DATE"), tableRs.getString("DID_WELL"), tableRs.getString("BE_BETTER"), tableRs.getString("LEARNINGS_ID")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(report);
        return FXCollections.observableArrayList(report);
    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSelect(ActionEvent event) {
        learningsEntryDM edit = entries.getSelectionModel().getSelectedItem();

        try {
            setData(entries.getSelectionModel().getSelectedItem().getDate(),
                    entries.getSelectionModel().getSelectedItem().getAchievements(),
                    entries.getSelectionModel().getSelectedItem().getImprovements(),
                    entries.getSelectionModel().getSelectedItem().getPk());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No entry selected");
            alert.setHeaderText("Please select an entry!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        learningsEntryDM delete = entries.getSelectionModel().getSelectedItem();
        try {
            db.insertStatement("DELETE FROM LEARNINGS WHERE DATE = '" + delete.getDate() + "' "
                    + " AND DID_WELL = '" + delete.getAchievements() + "'"
                    + " AND BE_BETTER = '" + delete.getImprovements() + "'"
                    + " AND LEARNINGS_ID = '" + delete.getPk() + "'");
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            e.printStackTrace();
        }
        try {
            entries.getItems().removeAll(entries.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't remove row from table!");
        }
    }

    @FXML
    private void editLearning(ActionEvent event) {
        
        try {
            String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String a = (String) achieveBox.getValue();
        String i = (String) improveBox.getValue();
        String z = (String) pKey.getText();
        
            db.insertStatement("UPDATE LEARNINGS SET "
                    + "DATE = '" + date + "'"
                    + " , DID_WELL = '" + a + "'"
                    + " , BE_BETTER = '" + i + "' WHERE LEARNINGS_ID = '" + z +"'");
            System.out.println("Updated learnings!");
        confirm.setVisible(true);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No entry selected");
            alert.setHeaderText("Please select an entry!");
            alert.showAndWait();
            Logger.getLogger(PopUpLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    public void setData(String date, String achievements, String improvements, String pk) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myLocalDate = LocalDate.parse(date, formatter);
        datePicker.setValue(myLocalDate);
        achieveBox.setValue(achievements);
        improveBox.setValue(improvements);
        //Integer.toString(pk);
        String number = String.valueOf(pk);
        pKey.setText(number);
    }
}
