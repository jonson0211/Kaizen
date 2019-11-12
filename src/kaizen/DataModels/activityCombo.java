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
public class activityCombo {
    private StringProperty actChoice;
    
    public activityCombo(String actChoice) {
         this.actChoice = new SimpleStringProperty(actChoice);
    }

    public StringProperty getActChoiceProperty() {
        return actChoice;
    }
    
     public String getActChoice(){
        return actChoice.get();
    }

    public void setActChoice(StringProperty actChoice) {
        this.actChoice = actChoice;
    }
    
}

