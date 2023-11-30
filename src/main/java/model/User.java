package model;


/**
 *
 * @author capoo
 */

public class User {
     private int userID;
    private String username;
    private String password;
    private String Sodienthoai;
      private String Cccd;
    private String name;
    private String birthday;
    private String Add;
    private String Img;
        private boolean role;
    private boolean IsLooked;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getSodienthoai() {
        return Sodienthoai;
    }

    public void setSodienthoai(String Sodienthoai) {
        this.Sodienthoai = Sodienthoai;
    }

    public String getCccd() {
        return Cccd;
    }

    public void setCccd(String Cccd) {
        this.Cccd = Cccd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAdd() {
        return Add;
    }

    public void setAdd(String Add) {
        this.Add = Add;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isIsLooked() {
        return IsLooked;
    }

    public void setIsLooked(boolean IsLooked) {
        this.IsLooked = IsLooked;
    }
    
    
}
