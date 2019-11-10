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
    private TableColumn<learningsDidWell, Number> wellCountColumn;
    
    @FXML
    private TableColumn<learningsDoBetter, Number> betterCountColumn;
    
    KaizenDatabase db = new KaizenDatabase();
    
    PageSwitchHelper psh = new PageSwitchHelper();
   
   
    @FXML
    public void initialize(){
        wellColumn.setCellValueFactory(cellData -> cellData.getValue().getDidWellProperty());
        betterColumn.setCellValueFactory(cellData -> cellData.getValue().getBeBetterProperty());
       
        well30Report.setItems(this.getWell30());
        better30Report.setItems(this.getBetter30());
    }
    
        public ObservableList<learningsDidWell> getWell30(){
        List<learningsDidWell> well30 = FXCollections.observableArrayList();
        
        try{
            ResultSet tableRs = db.getResultSet("SELECT DID_WELL FROM LEARNINGS");
            
            while (tableRs.next()){
                well30.add(new learningsDidWell(tableRs.getString("DID_WELL")));
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
            ResultSet tableRs = db.getResultSet("SELECT BE_BETTER FROM LEARNINGS");
            
            while (tableRs.next()){
                better30.add(new learningsDoBetter(tableRs.getString("BE_BETTER")));
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        System.out.println(better30);
        return FXCollections.observableArrayList(better30);
}
        /*
    private void loadTable(){
        wellColumn.setCellValueFactory(new PropertyValueFactory<learningsDidWell, String>("didWell"));
        
        well30Report.setItems(getWell30());
    }
 /*
    public ObservableList<learningsDidWell> getWell30(){
        ObservableList<learningsDidWell> well30View = FXCollections.observableArrayList();
        
        try {
            ResultSet tableWell = db.getResultSet("SELECT DID_WELL, COUNT (*) FROM LEARNINGS"
    
    
                    + "GROUP BY DID_WELL"
                    + "HAVING COUNT(*) >1 "
                    + "ORDER BY COUNT(*) " 
                    + "WHERE USERNAME = '" + LoginScreenController.loginUsername + "';");
            
            while (tableWell.next()){
                well30View.add(new learningsDidWell(tableWell.getString("didWell")));
            }
        } catch (Exception e){
            e.printStackTrace();
            
        }
        return FXCollections.observableArrayList(well30View);
    }
    
    public ObservableList<learningsDoBetter> getBetter30(){
        ObservableList<learningsDoBetter> better30View = FXCollections.observableArrayList();
        
        try{
            ResultSet tableBetter = db.getResultSet("SELECT DO_BETTER, COUNT(*) FROM LEARNINGS "
                    + "GROUP BY DO_BETTER "
                    + "HAVING COUNT (*)>1 "
                    + "ORDER BY COUNT(*)>1 "
                    + "WHERE USERNAME = " + LoginScreenController.loginUsername + "';");
            
            while (tableBetter.next()){
                better30View.add(new learningsDoBetter(tableBetter.getString(1), tableBetter.getInt(2)));
        }
    } catch(Exception e){
        e.printStackTrace();
    }
        return FXCollections.observableArrayList(better30View);
}  */
    @FXML
    private void handleBack(ActionEvent event) throws IOException{
        psh.switcher(event, "DailyLearnings.fxml");
    }
}