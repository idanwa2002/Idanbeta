package com.example.idanbeta;

import java.sql.Date;
import java.sql.Time;
/**
 * @author		Idan Wasserblat <uxrcky@gmail.com>
 * @version	1.6
 * class fback that saves the client's feedback using fb,date,time,task,done,rate,cName
 */
class Fback {
    private String fb,cName;
    private String time;
    private String date,task;
    private Boolean done;
    private int rate;
    public Fback (){}
    public Fback (String cName,String fb, String time, String date, Boolean done,int rate,String task) {
        this.fb=fb;
        this.time=time;
        this.date=date;
        this.done=done;
        this.rate=rate;
        this.task=task;
        this.cName=cName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName=cName;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb=fb;
    }

    public Boolean getDone() {return done;}

    public void setDone(Boolean done) {this.done=done;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time=time;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date=date;}

    public int getRate() {return rate;}

    public void setRate(int rate) {this.rate=rate;}

    public String getTask() {return task;}

    public void setTask(String task) {this.task=task;}
}
