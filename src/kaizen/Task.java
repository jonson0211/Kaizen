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
public class Task {
    private final StringProperty title = new SimpleStringProperty(this, "title");
    public StringProperty titleProperty() {
        return title;
    }

    public final String getTitle() {
        return titleProperty().get();
    }
    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    private final StringProperty categoryName = new SimpleStringProperty(this, "categoryName");
    public StringProperty categoryNameProperty() {
        return categoryName;
    }
    public final String getCategoryName() {
        return categoryNameProperty().get();
    }
    public final void setCategoryName(String categoryName) {
        categoryNameProperty().set(categoryName);
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");
    public StringProperty descriptionProperty() {
        return description;
    }
    public final String getDescription() {
        return descriptionProperty().get();
    }
    public final void setDescription(String description) {
        descriptionProperty().set(description);
    }
    
    private final StringProperty priority = new SimpleStringProperty(this, "priority");
    public StringProperty priorityProperty() {
        return priority;
    }
    public final String getPriority() {
        return priorityProperty().get();
    }
    public final void setPriority(String priority) {
        priorityProperty().set(priority);
    }
    
    private final StringProperty doDate = new SimpleStringProperty(this, "doDate");
    public StringProperty doDateProperty() {
        return doDate;
    }
    public final String getDoDate() {
        return doDateProperty().get();
    }
    public final void setDoDate(String doDate) {
       doDateProperty().set(doDate);
    }
    
    private final StringProperty dueDate = new SimpleStringProperty(this, "dueDate");
    public StringProperty dueDateProperty() {
        return dueDate;
    }
    public final String getDueDate() {
        return dueDateProperty().get();
    }
    public final void setDueDate(String dueDate) {
        dueDateProperty().set(dueDate);
    }
    
    public Task(String title, String categoryName, String description, String priority, String doDate, String dueDate) {
        setTitle(title);
        setCategoryName(categoryName);
        setDescription(description);
        setPriority(priority);
        setDoDate(doDate);
        setDueDate(dueDate);
    }
}
