package kaizen.DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class learningsDidWell {

    private StringProperty didWell;
    private IntegerProperty didWellCount;

    public learningsDidWell(String didWell, Integer didWellCount) {
        this.didWell = new SimpleStringProperty(didWell);
        this.didWellCount = new SimpleIntegerProperty(didWellCount);
    }

    public String getDidWell() {
        return didWell.get();
    }

    public StringProperty getDidWellProperty() {
        return didWell;
    }

    public void setDidWell(String didWell) {
        this.didWell.set(didWell);
    }

    public Integer getDidWellCount() {
        return didWellCount.get();
    }

    public IntegerProperty getDidWellCountProperty() {
        return didWellCount;
    }

    public void setDidWellCount(Integer didWellCount) {
        this.didWellCount.set(didWellCount);
    }

}
