package kaizen;

import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kaizen.UserData.KaizenDatabase;

public class AddCategoryController implements Initializable {

    @FXML
    private Button addCat;
    @FXML
    private TextField catName;
    @FXML
    private ColorPicker catColour;
    @FXML
    private Label catOutput;
    @FXML
    private Button exit;

    KaizenDatabase database = new KaizenDatabase();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        catOutput.setVisible(false);

    }

    @FXML
    private void handleSubmitAction(ActionEvent event) throws SQLException, ParseException {

        String category = catName.getText();
        String colour = catColour.getValue().toString();

        try {

            database.insertStatement(
                    "INSERT INTO CATEGORY ("
                    + "CATEGORYNAME, COLOUR)"
                    + " VALUES('" + category + "', '"
                    + colour + "');"
            );

            catOutput.setText("Category added!");
            catOutput.setVisible(true);

        } catch (Exception ex) {
            System.out.println("Could not add entry. Please check your inputs!");
            catOutput.setText("Could not add entry. Please check your inputs!");
            catOutput.setVisible(true);
            ex.printStackTrace();
        }

    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

}
