package kaizen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import kaizen.UserData.KaizenDatabase;

/**
 * FXML Controller class
 *
 * @author lienzhu
 */
public class AddCategoryController implements Initializable {

    
    @FXML private Button addCat;
    @FXML private TextField catName;
    @FXML private ColorPicker catColour;
    
    KaizenDatabase database = new KaizenDatabase();   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

        } catch (Exception ex) {
            System.out.println("Could not add entry. Please check your inputs!");
            ex.printStackTrace();
        }
    
    }
    
}
