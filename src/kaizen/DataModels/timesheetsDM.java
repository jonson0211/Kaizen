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
 * @author lienzhu
 */
public class timesheetsDM {
    
    private StringProperty activity;
    private StringProperty category;
    private StringProperty colour;
    private StringProperty date;
    private StringProperty description;
    private IntegerProperty duration;
    private StringProperty timeStart;
    private StringProperty timeEnd;

    public timesheetsDM(String activity , String category, String colour, String date, String description, Integer duration, String timeStart, String timeEnd) {
        this.activity = new SimpleStringProperty(activity);
        this.category = new SimpleStringProperty(category);
        this.colour = new SimpleStringProperty(colour);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);;
        this.duration = new SimpleIntegerProperty(duration);;
        this.timeStart = new SimpleStringProperty(timeStart);;
        this.timeEnd = new SimpleStringProperty(timeEnd);;
    }
    
    
    public StringProperty getActivity() {
        return activity;
    }

    public void setActivity(StringProperty activity) {
        this.activity = activity;
    }

    public StringProperty getCategory() {
        return category;
    }

    public void setCategory(StringProperty category) {
        this.category = category;
    }

    public StringProperty getColour() {
        return colour;
    }

    public void setColour(StringProperty colour) {
        this.colour = colour;
    }

    public StringProperty getDate() {
        return date;
    }

    public void setDate(StringProperty date) {
        this.date = date;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public IntegerProperty getDuration() {
        return duration;
    }

    public void setDuration(IntegerProperty duration) {
        this.duration = duration;
    }

    public StringProperty getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(StringProperty timeStart) {
        this.timeStart = timeStart;
    }

    public StringProperty getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(StringProperty timeEnd) {
        this.timeEnd = timeEnd;
    }
    
}