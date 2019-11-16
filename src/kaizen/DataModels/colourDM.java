package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class colourDM {

    private StringProperty colourChoice;

    public colourDM(String colourChoice) {
        this.colourChoice = new SimpleStringProperty(colourChoice);
    }

    public StringProperty getColourChoiceProperty() {
        return colourChoice;
    }

    public String getColourChoice() {
        return colourChoice.get();
    }

    public void setColourChoice(StringProperty colourChoice) {
        this.colourChoice = colourChoice;
    }

}
