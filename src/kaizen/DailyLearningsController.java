/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import kaizen.UserData.KaizenDatabase;

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
        String answerOneString = String.parseString(answerOne.getText());
        userLearn.insertStatement("UPDATE LEARNINGS SET DID_WELL = " + answerOneString
                + " WHERE USERNAME = '" + LoginScreenController.loggedInUsername + "'"); //need to fix when LoginScreenController done
        
        
        }
        
    }
}
