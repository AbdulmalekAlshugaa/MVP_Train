package com.example.trainsystem.mainActivity;


public interface mainPresenter {

    void POstTrip(String passangerName, String fromPlace, String toPlace, String dateAndTime, String price, String stauts, String tripID, String userID);
    void ListTrips();
}
