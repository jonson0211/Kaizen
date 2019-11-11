/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaizen;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author jonso
 * 
 * //(USERNAME, TITLE,CATEGORYNAME, DESCRIPTION, DO_DATE, DUE_DATE, PRIORITY
 */

//
public class Task {
    
    private StringProperty title;
    private StringProperty category;
    private StringProperty description;
    private StringProperty doDate;
    private StringProperty dueDate;
    private StringProperty priority;
    
    
    public Task(){
        this("Title", "Description", "Category", "Do Date", "Due Date", "Priority");
    }
    public Task(String title, String description, String category, String doDate, String dueDate, String priority) {
        this.title = new SimpleStringProperty(title);
        this.description= new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.doDate = new SimpleStringProperty(doDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.priority = new SimpleStringProperty(priority);
        
    }

    public StringProperty getTitle() {
        return title;
    }

    public void setTitle(StringProperty title) {
        this.title = title;
    }

    public StringProperty getCategory() {
        return category;
    }

    public void setCategory(StringProperty category) {
        this.category = category;
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(StringProperty description) {
        this.description = description;
    }

    public StringProperty getDoDate() {
        return doDate;
    }

    public void setDoDate(StringProperty doDate) {
        this.doDate = doDate;
    }

    public StringProperty getDueDate() {
        return dueDate;
    }

    public void setDueDate(StringProperty dueDate) {
        this.dueDate = dueDate;
    }

    public StringProperty getPriority() {
        return priority;
    }

    public void setPriority(StringProperty priority) {
        this.priority = priority;
    }
    
}
