package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class learningsCombo {

    private StringProperty dw;

    public learningsCombo(String dw) {
        this.dw = new SimpleStringProperty(dw);
    }

    public StringProperty getDwProperty() {
        return dw;
    }

    public String getDw() {
        return dw.get();
    }

    public void setDw(StringProperty dw) {
        this.dw = dw;
    }

}
