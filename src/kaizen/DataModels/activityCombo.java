package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class activityCombo {

    private StringProperty actChoice;

    public activityCombo(String actChoice) {
        this.actChoice = new SimpleStringProperty(actChoice);
    }

    public StringProperty getActChoiceProperty() {
        return actChoice;
    }

    public String getActChoice() {
        return actChoice.get();
    }

    public void setActChoice(StringProperty actChoice) {
        this.actChoice = actChoice;
    }

}
