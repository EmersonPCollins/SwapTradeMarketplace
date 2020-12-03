package com.example.myfirstapp.domain;

import java.util.UUID;

public class RequestNotification {
    private String requestingEmail;
    private String notifiedEmail;
    private String goodID;
    private String id;

    public RequestNotification(String requestingEmail, String notifiedEmail, String goodID ){
        this.requestingEmail = requestingEmail;
        this.notifiedEmail = notifiedEmail;
        this.goodID = goodID;
        this.id = UUID.randomUUID().toString();
    }

    public String getId(){ return id; }

    public String getRequestingEmail(){ return requestingEmail; }

    public String getNotifiedEmail(){ return notifiedEmail; }

    public String getGoodID(){ return goodID; }
}
