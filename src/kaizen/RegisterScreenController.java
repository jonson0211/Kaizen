/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;

/**
 *
 * @author Raymond
 */
public class RegisterScreenController{
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField username;
    
    @FXML
    private PasswordField pw; 
    
    @FXML
    private PasswordField confirmPw;
    
    @FXML
    private Button cancelRegistration;
    
    @FXML
    private Button signUp;
    
    KaizenDatabase userDB = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    }

@FXML
private void handleCancel(ActionEvent event) throws IOException{
pageSwitchHelper.switcher(event, "LoginScreen.fxml");
}

public String getLoggedInUser(){
return loggedInUser;
}