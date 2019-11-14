/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import kaizen.DataModels.learningsEntryDM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kaizen.UserData.KaizenDatabase;

/**
 * FXML Controller class
 *
 * @author wongad1
 */
public class PopUpLearningsController {

    @FXML
    private Button back;
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

    KaizenDatabase db = new KaizenDatabase();

    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    public void initialize() {
        date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        achievements.setCellValueFactory(cellData -> cellData.getValue().getAchievementsProperty());
        improvements.setCellValueFactory(cellData -> cellData.getValue().getImprovementsProperty());
        entries.setItems(this.getReport());
    }

    public ObservableList<learningsEntryDM> getReport() {
        List<learningsEntryDM> report = FXCollections.observableArrayList();

        try {
            ResultSet tableRs = db.getResultSet("SELECT * FROM LEARNINGS ORDER BY DATE DESC");

            while (tableRs.next()) {
                report.add(new learningsEntryDM(tableRs.getString("DATE"), tableRs.getString("DID_WELL"), tableRs.getString("BE_BETTER")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(report);
        return FXCollections.observableArrayList(report);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        psh.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleAdd(ActionEvent event) throws IOException {
        psh.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        learningsEntryDM edit = entries.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void handleRefresh(ActionEvent event) throws IOException {
        psh.switcher(event, "DailyLearnings.fxml");
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        learningsEntryDM edit = entries.getSelectionModel().getSelectedItem();
        try{
            db.insertStatement("DELETE FROM TIMESHEETS WHERE DATE = '"+ edit.getDate()+"' "
                    + "AND CATEGORYNAME = '"+ edit.getAchievements()+"'"
                    + " AND ACTIVITY = '"+edit.getImprovements()+"'");
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            e.printStackTrace();
        }
    }

}
