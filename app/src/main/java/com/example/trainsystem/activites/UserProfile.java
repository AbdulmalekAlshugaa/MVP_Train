package com.example.trainsystem.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trainsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserProfile extends AppCompatActivity {

    private ImageView carImage;
    private Uri uri_image;
    private Button UplaodData;
    SweetAlertDialog pDialog ;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStorege;
    private StorageReference mStorageRef;
    private Button PushData;
    private static final String TAG = "UserProfile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        mStorege = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carImage = findViewById(R.id.UserImage);
        PushData = findViewById(R.id.UserProfile);
        PushData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatUaer();
            }
        });

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UserProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(UserProfile.this, "Permission denied", Toast.LENGTH_SHORT).show();
                } else
                {
                    PickerImage();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Logout");
        builder.setMessage("Are sure you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserProfile.this, SignUp.class);
                startActivity(intent);
                finish();


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                //
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void PickerImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(UserProfile.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri_image = result.getUri();
                Picasso.with(this).load(uri_image).into(carImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                System.out.println(error.getMessage().toString());
            }
        }
    }
    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserProfile.this, SignUp.class);
        startActivity(intent);
        finish(); //Purtchase
    }



    public void UpdatUaer(){
        pDialog.show();

        if (uri_image == null){
            Toast.makeText(UserProfile.this, "Please Select profile image",Toast.LENGTH_SHORT).show();
        }else {
            final StorageReference Image_path = mStorageRef.child("UserData").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + FileExtention(uri_image));
            Image_path.putFile(uri_image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String DownloadUri = uri.toString();
                                    HashMap UserImage = new HashMap();
                                    UserImage.put("personalImage", DownloadUri);

                                    mStorege.collection("UserData").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .update(UserImage).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(UserProfile.this, "Image has uploaded", Toast.LENGTH_SHORT).show();
                                            pDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: "+e.getMessage());
                                            pDialog.dismiss();
                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.getMessage());
                                    pDialog.dismiss();

                                }
                            });


                        }
                    });


        }



    }

    public String FileExtention (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }


}
