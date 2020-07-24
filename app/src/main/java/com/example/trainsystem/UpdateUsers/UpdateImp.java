package com.example.trainsystem.UpdateUsers;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.trainsystem.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class UpdateImp implements UpdatePresenter, UpdateView {

    private UpdateView updateView;
    private static final String TAG = "UpdateImp";

    public UpdateImp(UpdateView updateView) {
        this.updateView = updateView;
    }

    @Override
    public void onUpdateUpuccess(String message) {
        updateView.onUpdateUpuccess(message);

    }

    @Override
    public void onUpdateFailure(String message) {
        updateView.onUpdateFailure(message);

    }

    @Override
    public void onSuccessUpdate(Activity activity, String UserName, String userId, String DeviceToekn, String personalImage, String TimeStampl) {
        Users users = new Users(UserName, userId, DeviceToekn, personalImage, TimeStampl );
        FirebaseFirestore.getInstance().collection("UserData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(users, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            updateView.onUpdateUpuccess(task.getResult().toString());

                        }else {
                            updateView.onUpdateFailure(task.getResult().toString());


                        }
                    }
                });
    }
}
