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

public class ResidentSkipLogin extends AppCompatActivity {
    ImageView logoutbtn,maintenancefeepayment,logComplaint,viewComplaint,scheduleDeliveries;
    TextView residentEmail;


    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_skip_login);
        logoutbtn=(ImageView)findViewById(R.id.logoutbtn);
        maintenancefeepayment=(ImageView)findViewById(R.id.maintenancefeepay);
        logComplaint=(ImageView)findViewById(R.id.complaintRegister);
        residentEmail=(TextView)findViewById(R.id.residentEmail);
        viewComplaint=(ImageView)findViewById(R.id.viewComplaints);
        scheduleDeliveries=(ImageView)findViewById(R.id.Delivery);
        auth=FirebaseAuth.getInstance();


        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(auth.getCurrentUser()!=null) {
                    if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                        String flNo;
                        flNo = dataSnapshot.child(auth.getCurrentUser().getUid()).child("flatno").getValue().toString();
                        FirebaseMessaging.getInstance().subscribeToTopic("news" + flNo);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("security");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        residentEmail.setText(user.getEmail());

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        maintenancefeepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),FeePayment.class);
                startActivity(intent);
            }
        });

        logComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Complaints.class);
                startActivity(intent);
            }
        });

        viewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ComplaintLog.class);
                startActivity(intent);
            }
        });

        scheduleDeliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ManageDeliveryScreen.class);
                startActivity(intent);
            }
        });
    }
}
