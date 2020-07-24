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

import java.util.ArrayList;

public class ListImp implements mainPresenter , ViewList {
    private ViewList viewList;
    private static final String TAG = "ListImp";

    public ListImp(ViewList viewList) {
        this.viewList = viewList;
    }

    @Override
    public void onMainSuccessList(Tripmodel tripmodel) {
        viewList.onMainSuccessList(tripmodel);


    }

    @Override
    public void onMainFailureList(String message) {
        viewList.onMainFailureList(message);

    }

    @Override
    public void POstTrip(String passangerName, String fromPlace, String toPlace, String dateAndTime, String price, String stauts, String tripID, String userID) {

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
                            String dateAndTime = snapshots.get(i).getString("dateAndTime");
                            String fromPlace = snapshots.get(i).getString("fromPlace");
                            String passangerName = snapshots.get(i).getString("passangerName");
                            String price = snapshots.get(i).getString("price");
                            String stauts = snapshots.get(i).getString("stauts");
                            String toPlace = snapshots.get(i).getString("toPlace");
                            String tripID = snapshots.get(i).getString("tripID");
                            String userID = snapshots.get(i).getString("userID");

                            // set a;; the need request
                            Tripmodel tripmodel = new Tripmodel();
                            tripmodel.setTripID(id);
                            tripmodel.setDateAndTime(dateAndTime);
                            tripmodel.setFromPlace(fromPlace);
                            tripmodel.setPassangerName(passangerName);
                            tripmodel.setPrice(price);
                            tripmodel.setStauts(stauts);
                            tripmodel.setToPlace(toPlace);
                            tripmodel.setTripID(tripID);
                            tripmodel.setUserID(userID);
                            Tripmodel.getTribLlist().add(tripmodel);
                            viewList.onMainSuccessList(tripmodel);






                        }


                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                viewList.onMainFailureList(e.getMessage().toString());


            }
        });


    }
}
