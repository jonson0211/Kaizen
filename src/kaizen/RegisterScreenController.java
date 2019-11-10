/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    @FXML
            private Label label;
    
    KaizenDatabase userDB = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    public static String loggedInUser;
    
    private PreparedStatement pst;
    private Connection conn;
    private ResultSet rs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setVisible(false);
        //To change body of generated methods, choose Tools | Templates.
    }

    //When user clicks cancel
@FXML
private void handleCancel(ActionEvent event) throws IOException{
pageSwitcher.switcher(event, "LoginScreen.fxml");
}

//Insert values into user table
@FXML
private void handleSignUp(ActionEvent event){
    String addFname = fname.getText();
    String addLname = lname.getText();
    String addEmail = email.getText();
    String addPassword = pw.getText();
    String addPasswordConfirm = confirmedPw.getText();
    loggedInUser = addEmail;
    System.out.println(loggedInUser);
    
    if (addEmail.isEmpty() || addPassword.isEmpty() || addPasswordConfirm.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Required fields cannot be empty!");
            alert.setContentText("Please ensure fields are complete.");
            alert.showAndWait();
        } else if (!addPassword.equals(addPasswordConfirm)) {
            Alert alertpw = new Alert(Alert.AlertType.ERROR);
            alertpw.setTitle("Error!");
            alertpw.setHeaderText("Passwords do not match!");
            alertpw.setContentText("Please ensure passwords match.");
            alertpw.showAndWait();
        } else {
    try{
        userDB.insertStatement("INSERT INTO LOGIN("
               + "FNAME, "
               + "LNAME, "
               + "USERNAME, "
               + "PASSWORD) "
               + "VALUES('" + addFname + "', '" + addLname + "', '" + addEmail + "', '" + addPassword + "')");

    label.setVisible(true);
    pageSwitcher.switcher(event, "LoginScreen.fxml");
    
       } catch(Exception e){
           e.printStackTrace();
       }
        }
}

public String getLoggedInUser(){
        return loggedInUser;
}
}
