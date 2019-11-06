/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

/**
 *
 * @author Raymond
 */

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;

public class LoginScreenController implements Initializable {
    
    public static String loginUsername;
    
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button signInButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label loginOutput;

    KaizenDatabase userDatabase = new KaizenDatabase();

    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
    }

    //Action on login button click
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

        loginUsername = usernameInput.getText();
        String loginPassword = passwordInput.getText();

        try {

            ResultSet rs = userDatabase.getResultSet("SELECT USERNAME, PASSWORD FROM LOGIN WHERE "
                    + "USERNAME = '" + loginUsername + "' AND "
                    + "PASSWORD = '" + loginPassword + "' ");

            if (!rs.next()) {

                loginOutput.setText("Your username or password is incorrect. Please check and try again.");
                loginOutput.setVisible(true);

            } else {
                loginOutput.setText("Welcome back to Kaizen.");
                pageSwitcher.switcher(event, "KanbanBoard.fxml");
                loginOutput.setVisible(true);
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // don't forget to include code that indicates which user is logged in
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws IOException {
        System.out.println("System milestone 1");
        pageSwitcher.switcher(event, "RegisterScreen.fxml");
    }

    public void initialize() {

        loginOutput.setVisible(false);

    }

}
