package com.example.trainsystem.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trainsystem.R;

public class ViewPAge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
    }


    //

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewPAge.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
