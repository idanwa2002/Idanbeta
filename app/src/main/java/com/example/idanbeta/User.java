package com.example.idanbeta;

public class User {
    private String name, email, phone, uid, phy;
    private Boolean permission;

    public User (){}
    public User (String name, String email, String phone, String uid, String phy, Boolean permission) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.phy=phy;
        this.permission=permission;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid=uid;
    }

    public String getPhy() {
        return phy;
    }

    public void setPhy(String phy) {
        this.phy=phy;
    }

    public Boolean getPermission() {return permission;}

    public void setPermission(Boolean permission) {this.permission=permission;}
}
