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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    @FXML private TextField id;

    KaizenDatabase db = new KaizenDatabase();

    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    public void initialize() {
        date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        achievements.setCellValueFactory(cellData -> cellData.getValue().getAchievementsProperty());
        improvements.setCellValueFactory(cellData -> cellData.getValue().getImprovementsProperty());
        entries.setItems(this.getReport());
        confirm.setVisible(false);
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
    private void handleRefresh(MouseEvent event) throws IOException {
    }
    
/*    private void refreshPage() {
        try{
            loadTable();
        }catch(Exception e){
            System.out.println("can't load table");
            e.printStackTrace();
        }
    }
    private void loadTable(){
        date.setCellValueFactory(new PropertyValueFactory<learningsEntryDM, String>("DATE"));
        achievements.setCellValueFactory(new PropertyValueFactory<learningsEntryDM, String>("DID_WELL"));
        improvements.setCellValueFactory(new PropertyValueFactory<learningsEntryDM, String>("BE_BETTER"));
        entries.setItems(getReport()); */
    

    @FXML
    private void handleSelect(ActionEvent event) {
        learningsEntryDM edit = entries.getSelectionModel().getSelectedItem();

        try {
            setData(entries.getSelectionModel().getSelectedItem().getDate(),
                    entries.getSelectionModel().getSelectedItem().getAchievements(),
                    entries.getSelectionModel().getSelectedItem().getImprovements());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        learningsEntryDM delete = entries.getSelectionModel().getSelectedItem();
        try {
            db.insertStatement("DELETE FROM TIMESHEETS WHERE DATE = '" + delete.getDate() + "' "
                    + "AND CATEGORYNAME = '" + delete.getAchievements() + "'"
                    + " AND ACTIVITY = '" + delete.getImprovements() + "'");
        } catch (Exception e) {
            System.out.println("Can't delete from database!");
            e.printStackTrace();
        }
            try{
                entries.getItems().removeAll(entries.getSelectionModel().getSelectedItem());
                }catch(Exception ex){
                    ex.printStackTrace();
                    System.out.println("Can't remove row from table!");
            }
        }
    

    @FXML
    private void editLearning(ActionEvent event) {
        String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Object a = achieveBox.getValue();
        Object i = improveBox.getValue();

        try {
            db.insertStatement("UPDATE LEARNINGS SET "
                    + "DATE = '" + date + "' "
                    + "AND DID_WELL = '" + a + "' "
                    + "AND BE_BETTER = '" + i + "'");
        } catch (SQLException ex) {
            Logger.getLogger(PopUpLearningsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Updated learnings!");
        confirm.setVisible(true);

    }

    public void setData(String date, String achievements, String improvements) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myLocalDate = LocalDate.parse(date, formatter);
        datePicker.setValue(myLocalDate);
        achieveBox.setValue(achievements);
        improveBox.setValue(improvements);       
    }
}
