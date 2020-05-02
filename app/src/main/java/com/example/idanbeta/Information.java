package com.example.idanbeta;
/**
 * @author		Idan Wasserblat <uxrcky@gmail.com>
 * @version	1.6
 * class information that saves info about exercises using tname,url,info and type
 */
class Information {
    private String info, tName,url,type;

    public Information (){}
    public Information(String tName, String url, String info,String type) {
        this.tName=tName;
        this.url=url;
        this.info=info;
        this.type=type;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info=info;
    }

    public String gettName() {return tName;}

    public void settName(String tName) {this.tName=tName;}

    public String getType() {return type;}

    public void setType(String type) {this.type=type;}

}
