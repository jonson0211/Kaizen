/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen.DataModels;

import java.sql.Date;
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
    
    public learningsEntryDM(String date, String achievements, String improvements){
        this.date = new SimpleStringProperty(date);
        this.achievements = new SimpleStringProperty(achievements);
        this.improvements = new SimpleStringProperty(improvements);
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
    
}
