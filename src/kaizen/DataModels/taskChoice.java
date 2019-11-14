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
 * @author Raymond
 */
public class taskChoice {
    
        private StringProperty taskChoice;

        public taskChoice(String taskChoice) {
            this.taskChoice = new SimpleStringProperty(taskChoice);
        }

        public StringProperty getTaskChoiceProperty() {
            return taskChoice;
        }

        public String getTaskChoice() {
            return taskChoice.get();
        }

        public void setTaskChoice(StringProperty taskChoice) {
            this.taskChoice = taskChoice;
        }
    }