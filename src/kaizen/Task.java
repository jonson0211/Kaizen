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
    public final void setTitle(String firstName) {
        titleProperty().set(titke);
    }

    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    public StringProperty lastNameProperty() {
        return lastName ;
    }
    public final String getLastName() {
        return lastNameProperty().get();
    }
    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    private final StringProperty email = new SimpleStringProperty(this, "email");
    public StringProperty emailProperty() {
        return email ;
    }
    public final String getEmail() {
        return emailProperty().get();
    }
    public final void setEmail(String email) {
        emailProperty().set(email);
    }

}
