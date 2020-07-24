package com.example.trainsystem.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trainsystem.R;
import com.example.trainsystem.mainActivity.MainImp;
import com.example.trainsystem.mainActivity.MainView;
import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView time, price,NameOfTheUSer;


    MaterialSpinner spinner,toSpiner ;
    private MainImp mainImp;
    private static final String TAG = "MainActivity";
    ProgressDialog mProgressDialog;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (MaterialSpinner) findViewById(R.id.fromplace);
        toSpiner = findViewById(R.id.ToPlace);

        price = findViewById(R.id.PriceOfTheTrip);
        time = findViewById(R.id.TripTime);
        textView = findViewById(R.id.NameOfTheUSer);
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());



        mainImp = new MainImp(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait, Creating your Booking");

        spinner.setItems("Kl sentral  ", "Tun sambanthan ", "Maharajaela ", "Hang tuah ", "Imbi ","Raja chulan ","Bukit nanas ","Medan tunko ","Chow kit","Titiwangsa");
        toSpiner.setItems("Kl sentral  ", "Tun sambanthan ", "Maharajaela ", "Hang tuah ", "Imbi ","Raja chulan ","Bukit nanas ","Medan tunko ","Chow kit","Titiwangsa");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(MainActivity.this, item,Toast.LENGTH_LONG).show();
            }
        });
        toSpiner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                int setNumber = (int) (Math.random()*30);
             //   price.setText(setNumber);
                DateFormat df = new SimpleDateFormat("HH:mm");
                String currentTime = df.format(Calendar.getInstance().getTime());

                Log.d(TAG, "onItemSelected: "+currentTime);
                price.setText(setNumber+"");
                time.setText(currentTime);
                Toast.makeText(MainActivity.this, item,Toast.LENGTH_LONG).show();
            }
        });


    }


    public void UserProfile(View view) {
        Intent intent = new Intent(MainActivity.this, UserProfile.class);
        startActivity(intent);
        finish();


    }

    @Override
    public void onMainSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Booked");
        builder.setMessage("Are you sure you want to book a trip again?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Process to pay", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, Tracking_detials.class);
                startActivity(intent);
                finish();

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onMainFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();

    }

    public void PushUser(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        mProgressDialog.show();
        Log.d(TAG, "PushUser User Name: "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
        mainImp.POstTrip(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                spinner.getText().toString(),
                toSpiner.getText().toString(),
                currentDateandTime, price.getText().toString(),
                "PENDING",currentDateandTime,
                FirebaseAuth.getInstance().getCurrentUser().getUid());


    }

    public void ViewPage(View view) {
        Intent intent = new Intent(MainActivity.this, ViewPAge.class);
        startActivity(intent);
        finish();
    }

    public void Setting(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    public void PDF(){

    }
}
