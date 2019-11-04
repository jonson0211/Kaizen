
package kaizen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimesheetsController implements Initializable {

    @FXML
    private RadioButton categoryWork;
    
    @FXML
    private RadioButton categoryDaily;
    
    @FXML
    private RadioButton categoryRelationships;
    
    @FXML
    private RadioButton categoryWellness;
    
    @FXML
    private RadioButton categoryRelaxation;
    
    @FXML
    private RadioButton categoryProjects;
    
    @FXML
    private TextField timeStart;
    
    @FXML
    private TextField timeEnd;
    
    @FXML
    private TextField duration;
    
    @FXML
    private TextArea description;
    
    @FXML
    private Button submit;
    
    @FXML
    private Button back;
    
    KaizenDatabase addTimesheet = new KaizenDatabase();
    
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleSubmitAction(ActionEvent event) {
        String start = timeStart.getText().trim();
        String end = timeEnd.getText();
        String desc = description.getText();
        Int actDuration = (timeStart-timeEnd);
        
        /** INSERT instead of SELECT?**/
        try {
            ResultSet rs = addTimesheet.getResultSet("SELECT * FROM TIMESHEETS WHERE "
                    + "USERNAME = '" + timeStart + "' "
                    + "AND PASSWORD = '" + timeEnd + "'");
            if (!rs.next()) {
                loginOutput.setText("Incorrect username or password");
                loginOutput.setVisible(true);
            } else {
                loginOutput.setText("Login successful");
                loginOutput.setVisible(true);
                nextBtn.setVisible(true);
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    @FXML
    private void handleBackAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "KanbanBoard.fxml");
    }
    
    @FXML
    public void initialize(){
        /** probably don't need this initialize^ unless hiding buttons or whatever**/
    }
    
}