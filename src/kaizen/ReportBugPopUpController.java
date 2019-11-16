package kaizen;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ReportBugPopUpController implements Initializable {

    @FXML
    private TextField errorNameTxt;
    @FXML
    private TextField errorPageTxt;
    @FXML
    private TextArea errorDescTxt;
    @FXML
    private DatePicker DtPicker;

    @FXML
    private Button submit;
    @FXML
    private Label status;

    KaizenDatabase database = new KaizenDatabase();
    PageSwitchHelper pageSwitcher = new PageSwitchHelper();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        status.setVisible(false);

    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException, ParseException {
        try {
            String errorName = errorNameTxt.getText();
            String errorPage = errorPageTxt.getText();
            String errorDesc = errorDescTxt.getText();
            String date = DtPicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            try {

                database.insertStatement("INSERT INTO ERRORS(DATE, ERRORNAME, ERRORPAGE, DESCRIPTION)"
                        + " VALUES('" + date + "', '" + errorName + "', '" + errorPage + "','" + errorDesc + "');");

                status.setText("Bug reported!");
                status.setVisible(true);

            } catch (Exception ex) {
                System.out.println("Could not report bug. Please check your inputs!");
                status.setText("Could not report entry. Please check your inputs.");
                status.setVisible(true);
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            System.out.println("Could not report bug. Please check your inputs!");
            status.setText("Could not report bug. Please check your inputs.");
            status.setVisible(true);
            ex.printStackTrace();
        }
    }

}
