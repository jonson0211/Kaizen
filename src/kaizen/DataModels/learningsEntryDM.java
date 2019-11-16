package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class learningsEntryDM {

    private StringProperty date;
    private StringProperty achievements;
    private StringProperty improvements;
    private StringProperty pk;

    public learningsEntryDM(String date, String achievements, String improvements, String pk) {
        this.date = new SimpleStringProperty(date);
        this.achievements = new SimpleStringProperty(achievements);
        this.improvements = new SimpleStringProperty(improvements);
        this.pk = new SimpleStringProperty(pk);
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

    public String getAchievements() {
        return achievements.get();
    }

    public StringProperty getAchievementsProperty() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements.set(achievements);
    }

    public String getImprovements() {
        return improvements.get();
    }

    public StringProperty getImprovementsProperty() {
        return improvements;
    }

    public void setImprovements(String improvements) {
        this.improvements.set(improvements);
    }

    public String getPk() {
        return pk.get();
    }

    public StringProperty getPkProperty() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk.set(pk);
    }

}
