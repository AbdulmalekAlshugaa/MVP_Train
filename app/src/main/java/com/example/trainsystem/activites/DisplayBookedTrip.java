package com.example.trainsystem.activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.trainsystem.R;
import com.example.trainsystem.Utilts.PdfDocumentAdapter;
import com.example.trainsystem.mainActivity.ListImp;
import com.example.trainsystem.mainActivity.ViewList;
import com.example.trainsystem.model.Tripmodel;
import com.google.android.gms.common.logging.Logger;
import com.google.firebase.auth.FirebaseAuth;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.File;
import java.lang.reflect.Method;


public class DisplayBookedTrip extends AppCompatActivity implements ViewList {
   private ListImp listImp;
   private TextView FromPlace, ToPlace, Stauts, DateTime,Email,Price;
    private static final String TAG = "DisplayBookedTrip";
    private Toolbar mToolBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_booked_trip);
        //
        FromPlace = findViewById(R.id.FromPlaceDispaly);
        ToPlace = findViewById(R.id.ToPlaceDispaly);
        Stauts = findViewById(R.id.StautsDetials);
        DateTime = findViewById(R.id.BookingID);
        Email = findViewById(R.id.NameOfTheCustomer);
        Price = findViewById(R.id.priceTrip);
        // Toolbar
        mToolBar = (Toolbar) findViewById(R.id.bill_info_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Booking Details");

        Email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        FromPlace.setText(getIntent().getExtras().get("From").toString());
        ToPlace.setText(getIntent().getExtras().get("To").toString());
        Stauts.setText(getIntent().getExtras().get("Status").toString());
        DateTime.setText(getIntent().getExtras().get("Date").toString());
        Price.setText(getIntent().getExtras().get("Price").toString());


        listImp = new ListImp(this);
        listImp.ListTrips();

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Print(View view) {
        printPDF();




    }


    private void printPDF() {
        AbstractViewRenderer page =
                new AbstractViewRenderer(DisplayBookedTrip.this, R.layout.activity_display_booked_trip) {
            private String _text;
            private String id, stauts, from,to,price;



            @Override
            protected void initView(View view) {
                _text = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                TextView tv_hello =view.findViewById(R.id.NameOfTheCustomer);
                FromPlace = view.findViewById(R.id.FromPlaceDispaly);
                ToPlace = view.findViewById(R.id.ToPlaceDispaly);
                Stauts = view.findViewById(R.id.StautsDetials);
                DateTime = view.findViewById(R.id.BookingID);
                Price = view.findViewById(R.id.priceTrip);
                FromPlace.setText(getIntent().getExtras().get("From").toString());
                ToPlace.setText(getIntent().getExtras().get("To").toString());
                Stauts.setText(getIntent().getExtras().get("Status").toString());
                DateTime.setText(getIntent().getExtras().get("Date").toString());
                Price.setText(getIntent().getExtras().get("Price").toString());
                tv_hello.setText(_text);
            }
        };

// you can reuse the bitmap if you want
        page.setReuseBitmap(true);

        new PdfDocument.Builder(DisplayBookedTrip.this).
                addPage(page).filename("TripPDF").orientation(PdfDocument.A4_MODE.LANDSCAPE).
                renderWidth(2115).renderHeight(1500)
                .listener(new PdfDocument.Callback() {
                    @Override
                    public void onComplete(File file) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                        Log.d(TAG, "onComplete: "+file.getName());
                        Log.d(TAG, "onComplete: "+file.getParent());
                        Log.d(TAG, "onComplete: "+file.getPath());
                        Log.d(TAG, "onComplete: "+file.toURI());
                        File files = new File(file.getPath());
                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                                if (file.exists()) {
                                    Uri path = Uri.fromFile(files);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(path, "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    try {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e) {
                                        Toast.makeText(DisplayBookedTrip.this, "No Application Available to View PDF", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                                if (file.exists()) {
                                    Uri path = Uri.fromFile(files);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(path, "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    try {
                                        startActivity(intent);
                                    }
                                    catch (ActivityNotFoundException e) {
                                        Toast.makeText(DisplayBookedTrip.this, "No Application Available to View PDF", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }



                    }

                    @Override
                    public void onError(Exception e) {
                        Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
                        Log.d(TAG, "onError: "+e.getMessage().toString());
                    }
                }).create().createPdf(this);

    }






    @Override
    public void onMainSuccessList(Tripmodel tripmodel) {
        Log.d(TAG, "onMainSuccessList: "+tripmodel.getDateAndTime());

    }

    @Override
    public void onMainFailureList(String message) {
        Log.d(TAG, "onMainFailureList: "+message);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DisplayBookedTrip.this, Settings.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }









}
