package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.example.myfirstapp.domain.User;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class RequestNotificationUnitTest{
    @Test
    public void generateRequestNotificationObject(){
        User requesting = new User("Steve", "Johnson", "steve@google.com", "Password123");
        User notified = new User("Laura", "White", "laura@yahoo.com", "Password123");
        Good desired = new Good("antique doll", LocalDate.now().toString(),"2021-05-11","have been taken care of for years",
                "location","laura@yahoo.com","url", "type");

        RequestNotification reqNot = new RequestNotification(requesting.getEmail(), notified.getEmail(), desired.getId());
        assertEquals(reqNot.getRequestingEmail(), requesting.getEmail());
        assertEquals(reqNot.getNotifiedEmail(), notified.getEmail());
        assertEquals(reqNot.getGoodID(), desired.getId());
    }
}