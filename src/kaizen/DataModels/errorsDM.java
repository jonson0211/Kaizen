package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class errorsDM {

    private StringProperty errorID;
    private StringProperty date;
    private StringProperty errorName;
    private StringProperty errorPage;
    private StringProperty errorDesc;

    public errorsDM(String errorID, String date, String errorName, String errorPage, String errorDesc) {
        this.errorID = new SimpleStringProperty(errorID);
        this.date = new SimpleStringProperty(date);
        this.errorName = new SimpleStringProperty(errorName);
        this.errorPage = new SimpleStringProperty(errorPage);
        this.errorDesc = new SimpleStringProperty(errorDesc);
    }

    public String getErrorID() {
        return errorID.get();
    }

    public StringProperty getErrorIDProperty() {
        return errorID;
    }

    public void setID(String errorID) {
        this.errorID.set(errorID);
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

    public String getErrorName() {
        return errorName.get();
    }

    public StringProperty getErrorNameProperty() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName.set(errorName);
    }

    public String getErrorPage() {
        return errorPage.get();
    }

    public StringProperty getErrorPageProperty() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage.set(errorPage);
    }

    public String getErrorDesc() {
        return errorDesc.get();
    }

    public StringProperty getErrorDescProperty() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc.set(errorDesc);
    }

}
