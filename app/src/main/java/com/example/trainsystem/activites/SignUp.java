package com.example.trainsystem.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.trainsystem.EmailVerfication.EmailVerifactionIMP;
import com.example.trainsystem.EmailVerfication.OnEmailVerficationView;
import com.example.trainsystem.R;
import com.example.trainsystem.registration.SginUpImp;
import com.example.trainsystem.registration.SginUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.iid.FirebaseInstanceId;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements SginUpView , OnEmailVerficationView {
    private com.example.trainsystem.customfonts.EditText_SFUI_Regular UserName, emailAddrees, Password;
    private com.example.trainsystem.customfonts.Button_Roboto_Medium SginUp;
    private SginUpImp sginUpImp;
    ProgressDialog mProgressDialog;
    private static final String TAG = "SignUp";
    private FirebaseFirestore firestore ;
    private EmailVerifactionIMP emailVerifactionIMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        UserName = findViewById(R.id.UserName);
        emailAddrees = findViewById(R.id.EmailAddressSigup);
        Password = findViewById(R.id.PasswordCreate);
        SginUp = findViewById(R.id.SignUp);
        sginUpImp = new SginUpImp(this);
        emailVerifactionIMP = new EmailVerifactionIMP(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait, creating an account ..");
        firestore = FirebaseFirestore.getInstance();

        SginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignUpDetails();
            }
        });
    }


    private void initSignUp( String email, String password) {
        mProgressDialog.show();

        sginUpImp.CreatNewAccount(SignUp.this,  email,password);
    }


    private void checkSignUpDetails() {
        if(!TextUtils.isEmpty(emailAddrees.getText().toString()) && !TextUtils.isEmpty(Password.getText().toString())){
            initSignUp(emailAddrees.getText().toString(),Password.getText().toString());
        }else{
            if(TextUtils.isEmpty(UserName.getText().toString())){
                // Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                UserName.setError("User name is required");
                return;
            }if(TextUtils.isEmpty(emailAddrees.getText().toString())){
                emailAddrees.setError("Please enter a valid email");
                return;
            }if (TextUtils.isEmpty(Password.getText().toString())){
                Password.setError("Please enter password");

            }
        }
    }

//    private void SendEmailVerfication (String email){
//        sginUpImp.SendEmailVerfication(SignUp.this, email);
//
//    }

    public void login(View view) {
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void SendEmailVerfication (){
        emailVerifactionIMP.OnSucessEmailVerfication(SignUp.this);
    }


    @Override
    public void onSignUpuccess(String message) {
        mProgressDialog.dismiss();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // send the request here , it will work much betther
//        SendEmailVerfication(emailAddrees.getText().toString());

        String DeeviceToken = FirebaseInstanceId.getInstance().getToken();
        sginUpImp.pushUserData(SignUp.this, UserName.getText().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                DeeviceToken,"URL",timestamp.toString());
        FirebaseFirestore.getInstance().collection("UserData")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String EmailAddress = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        Log.d(TAG, "onComplete: "+EmailAddress);
                        SendEmailVerfication();
                    }
                });




    }

    @Override
    public void onSignFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(SignUp.this, message, Toast.LENGTH_LONG).show();


    }

    public void PushDatatest(View view) {
        Log.d(TAG, "PushDatatest: ");


    }

    @Override
    public void OnSucess(String SuccessMesagee) {
        mProgressDialog.dismiss();
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void OnError(String SuccessMesagee) {

    }
}
