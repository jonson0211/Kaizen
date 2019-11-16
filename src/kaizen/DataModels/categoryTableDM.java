package kaizen.DataModels;

public class categoryTableDM {

    private String categoryName;
    private String colour;
    private String categoryID;

    public categoryTableDM(String categoryID, String categoryName, String categoryColour) {
        this.categoryName = categoryName;
        this.colour = categoryColour;
        this.categoryID = categoryID;

    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

}
