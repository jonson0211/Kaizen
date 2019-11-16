package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
