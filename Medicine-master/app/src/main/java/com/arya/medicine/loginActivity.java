package com.arya.medicine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
     private Button buttonsignin;
     private EditText edittextEmail;
     private EditText edittextPass;
     private TextView textviewSignup;
     private ProgressDialog progressDialog;
     private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        edittextEmail =(EditText)findViewById(R.id.etemail);

        edittextPass =(EditText)findViewById(R.id.etpass);
        buttonsignin=(Button)findViewById(R.id.buttonSignin);

        textviewSignup =(TextView)findViewById(R.id.textviewsignup);
        buttonsignin.setOnClickListener(this);
        textviewSignup.setOnClickListener(this);
        progressDialog= new ProgressDialog(this);


        //FirebaseApp.initializeApp(this);
        firebaseAuth =FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),photo.class));
        }
    }
 private void userLogin(){
        String email=edittextEmail.getText().toString().trim();

     String pass=edittextPass.getText().toString().trim();
     if(TextUtils.isEmpty(email)){
         Toast.makeText(this,"please enter email", Toast.LENGTH_SHORT).show();
         return;
     }
     if(TextUtils.isEmpty(pass)){
         Toast.makeText(this,"please enter passsword", Toast.LENGTH_SHORT).show();
         return;
     }
     progressDialog.setMessage("login....");
     progressDialog.show();
     firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             progressDialog.dismiss();
             if(task.isSuccessful()){
                 // start the profile activity
                 finish();
                 startActivity(new Intent(getApplicationContext(), photo.class));

             }
         }
     });
 }
    @Override
    public void onClick(View v) {
        if(v ==buttonsignin){
            userLogin();

        }
        if(v== textviewSignup)
        {finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
