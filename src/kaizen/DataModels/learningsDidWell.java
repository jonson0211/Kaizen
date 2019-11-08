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
public class learningsDidWell {
    

    String didWell;
    Integer didWellCount;
    
    public learningsDidWell(Integer didWellCount, String didWell) {
        this.didWellCount = didWellCount;
        this.didWell = didWell;
    }

    public Integer getDidWellCount() {
        return didWellCount;
    }

    public void setDidWellCount(Integer didWellCount) {
        this.didWellCount = didWellCount;
    }

    public String getDidWell() {
        return didWell;
    }

    public void setDidWell(String didWell) {
        this.didWell = didWell;
    }
    
    
}

    
    
    