package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AfterLogin extends AppCompatActivity {

  Button logoutbtn,residentbtn,securitybtn;
  TextView emailIDafterlogin;
  FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_after_login);
    residentbtn=(Button)findViewById(R.id.residentbtn);
    securitybtn=(Button)findViewById(R.id.securitybtn);

    logoutbtn=(Button)findViewById(R.id.logoutbutton);
    emailIDafterlogin=(TextView)findViewById(R.id.emailidafterlogin);

    emailIDafterlogin.setText(user.getEmail());
    logoutbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
      }
    });

    residentbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),ResidentRegistration.class);
        startActivity(intent);
        finish();

      }
    });


    securitybtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),SecurityRegistration.class);
        startActivity(intent);
        finish();
      }
    });

  }
}
