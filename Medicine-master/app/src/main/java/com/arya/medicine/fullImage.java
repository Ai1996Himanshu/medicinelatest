package com.arya.medicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class fullImage extends AppCompatActivity {

    ImageView fullImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        fullImageView= findViewById(R.id.imageFullView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        Glide.with(this).load(url).into(fullImageView);
    }
}
