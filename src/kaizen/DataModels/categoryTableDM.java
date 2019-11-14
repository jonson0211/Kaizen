/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import kaizen.SettingsController;
import kaizen.UserData.KaizenDatabase;

/**
 *
 * @author lienzhu
 */
public class categoryTableDM {
    
    
    
    private String categoryName;
    private String colour;
    private String categoryID;

    
    
    public categoryTableDM(String categoryID, String categoryName, String categoryColour) {
        this.categoryName = categoryName;
        this.colour = categoryColour;
        this.categoryID = categoryID;
        
        
    }


    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    
}