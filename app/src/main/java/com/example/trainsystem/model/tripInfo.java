package com.example.trainsystem.model;

public class tripInfo {
    private String DateAndTime;
    private String Stauts, fromPlace,toPlace;
    private String TripID;



    public tripInfo() {
    }

    public tripInfo(String dateAndTime, String stauts, String fromPlace, String toPlace, String tripID) {
        DateAndTime = dateAndTime;
        Stauts = stauts;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        TripID = tripID;
    }

    public String getTripID() {
        return TripID;
    }

    public void setTripID(String tripID) {
        TripID = tripID;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public String getStauts() {
        return Stauts;
    }

    public void setStauts(String stauts) {
        Stauts = stauts;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }
}
