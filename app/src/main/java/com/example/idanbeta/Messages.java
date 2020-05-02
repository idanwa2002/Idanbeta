package com.example.idanbeta;
/**
 * @author		Idan Wasserblat <uxrcky@gmail.com>
 * @version	1.6
 * class exercises that saves info about exercises for the users using pname, cname, time, date, advice, tname, active
 */
class Messages {
    private String trainer,client,msg;

    public Messages (){}
    public Messages(String trainer, String client, String msg) {
        this.trainer=trainer;
        this.client=client;
        this.msg=msg;

    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer=trainer;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client=client;
    }

    public String getMsg() {return msg;}

    public void setMsg(String msg) {this.msg=msg;}
}
