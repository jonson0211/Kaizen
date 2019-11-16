package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
