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
public class colourDM {
   private StringProperty colourChoice;
    
    public colourDM(String colourChoice) {
         this.colourChoice = new SimpleStringProperty(colourChoice);
    }

    public StringProperty getColourChoiceProperty() {
        return colourChoice;
    }
    
     public String getColourChoice(){
        return colourChoice.get();
    }

    public void setColourChoice(StringProperty colourChoice) {
        this.colourChoice = colourChoice;
    }
    
} 

