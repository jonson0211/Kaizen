package kaizen.DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class pieChartDM {

    private StringProperty category;
    private IntegerProperty duration;

    public StringProperty getCategoryProperty() {
        return category;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCatChoice(StringProperty catChoice) {
        this.category = catChoice;
    }

    public String setCatChoice(String catChoice) {
        this.category.get();
        return catChoice;
    }

    public Integer getDuration() {
        return duration.get();
    }

    public IntegerProperty getDurationProperty() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration.set(duration);
    }

    public pieChartDM(String category, Integer duration) {
        this.category = new SimpleStringProperty(category);
        this.duration = new SimpleIntegerProperty(duration);
    }

}
