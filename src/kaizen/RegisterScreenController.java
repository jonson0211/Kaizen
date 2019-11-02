/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;

/**
 *
 * @author Raymond
 */
//REMEMBER TO WRITE EXCEPTION FOR WHEN SOMEONE TRIES TO REGISTER USING THE 
//SAME USERNAME AS USERNAME IS PRIMARY KEY AND UNIQUE



public class RegisterScreenController implements Initializable{
    
    @FXML
    private TextField fname;
    
    @FXML
    private TextField lname;
    
    @FXML
    private TextField email;
       
    @FXML
    private PasswordField pw; 
    
    @FXML
    private PasswordField confirmedPw;
    
    @FXML
    private Button cancelRegistration;
    
    @FXML
    private Button signUp;
    
    KaizenDatabase userDB = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    public static String loggedInUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //When user clicks cancel
@FXML
private void handleCancel(ActionEvent event) throws IOException{
pageSwitcher.switcher(event, "LoginScreen.fxml");
}

//Insert valuesd into user table

@FXML
private void handleSignUp(ActionEvent event){
    String addFname = fname.getText();
    String addLname = lname.getText();
    String addEmail = email.getText();
    String addPassword = pw.getText();
    String addPasswordConfirm = confirmedPw.getText();
    loggedInUser = addEmail;
    System.out.println(loggedInUser);
    
    if (addFname.isEmpty() || addLname.isEmpty() || addPassword.isEmpty() 
            || addEmail.isEmpty() || addPasswordConfirm.isEmpty()){
        
       Alert a = new Alert(AlertType.ERROR);
       a.setHeaderText("Required fields cannot be empty");
       a.setContentText("Please fill in all required fields");
       a.show();
        }
    else if (addPassword != addPasswordConfirm){
       Alert apw = new Alert(AlertType.ERROR);
       apw.setHeaderText("Password don't match");
       apw.setContentText("Passwords must match");
       apw.show();       
       }
    else if (addEmail = userDB.)
    else {
       try{ userDB.insertStatement("INSERT INTO LOGIN("
               + "FNAME, "
               + "LNAME, "
               + "EMAIL, "
               + "PASSWORD) "
               + "VALUES('" + addFname + "', '" + addLname + "', '" + addEmail + "', '" + addPassword + "'?','?','?','?' ");
       } catch(Exception e){
           
       }
       
       
    }
}
    
public String getLoggedInUser(){
        return loggedInUser;
}
}
