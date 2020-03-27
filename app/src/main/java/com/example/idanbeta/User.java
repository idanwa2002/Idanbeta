package com.example.idanbeta;

public class User {
    private String name, email, phone, uid;
    private Boolean phy;

    //public User (){}
    public User (String name, String email, String phone, String uid, Boolean phy) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.phy=phy;

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

    public Boolean getPhy() {
        return phy;
    }

    public void setPhy(Boolean phy) {
        this.phy=phy;
    }
}
