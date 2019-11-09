/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen.DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author wongad1
 */
public class learningsDidWell {
    

    private StringProperty didWell;
    
        public learningsDidWell(String didWell) {
        this.didWell = new SimpleStringProperty(didWell);
    }
    
    public String getDidWell(){
        return didWell.get();
    }
    public StringProperty getDidWellProperty(){
        return didWell;
    }
    public void setDidWell(String didWell){
        this.didWell.set(didWell);
    }

}