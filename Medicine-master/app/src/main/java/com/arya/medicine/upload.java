package com.arya.medicine;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class upload extends AppCompatActivity {

    EditText userName, medInfo, dom, doe, batchNo, noOfMedicine,contact;
    Button upload;
    ImageView billIv, medicineIv;
    String user, detail, manDate, expDate, bNo, quantity,phone;
    String billUrl, medicineUrl;
    private static final int CAMERA_REQUEST_BILL = 1888;
    private static final int CAMERA_REQUEST_MEDICINE = 1889;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dr;
    StorageReference mStorageReference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);
        firebaseAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference("med_details");
        userName = findViewById(R.id.userName);
        medInfo = findViewById(R.id.medInfo);
        dom = findViewById(R.id.dom);
        doe = findViewById(R.id.doe);
        batchNo = findViewById(R.id.batchNo);
        noOfMedicine = findViewById(R.id.noOfMedicine);
        upload = findViewById(R.id.upload);
        billIv = findViewById(R.id.billIv);
        medicineIv = findViewById(R.id.medicineIv);
        contact=findViewById(R.id.contact);

        pd = new ProgressDialog(upload.this);
        pd.setMessage("loading");

        mStorageReference = FirebaseStorage.getInstance().getReference();


        billIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_BILL);
                }
            }
        });

        medicineIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_MEDICINE);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == upload) {
                    upload_data();
                    Toast.makeText(upload.this,"Uploaded successfully", Toast.LENGTH_SHORT).show();

                    cleanup();

                }
            }
        });
    }

    public void cleanup(){
        userName.setText("");
        medInfo.setText("");
        dom.setText("");
        doe.setText("");
        batchNo.setText("");
        noOfMedicine.setText("");
        billIv.setImageDrawable(null);
        medicineIv.setImageDrawable(null);
        contact.setText("");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_BILL);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_BILL && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            billIv.setImageBitmap(photo);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataArray = baos.toByteArray();

            final StorageReference mountainsRef = mStorageReference.child(getAlphaNumericString());
            final UploadTask uploadTask = mountainsRef.putBytes(dataArray);
            pd.show();
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        pd.cancel();
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        billUrl = downloadUri.toString();
                        Log.d("nuclode", billUrl);
                        pd.cancel();
                    } else {
                        pd.cancel();
                    }
                }
            });
        } else if (requestCode == CAMERA_REQUEST_MEDICINE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            medicineIv.setImageBitmap(photo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataArray = baos.toByteArray();

            final StorageReference mountainsRef = mStorageReference.child(getAlphaNumericString());
            final UploadTask uploadTask = mountainsRef.putBytes(dataArray);
            pd.show();
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        pd.cancel();
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        medicineUrl = downloadUri.toString();
                        Log.d("nuclode", medicineUrl);
                        pd.cancel();
                    } else {
                        pd.cancel();
                    }
                }
            });
        }
    }

    static String getAlphaNumericString() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void upload_data() {
        user = userName.getText().toString();
        phone=contact.getText().toString();
        detail = medInfo.getText().toString();
        manDate = dom.getText().toString();
        expDate = doe.getText().toString();
        bNo = batchNo.getText().toString();
        quantity = noOfMedicine.getText().toString();
        med_detail medicine_details = new med_detail(user, detail,phone, manDate, expDate, bNo, quantity, medicineUrl, billUrl);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        dr.child(user.getUid()).setValue(medicine_details);

    }
}
