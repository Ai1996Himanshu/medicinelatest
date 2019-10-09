package com.arya.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {
private FirebaseAuth firebaseAuth;
private TextView textViewUserEmail;
private Button buttonLogout;
private DatabaseReference dr;
private EditText edittextName , editextAddress , editTextCity,editTextState,editTextNumber;
private Button buttonSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
       // FirebaseApp.initializeApp(this);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }
        dr= FirebaseDatabase.getInstance().getReference("Users");
        editextAddress =(EditText)findViewById(R.id.etadd);
        edittextName =(EditText)findViewById(R.id.etname);
        buttonSave = (Button)findViewById(R.id.buttonAdd);
        editTextNumber=(EditText)findViewById(R.id.etnumb);
        editTextCity=(EditText)findViewById(R.id.etcity);
        editTextState=(EditText)findViewById(R.id.etstate);



        FirebaseUser user =firebaseAuth.getCurrentUser();
        textViewUserEmail=(TextView)findViewById(R.id.textviewUserEmail);
        textViewUserEmail.setText("welcome "+ user.getEmail());
        buttonLogout=(Button)findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);


    }
    private void  saveUserInfo(){
        String name= edittextName.getText().toString().trim();
        String number= editTextNumber.getText().toString().trim();
        String city= editTextCity.getText().toString().trim();
        String state= editTextState.getText().toString().trim();
        String address = editextAddress.getText().toString().trim();

        userinfo userinformation =new userinfo(name ,number, city, state, address);
        FirebaseUser user =firebaseAuth.getCurrentUser();
        //String id=dr.push().getKey();
        dr.child(user.getUid()).setValue(userinformation);
        Toast.makeText(this,"information saved", Toast.LENGTH_SHORT).show();

}
    @Override
         public void onClick(View v) {
             if(v== buttonLogout){
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this , loginActivity.class));
             }
             if(v== buttonSave){
                 saveUserInfo();
                 finish();
                 startActivity(new Intent(this, photo.class));
             }
    }
}
