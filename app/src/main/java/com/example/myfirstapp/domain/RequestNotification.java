package com.example.myfirstapp.domain;

import java.util.UUID;

public class RequestNotification {
    private String requestingEmail;
    private String notifiedEmail;
    private String goodID;
    private String id;
    private String goodTitle;
    private String location;

    public RequestNotification() {}

    public RequestNotification(String requestingEmail, String notifiedEmail, String goodID, String goodTitle, String location){
        this.requestingEmail = requestingEmail;
        this.notifiedEmail = notifiedEmail;
        this.goodID = goodID;
        this.goodTitle = goodTitle;
        this.location = location;
        this.id = UUID.randomUUID().toString();
    }

    public String getId(){ return id; }

    public String getGoodTitle(){ return goodTitle; }

    public String getLocation(){ return location; }

    public String getRequestingEmail(){ return requestingEmail; }

    public String getNotifiedEmail(){ return notifiedEmail; }

    public String getGoodID(){ return goodID; }
}
