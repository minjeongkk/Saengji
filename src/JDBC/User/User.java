package JDBC.User;

import java.util.Date;

public class User {
    private int ID;
    private String userId;
    private String userPw;
    private String phoneNumber;
    private Date birthday;
    private Date created;
    private String userName;

    //생성자
    public User(){}
    public User(String userId, String userPw, String phoneNumber, Date birthday, String userName) {
        this.userId = userId;
        this.userPw = userPw;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.userName = userName;
    }

    //getter, setter
    public int getID() {
        return ID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}