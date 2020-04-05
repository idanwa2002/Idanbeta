package com.example.idanbeta;

class Information {
    private String info, tName,url;

    public Information (){}
    public Information(String tName, String url, String info) {
        this.tName=tName;
        this.url=url;
        this.info=info;
        //this.fb=fb;

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

}
