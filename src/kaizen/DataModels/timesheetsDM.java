package kaizen.DataModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import kaizen.EntriesScreenController;

public class timesheetsDM {

    private StringProperty timesheetID;
    private StringProperty activity;
    private StringProperty category;
    //   private StringProperty colour;
    private StringProperty date;
    private StringProperty description;
    private IntegerProperty duration;
    private StringProperty timeStart;
    private StringProperty timeEnd;

    public timesheetsDM(String timesheetID, String activity, String category, String date,
            String description, Integer duration, String timeStart, String timeEnd) {
        this.timesheetID = new SimpleStringProperty(timesheetID);
        this.activity = new SimpleStringProperty(activity);
        this.category = new SimpleStringProperty(category);
        //  this.colour = new SimpleStringProperty(colour);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);;
        this.duration = new SimpleIntegerProperty(duration);;
        this.timeStart = new SimpleStringProperty(timeStart);;
        this.timeEnd = new SimpleStringProperty(timeEnd);;

        ObservableList<timesheetsDM> entry = EntriesScreenController.entriesView_2.getSelectionModel().getSelectedItems();
    }

    public String getTimesheetID() {
        return timesheetID.get();
    }

    public StringProperty getTimesheetIDProperty() {
        return timesheetID;
    }

    public void setTimesheetID(String timesheetID) {
        this.timesheetID.set(timesheetID);
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

    public String getCategory() {
        return category.get();
    }

    public StringProperty getCategoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getActivity() {
        return activity.get();
    }

    public StringProperty getActivityProperty() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public String getDesc() {
        return description.get();
    }

    public StringProperty getDescProperty() {
        return description;
    }

    public void setDesc(String description) {
        this.activity.set(description);
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

    public String getStart() {
        return timeStart.get();
    }

    public StringProperty getStartProperty() {
        return timeStart;
    }

    public void setStart(String timeStart) {
        this.timeStart.set(timeStart);
    }

    public String getEnd() {
        return timeEnd.get();
    }

    public StringProperty getEndProperty() {
        return timeEnd;
    }

    public void setEnd(String timeEnd) {
        this.timeEnd.set(timeEnd);
    }
}
