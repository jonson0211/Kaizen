package kaizen.DataModels;

public class UserDataModels {

    private String username;
    private String password;
    private String fName;
    private String LName;

    public UserDataModels(String username, String password, String fName, String LName) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.LName = LName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

}
