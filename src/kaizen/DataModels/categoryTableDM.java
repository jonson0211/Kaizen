/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author lienzhu
 */
public class categoryTableDM {

    private StringProperty categoryName;
    private StringProperty colour;
    
    public categoryTableDM(String categoryName, String categoryColour) {
        this.categoryName = new SimpleStringProperty(categoryName);
        this.colour = new SimpleStringProperty(categoryColour);
    }

      
    
    public StringProperty getCategoryNameProperty() {
        return categoryName;
    }

    public void setCategoryName(StringProperty categoryName) {
        this.categoryName = categoryName;
    }

    public StringProperty getCategoryColourProperty() {
        return colour;
    }

    public void setColour(StringProperty colour) {
        this.colour = colour;
    }
  

}
