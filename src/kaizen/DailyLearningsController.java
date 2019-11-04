/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import kaizen.UserData.KaizenDatabase;
import static kaizen.UserData.KaizenDatabase.conn;

/**
 * FXML Controller class
 *
 * @author wongad1
 */
public class DailyLearningsController implements Initializable {

    @FXML
    private ComboBox answerOne;
    
    @FXML
    private ComboBox answerTwo;
    
    @FXML
    private Button addLearningOne;
    
    @FXML
    private Button addLearningTwo;
    
    
    KaizenDatabase userLearn = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    PreparedStatement pst;
    
    Connection conn;
    
    ResultSet rs;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    //update learnings
    @FXML
    private void updateAnswerOne(ActionEvent event){
        String answerOneString = (String) answerOne.getValue();
        userLearn.insertStatement("UPDATE LEARNINGS SET DID_WELL = " + answerOneString
                + " WHERE USERNAME = '" + LoginScreenController.loggedUsername + "'"); //need to fix when LoginScreenController done
        System.out.println("Question 1 Learning updated in SQL");
        
        }
    @FXML
    private void updateAnswerTwo(ActionEvent event){
        String answerTwoString = (String) answerTwo.getValue();
        userLearn.insertStatement("UPDATE LEARNINGS SET BE_BETTER = " + answerTwoString 
                + "WHERE USERNAME = '" + LoginScreenController.loggedUsername + "'");
        System.out.println("Question 2 Learning updated in SQL");
        
    //to do - update combo box values
    }
    //see if user has already entered in previous answers
    //
    @FXML
    private void updateComboOneValue(ActionEvent event){
        try{
            ResultSet currentAnswerOne = userLearn.getResultSet("SELECT USERNAME, DID_WELL FROM LEARNINGS"
                    + "WHERE USERNAME = " + LoginScreenController.loggedUsername + " ");
            answerOne.setText(String.valueOf(currentAnswerOne.getString(3)));
        } catch(Exception e){
            System.out.println("New user");
            e.printStackTrace();
        }
    }
    //populating combobox
    private void FillCombo() throws SQLException{
        try{
            String queryOne = "SELECT * FROM LEARNINGS";
            pst = conn.prepareStatement(queryOne);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String didwell = rs.getString("DID_WELL");
                answerOne.addItem(didwell);
                
            } catch(Exception e){
                    
                    }
        }
    }
}
