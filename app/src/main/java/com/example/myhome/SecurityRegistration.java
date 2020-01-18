package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Struct;

public class SecurityRegistration extends AppCompatActivity {

  EditText securityName,securityphone;
  Button registerSecurity;
  DatabaseReference reference;
  Security security;
  long maxID=0;
  FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_security_registration);

    security=new Security();
    securityName=(EditText)findViewById(R.id.nameSecurity);
    securityphone=(EditText)findViewById(R.id.phoneSecurity);
    registerSecurity=(Button)findViewById(R.id.securityRegister);
    reference= FirebaseDatabase.getInstance().getReference().child("security");

    registerSecurity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String secName=securityName.getText().toString();
        long secPhone=Long.parseLong(securityphone.getText().toString());

        security.setName(secName);
        security.setPhone(secPhone);
        security.setEmail(user.getEmail());
        security.setRegistered(true);
        security.setUID(user.getUid());

        reference.child(user.getUid()).setValue(security);
        Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_SHORT).show();
        finish();
      }
    });

  }
}
