package com.example.trainsystem.model;

import java.util.ArrayList;

public class Tripmodel {
    private String passangerName, fromPlace, toPlace, dateAndTime, price, stauts,tripID, userID;
    private static ArrayList<Tripmodel> TribLlist = new ArrayList<>();




    public static ArrayList<Tripmodel> getTribLlist() {
        return TribLlist;
    }

    public static void setTribLlist(ArrayList<Tripmodel> tribLlist) {
        TribLlist = tribLlist;
    }

    public Tripmodel() {
    }

    public Tripmodel(String passangerName, String fromPlace, String toPlace, String dateAndTime, String price, String stauts, String tripID, String userID) {
        this.passangerName = passangerName;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.dateAndTime = dateAndTime;
        this.price = price;
        this.stauts = stauts;
        this.tripID = tripID;
        this.userID = userID;
    }

    public String getPassangerName() {
        return passangerName;
    }

    public void setPassangerName(String passangerName) {
        this.passangerName = passangerName;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
