package com.example.trainsystem.mainActivity;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.trainsystem.model.Tripmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainImp implements mainPresenter, MainView{
    private static final String TAG = "MainImp";
    private MainView mainView;

    public MainImp(MainView mainView) {
        this.mainView = mainView;
    }





    @Override
    public void onMainSuccess(String message) {
        mainView.onMainSuccess(message);

    }

    @Override
    public void onMainFailure(String message) {
        mainView.onMainFailure(message);

    }


    @Override
    public void POstTrip(String passangerName,
                         String fromPlace,
                         String toPlace,
                         String dateAndTime,
                         String price,
                         String stauts,
                         String tripID,
                         String userID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Tripmodel tripmodel = new Tripmodel(passangerName, fromPlace, toPlace, dateAndTime, price, stauts, tripID, userID);
        FirebaseFirestore.getInstance().collection("TripData")
                .document(currentDateandTime)
                .set(tripmodel, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mainView.onMainSuccess("Trip has been booked");

                        }else {
                            mainView.onMainFailure("Something went wrong");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
   ;

            }
        });


    }

    @Override
    public void ListTrips() {

        FirebaseFirestore.getInstance().collection("TripData")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<DocumentSnapshot> snapshots = (ArrayList<DocumentSnapshot>) task.getResult().getDocuments();
                    for (int i=0; i<snapshots.size(); i++){
                        if (snapshots.get(i).get("userID").equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            String id = snapshots.get(i).getId();

                            Log.d(TAG, "onComplete: Stauts"+snapshots.get(i).get("stauts"));
                            Log.d(TAG, "onComplete: TRIPS "+id);
                        }


                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getMessage());

            }
        });

    }


}

