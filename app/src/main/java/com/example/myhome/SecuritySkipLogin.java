package com.example.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Map;

public class SecuritySkipLogin extends AppCompatActivity {

  private ImageView seclogoutbtn,secsendnotif,secScanStaff,seccomplaintManagebtn,securityDeliveryCollect;
  private TextView securityEmail;
  ArrayList<String > flats=new ArrayList<>();

  FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_security_skip_login);

    secsendnotif=(ImageView) findViewById(R.id.sendvisitornotif);
    seclogoutbtn=(ImageView) findViewById(R.id.seclogoutbtn);
    secScanStaff=(ImageView) findViewById(R.id.scanQR);
    seccomplaintManagebtn=(ImageView) findViewById(R.id.ManageComplaintBtn);
    securityDeliveryCollect=(ImageView)findViewById(R.id.SecurityDeliveryCollect);
    securityEmail=(TextView)findViewById(R.id.securityEmail);




    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        collectFlatNos((Map<String,Object>)dataSnapshot.getValue());

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

    FirebaseMessaging.getInstance().unsubscribeFromTopic("newsC193");
    FirebaseMessaging.getInstance().unsubscribeFromTopic("newsC102");
    FirebaseMessaging.getInstance().unsubscribeFromTopic("newsC189");
    FirebaseMessaging.getInstance().unsubscribeFromTopic("newsC190");
    FirebaseMessaging.getInstance().subscribeToTopic("security");
    Runnable r=new Runnable() {
      @Override
      public void run() {
        for(String st : flats)
        {
          FirebaseMessaging.getInstance().unsubscribeFromTopic("news"+st);
        }
      }
    };
    Thread thread=new Thread(r);
    thread.start();





    securityEmail.setText(user.getEmail());

    seclogoutbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

      }
    });

    secsendnotif.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // sendNotification();
        Intent intent=new Intent(getApplicationContext(),VisitorDetails.class);
        startActivity(intent);
      }
    });

    secScanStaff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),PreQRScan.class);
        startActivity(intent);
      }
    });

    seccomplaintManagebtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent=new Intent(getApplicationContext(),SecurityComplaintManager.class);
        startActivity(intent);
      }
    });

    securityDeliveryCollect.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),SecurityCollectDeliveries.class);
        startActivity(intent);
      }
    });


  }
  private void collectFlatNos(Map<String, Object> users) {

    for(Map.Entry<String,Object>entry : users.entrySet())
    {
      Map singleUser=(Map)entry.getValue();
      flats.add((String)singleUser.get("flatno"));
    }
  }


}
