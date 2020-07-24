package com.example.trainsystem.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainsystem.login.LoginImp;
import com.example.trainsystem.login.LoginView;
import com.example.trainsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements LoginView{

    com.example.trainsystem.customfonts.Button_sfuitext_regular btnLogin;
    TextView tvRegister;
    com.example.trainsystem.customfonts.EditText_SFUI_Regular edtEmail, edtPassword;
    private LoginImp loginImp;
    private LoginView loginView;
    ProgressDialog mProgressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "LoginActivity";
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // XML
        edtEmail = findViewById(R.id.EmailAddress);
        edtPassword = findViewById(R.id.Passwords);
        btnLogin = findViewById(R.id.LoginBtn);
        loginImp = new LoginImp(this);
        mProgressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog.setMessage("Please wait, Logging in..");

        HashMap<String, String> update = new HashMap<>();
        update.put("Name", "Ahmed");

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("UserData").child(FirebaseAuth.getInstance().getUid())
                .setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: true");
                }else {
                    Log.d(TAG, "onComplete: false");
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();

            }
        });



    }

    private void initLogin(String email, String password, String FullNaame) {
        mProgressDialog.show();

        loginImp.performFirebaseLogin(LoginActivity.this, email, password,FullNaame);
    }

    private void checkLoginDetails() {
        if(!TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString())){

            initLogin(edtEmail.getText().toString(), edtPassword.getText().toString(),"Ahmed Ali");
        }else{
            if(TextUtils.isEmpty(edtEmail.getText().toString())){
               // Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                edtEmail.setError("Please enter a valid email");
            }if(TextUtils.isEmpty(edtPassword.getText().toString())){
                edtPassword.setError("Please enter password");
            }
        }
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
        if (firebaseAuth.getCurrentUser().isEmailVerified()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Successfully Logged in" , Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error" , Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onLoginFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

    public void SignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ((FirebaseAuth.getInstance().getUid() != null) && (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())  ){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
