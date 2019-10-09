package com.arya.medicine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class full_details extends AppCompatActivity {

    ImageView medImage, billImage;
    TextView medName, quantity, dom, doe, batch_no, contact;
    ImageButton contact1;
    static med_detail med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_details);

        med = (med_detail) getIntent().getSerializableExtra("obj");
        medImage = findViewById(R.id.med_image);
        billImage = findViewById(R.id.bill_image);
        medName = findViewById(R.id.med_name);
        quantity = findViewById(R.id.quantity);
        dom = findViewById(R.id.dom);
        doe = findViewById(R.id.doe);
        batch_no = findViewById(R.id.batch_no);
        contact = findViewById(R.id.contact);
        contact1 = findViewById(R.id.contact1);

        Glide.with(this).load(med.getMedUrl()).into(medImage);
        Glide.with(this).load(med.getBillUrl()).into(billImage);
        medName.setText(med.getDetail());
        quantity.setText(med.getQuantity());
        dom.setText(med.getManData());
        doe.setText(med.getExpData());
        batch_no.setText(med.getbNo());
        contact.setText(med.getPhone());

        medImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(full_details.this, fullImage.class);
                intent.putExtra("url", med.getMedUrl());
                Log.d("url", med.getMedUrl());
                startActivity(intent);
            }
        });

        billImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(full_details.this, fullImage.class);
                intent.putExtra("url", med.getBillUrl());
                Log.d("url", med.getMedUrl());
                startActivity(intent);
            }
        });

        contact1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String number = contact.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(full_details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }

        });

    }
}
