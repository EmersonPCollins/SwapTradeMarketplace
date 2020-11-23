package com.example.myfirstapp.domain;

public class RequestNotification {
    private String requestingEmail;
    private String notifiedEmail;
    private String goodID;

    public RequestNotification(String requestingEmail, String notifiedEmail, String goodID ){
        this.requestingEmail = requestingEmail;
        this.notifiedEmail = notifiedEmail;
        this.goodID = goodID;
    }

    public String getRequestingEmail(){ return requestingEmail; }

    public String getNotifiedEmail(){ return notifiedEmail; }

    public String getGoodID(){ return goodID; }
}
