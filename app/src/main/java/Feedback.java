import java.sql.Date;
import java.sql.Time;

public class Feedback {
    private String fb,cName;
    private Time time;
    private Date date;
    private Boolean done;
    private int rate;
    public Feedback (){}
    public Feedback (String cName, String fb, Time time, Date date, Boolean done,int rate) {
        this.fb=fb;
        this.time=time;
        this.date=date;
        this.done=done;
        this.rate=rate;
        this.cName=cName;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb=fb;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName=cName;
    }

    public Boolean getDone() {return done;}

    public void setDone(Boolean done) {this.done=done;}

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time=time;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date=date;}

    public int getRate() {return rate;}

    public void setRate(int rate) {this.rate=rate;}
}
