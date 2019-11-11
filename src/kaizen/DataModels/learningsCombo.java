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
 * @author wongad1
 */
public class learningsCombo {
    private StringProperty dw;
    
    public learningsCombo(String dw){
        this.dw = new SimpleStringProperty(dw);
    }

    public StringProperty getDwProperty() {
        return dw;
    }
    
    public String getDw(){
        return dw.get();
    }

    public void setDw(StringProperty dw) {
        this.dw = dw;
    }
    
}
