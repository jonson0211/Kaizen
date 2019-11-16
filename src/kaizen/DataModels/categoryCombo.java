package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class categoryCombo {

    private StringProperty catChoice;

    public categoryCombo(String catChoice) {
        this.catChoice = new SimpleStringProperty(catChoice);
    }

    public StringProperty getCatChoiceProperty() {
        return catChoice;
    }

    public String getCatChoice() {
        return catChoice.get();
    }

    public void setCatChoice(StringProperty catChoice) {
        this.catChoice = catChoice;
    }

    public String setCatChoice(String catChoice) {
        this.catChoice.get();
        return catChoice;
    }
}
