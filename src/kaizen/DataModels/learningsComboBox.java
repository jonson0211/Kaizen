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
public class learningsComboBox {
    private StringProperty dw;
    
        public learningsComboBox(String dw) {
        this.dw = new SimpleStringProperty(dw);
    }

    public StringProperty getDw() {
        return dw;
    }

    public void setDw(StringProperty dw) {
        this.dw = dw;
    }
    
    public StringProperty getDwProperty(){
        return dw;
    }
}