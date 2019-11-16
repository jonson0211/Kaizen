package kaizen.DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class dailyBreakdownDM {

    private StringProperty activity;
    private IntegerProperty duration;
    private StringProperty date;

    public String getActivity() {
        return activity.get();
    }

    public StringProperty getActivityProperty() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
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

    public String getDate() {
        return date.get();
    }

    public StringProperty getDateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public dailyBreakdownDM(String activity, Integer duration, String date) {
        this.activity = new SimpleStringProperty(activity);
        this.duration = new SimpleIntegerProperty(duration);
        this.date = new SimpleStringProperty(date);
    }
}
