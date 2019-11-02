
package kaizen;

import java.io.IOException;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;

public class LoginScreenController {
    
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
    
    //Action on login button click
    @FXML
    private void handleLoginButtonAction (Action event) {
        
        String loginUsername = usernameInput.getText();
        String loginPassword = passwordInput.getText();
        
        try {
            
            ResultSet rs = userDatabase.getResultSet("SELECT USERNAME, PASSWORD FROM LOGIN WHERE "
                    + "USERNAME = '" + loginUsername + "' AND "
                    + "PASSWORD = '" + loginPassword + "' ");
                    
        }
        
    }
    
    
    @FXML
    private void handleRegisterButtonAction (Action event) throws IOException (String "An error occurred.") {
        
    }
    
    
    
    
    
    
    
    
    
}
