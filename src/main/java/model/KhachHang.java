

package model;

public class KhachHang {
    private int Ma_KH;
    private String Name;
    private String SDT;
    private int Diem;
    private String email;
    private String birthday;

    public int getMa_KH() {
        return Ma_KH;
    }

    public void setMa_KH(int Ma_KH) {
        this.Ma_KH = Ma_KH;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getDiem() {
        return Diem;
    }

    public void setDiem(int Diem) {
        this.Diem = Diem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
