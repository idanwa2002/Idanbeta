package com.example.idanbeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * @author		Idan Wasserblat <uxrcky@gmail.com>
 * @version	1.6
 * class exercises that saves info about exercises for the users using pname, cname, time, date, advice, tname, active
 */
public class Exercises {
    private String pName, cName, advice, tName;//,url;
    private String time;
    //private Date date;
    //private int days;
    //private ArrayList<Feedback> fb = new ArrayList<>();
    private String date;
    private Boolean active;

    public Exercises (){}
    public Exercises (String pName, String cName, String time, String advice, String tName, String date, Boolean active) {
        this.pName=pName;
        this.cName=cName;
        this.time=time;
        this.advice=advice;
        this.tName=tName;
        //this.days=days;
        //this.url=url;
        this.date=date;
        this.active=active;
        //this.fb=fb;

    }
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName=pName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName=cName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time=time;
    }

   /// public String getUrl() {
    //    return url;
   // }

   // public void setUrl(String url) {
      //  this.url=url;
   // }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice=advice;
    }

    public String gettName() {return tName;}

    public void settName(String tName) {this.tName=tName;}

    //public int getDays() {return days;}

    //public void setDays(int days) {this.days=days;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date=date;}

    public Boolean getActive() {return active;}

    public void setActive(Boolean date) {this.active=active;}
}
