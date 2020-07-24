package com.example.trainsystem.EmailVerfication;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerifactionIMP implements OnEmailVerficationView, Verfication {
    private OnEmailVerficationView onEmailVerficationView;

    public EmailVerifactionIMP(OnEmailVerficationView onEmailVerficationView) {
        this.onEmailVerficationView = onEmailVerficationView;
    }

    @Override
    public void OnSucess(String SuccessMesagee) {
        onEmailVerficationView.OnSucess(SuccessMesagee);

    }

    @Override
    public void OnError(String SuccessMesagee) {
        onEmailVerficationView.OnError(SuccessMesagee);

    }

    @Override
    public void OnSucessEmailVerfication(Activity activity) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    onEmailVerficationView.OnSucess("Email verification has send to your email address");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onEmailVerficationView.OnSucess("Error");
                }
            });
        }


    }
}
