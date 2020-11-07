package com.example.myfirstapp.domain;

/**
 * Good - Represents a good of a user
 *
 */
public class Good {

    private String title;
    private String description;
    private String date;
    private String availability_date;
    private String used_duration;
    private String current_state;
    private String exchange_location;
    private String user_email;
    private String image_url;

    /**
     * Creates a good
     *
     * @param title - title of the good
     * //@param availability_date - the date of which the good is available for exchange
     */
    public Good(String title, String date, String description, String exchange_location, String user_email, String image_url){
        this.title = title;
        this.date = date;
        this.description = description;
        this.exchange_location = exchange_location;
        this.availability_date = availability_date;
        this.user_email = user_email;
        this.image_url = image_url;
    }

    /**
     * Getters
     */
    public String getTitle(){ return title; }

    public String getDescription(){ return description; }

    public String getAvailability_date(){ return availability_date; }

    public String getUsed_duration(){ return used_duration; }

    public String getUser_email(){ return user_email; }

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

    public void setAvailability_date(String availability_date){
        this.availability_date = availability_date;
    }

    public void setUsed_duration(String used_duration){ this.used_duration = used_duration; }

    public void setCurrent_state(String current_state){ this.current_state = current_state; }

    public void setUser_email(String user_email){ this.user_email = user_email; }

    public void setExchange_location(String exchange_location){
        this.exchange_location = exchange_location;
    }

    public void setImage_url(String image_url){ this.image_url = image_url; }
}