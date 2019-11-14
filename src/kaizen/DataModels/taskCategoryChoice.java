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
public class taskCategoryChoice {
   
        private StringProperty taskCategoryChoice;

        public taskCategoryChoice(String taskCategoryChoice) {
            this.taskCategoryChoice = new SimpleStringProperty(taskCategoryChoice);
        }

        public StringProperty getTaskCategoryChoiceProperty() {
            return taskCategoryChoice;
        }

        public String getTaskCategoryChoice() {
            return taskCategoryChoice.get();
        }

        public void setTaskCategoryChoice(StringProperty taskCategoryChoice) {
            this.taskCategoryChoice = taskCategoryChoice;
        }
    }