package com.example.myfirstapp.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.Integer;
import java.time.LocalDate;
/**
 * Good - Represents a good of a user
 *
 */
public class Good {

    private String title;
    private String description;
    private LocalDate date;
    private String availability_end_date;
    private String used_duration;
    private String current_state;
    private String exchange_location;
    private String user_email;
    private boolean expired;

    /**
     * Creates a good
     *
     * @param title - title of the good
     * //@param availability_date - the date of which the good is available for exchange
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Good(String title, String date, String description, String exchange_location, String user_email){
        this.title = title;
        this.date = LocalDate.now();
        this.description = description;
        this.exchange_location = exchange_location;
        this.availability_end_date = date;
        this.user_email = user_email;
        expired = false;
    }

    /**
     * Getters
     */
    public String getTitle(){ return title; }

    public String getDescription(){ return description; }

    public String getAvailability_date(){ return availability_end_date; }

    public String getUsed_duration(){ return used_duration; }

    public String getUser_email(){ return user_email; }

    /**
     * the current state of a good could: [new, moderate, old]
     */
    public String getCurrent_state(){ return current_state; }

    public String getExchange_location(){ return exchange_location; }

    /**
     * Setters
     */
    public void setTitle(String title){ this.title = title; }

    public void setDescription(String description){ this.description = description; }

    public void setAvailability_date(String availability_date){
        this.availability_end_date = availability_date;
    }

    public void setUsed_duration(String used_duration){ this.used_duration = used_duration; }

    public void setCurrent_state(String current_state){ this.current_state = current_state; }

    public void setUser_email(String user_email){ this.user_email = user_email; }

    public void setExchange_location(String exchange_location){
        this.exchange_location = exchange_location;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate isExpired(){
        LocalDate today = LocalDate.now();
        LocalDate enddate = LocalDate.of(Integer.parseInt(availability_end_date.substring(0,4)),Integer.parseInt(availability_end_date.substring(5,7)),Integer.parseInt(availability_end_date.substring(8)));
        return enddate;
    }
}