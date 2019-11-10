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
    String doBetter;
    Integer doBetterCount;

    public learningsDoBetter(String doBetter, Integer doBetterCount) {
        this.doBetter = doBetter;
        this.doBetterCount = doBetterCount;
    }

    public String getDoBetter() {
        return doBetter;
    }

    public void setDoBetter(String doBetter) {
        this.doBetter = doBetter;
    }

    public Integer getDoBetterCount() {
        return doBetterCount;
    }

    public void setDoBetterCount(Integer doBetterCount) {
        this.doBetterCount = doBetterCount;
    }
    
    
}