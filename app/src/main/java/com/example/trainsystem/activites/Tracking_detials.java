package com.example.trainsystem.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.trainsystem.Adapter.ListTrip;
import com.example.trainsystem.R;
import com.example.trainsystem.mainActivity.ListImp;
import com.example.trainsystem.mainActivity.ViewList;
import com.example.trainsystem.model.Tripmodel;
import com.example.trainsystem.model.tripInfo;

import java.util.ArrayList;

public class Tracking_detials extends AppCompatActivity implements ViewList {

    private ArrayList<Tripmodel> mTrackingTrip = new ArrayList<>(); // mNote
    private ListTrip mListCurrentDateAdapter; // Adapter
    private RecyclerView mListDataView;
    private Toolbar mToolBar;
    private ListImp listImp;
    private static final String TAG = "Tracking_detials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_detials);
        //
        mListDataView = (RecyclerView)findViewById(R.id.ListTrackingList);
        mToolBar = (Toolbar) findViewById(R.id.TracToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Trip Info");
        listImp = new ListImp(this);
        listImp.ListTrips();

        //
        RecyclerViewMethods();
    }

    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mListDataView.setLayoutManager(manager);
        mListDataView.setHasFixedSize(true);
        mListCurrentDateAdapter = new ListTrip(this, mTrackingTrip);
        mListDataView.setAdapter(mListCurrentDateAdapter);
        mListDataView.invalidate();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onMainSuccessList(Tripmodel tripmodel) {
            Tripmodel t = new Tripmodel();
            t.setStauts(tripmodel.getStauts());
            t.setDateAndTime(tripmodel.getDateAndTime());
            t.setFromPlace(tripmodel.getFromPlace());
            t.setToPlace(tripmodel.getToPlace());
            t.setPrice(tripmodel.getPrice());
            mTrackingTrip.add(t);

        mListCurrentDateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMainFailureList(String message) {
        Log.d(TAG, "onMainFailureList: ");
        Toast.makeText(Tracking_detials.this, message, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Tracking_detials.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
