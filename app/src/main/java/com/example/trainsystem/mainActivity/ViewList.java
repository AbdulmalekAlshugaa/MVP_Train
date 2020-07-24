package com.example.trainsystem.mainActivity;

import com.example.trainsystem.model.Tripmodel;

public interface ViewList {
    void onMainSuccessList(Tripmodel tripmodel);
    void onMainFailureList (String message);
}
