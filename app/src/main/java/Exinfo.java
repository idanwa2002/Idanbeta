public class Exinfo {
    private String info, tName,url;

    public Exinfo (){}
    public Exinfo(String tName, String url, String info) {
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
