import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Exercise {
    private String pName, cName, advice, tName,url;
    private String time;
    //private Date date;
    private int days;
    //private ArrayList<Feedback> fb = new ArrayList<>();

    public Exercise (){}
    public Exercise (String pName, String cName, String time, String advice, String tName, int days, String url) {
        this.pName=pName;
        this.cName=cName;
        this.time=time;
        this.advice=advice;
        this.tName=tName;
        this.days=days;
        this.url=url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice=advice;
    }

    public String gettName() {return tName;}

    public void settName(String tName) {this.tName=tName;}

    public int getDays() {return days;}

    public void setDays(int days) {this.days=days;}


}
