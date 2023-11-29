/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class NhanVien {
    private int MaNV;

    private String User;
    private String Pass;
    private boolean Role;
    private boolean IsLooked;  

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }
    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean Role) {
        this.Role = Role;
    }

    public boolean isIsLooked() {
        return IsLooked;
    }

    public void setIsLooked(boolean IsLooked) {
        this.IsLooked = IsLooked;
    }

   
    

}
