package com.example.myfirstapp.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

/**
 * Good - Represents a good of a user
 *
 */
public class Good {

    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String used_duration;
    private String current_state;
    private String exchange_location;
    private String user_email;
    private String image_url;
    private String type;

    /**
     * Creates a good
     *
     * @param title - title of the good
     * //@param availability_date - the date of which the good is available for exchange
     */
    public Good(String title, String startDate, String endDate, String description, String exchange_location, String user_email, String image_url, String type){
        this.title = title;
        this.description = description;
        this.exchange_location = exchange_location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user_email = user_email;
        this.image_url = image_url;
        this.type = type;
    }

    /**
     * Getters
     */
    public String getTitle(){ return title; }

    public String getDescription(){ return description; }

    public String getUsed_duration(){ return used_duration; }

    public String getUser_email(){ return user_email; }

    public String getType(){return type;}

    public String getAvailability_end_date(){ return endDate; }

    /**
     * the current state of a good could: [new, moderate, old]
     */
    public String getCurrent_state(){ return current_state; }

    public String getExchange_location(){ return exchange_location; }

    public String getImage_url(){ return image_url; }

    /**
     * Setters
     */
    public void setTitle(String title){ this.title = title; }

    public void setDescription(String description){ this.description = description; }

    public void setAvailability_end_date(String availability_end_date){
        this.endDate = availability_end_date;
    }

    public void setUsed_duration(String used_duration){ this.used_duration = used_duration; }

    public void setCurrent_state(String current_state){ this.current_state = current_state; }

    public void setUser_email(String user_email){ this.user_email = user_email; }

    public void setExchange_location(String exchange_location){
        this.exchange_location = exchange_location;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isExpired(){
        LocalDate today = LocalDate.now();
        LocalDate enddate = LocalDate.of(Integer.parseInt(endDate.substring(0,4)),Integer.parseInt(endDate.substring(5,7)),Integer.parseInt(endDate.substring(8)));
        if(today.isAfter(enddate)){
            return true;
        }
        return false;
    }
}