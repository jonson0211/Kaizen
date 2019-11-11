/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kaizen.DataModels.learningsDidWell;
import kaizen.DataModels.learningsDoBetter;
import kaizen.UserData.KaizenDatabase;

/**
 * FXML Controller class
 *
 * @author wongad1
 */
public class PopUpLearningsController{
    
    @FXML
    private Button back;
    
    @FXML
    private TableView<learningsDidWell> well30Report;
    
    @FXML
    private TableView<learningsDoBetter> better30Report;
    
    @FXML
    private TableColumn<learningsDidWell, String> wellColumn;
    
    @FXML
    private TableColumn<learningsDoBetter, String> betterColumn;
    
    @FXML
    private TableColumn<learningsDidWell, Number> well30Count;
    
    @FXML
    private TableColumn<learningsDoBetter, Number> better30Count;
    
    KaizenDatabase db = new KaizenDatabase();
    
    PageSwitchHelper psh = new PageSwitchHelper();
   
   
    @FXML
    public void initialize(){
        wellColumn.setCellValueFactory(cellData -> cellData.getValue().getDidWellProperty());
        well30Count.setCellValueFactory(cellData -> cellData.getValue().getDidWellCountProperty());
        betterColumn.setCellValueFactory(cellData -> cellData.getValue().getBeBetterProperty());
        better30Count.setCellValueFactory(cellData -> cellData.getValue().getBeBetterCountProperty());
        well30Report.setItems(this.getWell30());
        better30Report.setItems(this.getBetter30());
    }
    
        public ObservableList<learningsDidWell> getWell30(){
        List<learningsDidWell> well30 = FXCollections.observableArrayList();
        
        try{
            ResultSet tableRs = db.getResultSet("SELECT DID_WELL, COUNT(DID_WELL) FROM LEARNINGS GROUP BY DID_WELL ORDER BY COUNT(DID_WELL) DESC");
            
            while (tableRs.next()){
                well30.add(new learningsDidWell(tableRs.getString("DID_WELL"),tableRs.getInt("COUNT(DID_WELL)")));
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        System.out.println(well30);
        return FXCollections.observableArrayList(well30);
}
        public ObservableList<learningsDoBetter> getBetter30(){
        List<learningsDoBetter> better30 = FXCollections.observableArrayList();
        
        try{
            ResultSet tableRs = db.getResultSet("SELECT BE_BETTER, COUNT(BE_BETTER) FROM LEARNINGS GROUP BY BE_BETTER ORDER BY COUNT(BE_BETTER) DESC");
            
            while (tableRs.next()){
                better30.add(new learningsDoBetter(tableRs.getString("BE_BETTER"),tableRs.getInt("COUNT(BE_BETTER)")));
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        System.out.println(better30);
        return FXCollections.observableArrayList(better30);
}
}