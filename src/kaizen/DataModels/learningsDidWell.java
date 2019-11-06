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
    private IntegerProperty didWellCount;

    public learningsDidWell(String didWell, int count){
        this.didWell = new SimpleStringProperty(didWell);
        this.didWellCount = new SimpleIntegerProperty(count);
    } 

    public String getDidWell(){
        return didWell.get();
    }
    
    public StringProperty getDidWellProperty() {
        return didWell;
    }

    public IntegerProperty getDidWellCount() {
        return didWellCount;
    }
    
    public IntegerProperty getDidWellCountProperty(){
        return didWellCount;
    }
    
}
    