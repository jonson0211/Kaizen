/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen.DataModels;

/**
 *
 * @author wongad1
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author wongad1
 */
public class learningsDoBetter {
    private StringProperty doBetter;
    private IntegerProperty doBetterCount;

    public learningsDoBetter(String doBetter, int count){
        this.doBetter = new SimpleStringProperty(doBetter);
        this.doBetterCount = new SimpleIntegerProperty(count);
    } 

    public StringProperty getDoBetter() {
        return doBetter;
    }
    
    public String getDoBetterProperty(){
        return doBetter.get();
    }
    
    public IntegerProperty getDoBetterCount() {
        return doBetterCount;
    }
    public Integer getDoBetterCountProperty(){
        return doBetterCount.get();
        
    }  
}