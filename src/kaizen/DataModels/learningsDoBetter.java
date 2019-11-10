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
    
    private StringProperty beBetter;
    private IntegerProperty beBetterCount;
    
        public learningsDoBetter(String beBetter, Integer beBetterCount) {
        this.beBetter = new SimpleStringProperty(beBetter);
        this.beBetterCount = new SimpleIntegerProperty(beBetterCount);
    }
    
    public String getBeBetter(){
        return beBetter.get();
    }
    public StringProperty getBeBetterProperty(){
        return beBetter;
    }
    public void setBeBetter(String beBetter){
        this.beBetter.set(beBetter);
    }
    public Integer getBeBetterCount(){
        return beBetterCount.get();
    }
    public IntegerProperty getBeBetterCountProperty(){
        return beBetterCount;
    }
    public void setBeBetterCount(Integer beBetterCount){
        this.beBetterCount.set(beBetterCount);
    }
    
}