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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
 private Button buttonRegister;
 private EditText edittextEmail;
    private EditText edittextPass;
    private TextView textviewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
       // FirebaseApp.initializeApp(this);
        firebaseAuth =FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), profileActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button)findViewById(R.id.buttonreg);
        edittextEmail = (EditText)findViewById(R.id.etemail);
        edittextPass =(EditText)findViewById(R.id.etpass);
        textviewSignin =(TextView)findViewById(R.id.textviewsignin);
        buttonRegister.setOnClickListener(this);
        textviewSignin.setOnClickListener(this);
    }
private void registerUser(){
        String email= edittextEmail.getText().toString().trim();

    String password= edittextPass.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
             Toast.makeText(this,"please enter email", Toast.LENGTH_SHORT).show();
              return;
                 }
    if(TextUtils.isEmpty(password)){
        Toast.makeText(this,"please enter passsword", Toast.LENGTH_SHORT).show();
        return;
    }
    progressDialog.setMessage("register user..");
    progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(getApplicationContext(), profileActivity.class));


                    }else {
                        Toast.makeText(MainActivity.this, "Registered NOT successfully , tRY AGAIN", Toast.LENGTH_SHORT).show();
                    }progressDialog.dismiss();

                }
            })     ;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser();
        }
        if(v == textviewSignin){
            //will open activity here
            startActivity(new Intent(this, loginActivity.class));
        }
    }
}
