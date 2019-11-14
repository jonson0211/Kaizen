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
public class learningsEntryDM {
    private StringProperty date;
    private StringProperty achievements;
    private StringProperty improvements;
    private IntegerProperty pk;
    
    public learningsEntryDM(String date, String achievements, String improvements, Integer pk){
        this.date = new SimpleStringProperty(date);
        this.achievements = new SimpleStringProperty(achievements);
        this.improvements = new SimpleStringProperty(improvements);
        this.pk = new SimpleIntegerProperty(pk);
    }
    
    public String getDate(){
       return date.get(); 
    }
    public StringProperty getDateProperty(){
        return date;
    }
    public void setDate(String date){
        this.date.set(date);
    }
    public String getAchievements(){
       return achievements.get(); 
    }
    public StringProperty getAchievementsProperty(){
        return achievements;
    }
    public void setAchievements(String achievements){
        this.achievements.set(achievements);
    }
    public String getImprovements(){
       return improvements.get(); 
    }
    public StringProperty getImprovementsProperty(){
        return improvements;
    }
    public void setImprovements(String improvements){
        this.improvements.set(improvements);
    }
    public Integer getPk(){
       return pk.get(); 
    }
    public IntegerProperty getPkProperty(){
        return pk;
    }
    public void setPk(Integer pk){
        this.pk.set(pk);
    }
    
}
