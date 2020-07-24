package com.example.trainsystem.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trainsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Feedback extends AppCompatActivity {
    SweetAlertDialog pDialog ;
    private EditText feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback = findViewById(R.id.reviewED);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Feedback.this, Settings.class);
        startActivity(intent);
        finish();
    }

    public void PushFeedback(View view) {
        pDialog.show();
        HashMap<String, String> FeedBack = new HashMap<>();
        FeedBack.put("FeedMsg", feedback.getText().toString());
        FeedBack.put("UserID",FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseFirestore.getInstance().collection("FEEDBACK")
                .document()
                .set(FeedBack)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pDialog.dismiss();
                            Toast.makeText(Feedback.this, "Uploaded ",Toast.LENGTH_LONG).show();
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(Feedback.this, "Error ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void Cancel(View view) {
        feedback.setText("");
        Intent intent = new Intent(Feedback.this, Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
