package kaizen.DataModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskDM {

    private StringProperty taskID;
    private StringProperty title;
    private StringProperty category;
    private StringProperty description;
    private StringProperty doDate;
    private StringProperty dueDate;
    private StringProperty priority;

//    public TaskDM(){
//        this("taskID", "Title", "Description", "Category", "Do Date", "Due Date", "Priority");
    //}
    public TaskDM(String ID, String title, String description, String category,
            String doDate, String dueDate, String priority) {

        this.taskID = new SimpleStringProperty(ID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.doDate = new SimpleStringProperty(doDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.priority = new SimpleStringProperty(priority);

    }

    public String getTaskID() {
        return taskID.get();
    }

    public StringProperty getTaskIDProperty() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID.set(taskID);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDoDate() {
        return doDate.get();
    }

    public StringProperty getDoDateProperty() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate.set(doDate);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public StringProperty getDueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty getPriorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

}
