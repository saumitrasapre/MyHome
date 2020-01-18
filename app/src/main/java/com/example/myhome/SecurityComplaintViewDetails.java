package com.example.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecurityComplaintViewDetails extends AppCompatActivity {

    TextView nameText,phoneText,categoryText,complaintText,pendingText;
    DatabaseReference reference;
    FirebaseAuth auth;
    Button completebtn,cancelcompletecomplaint;
    String complaint,uid,complaintCode;
    Boolean isPending;
    ComplaintClass receivedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_complaint_view_details);

        nameText=(TextView)findViewById(R.id.SecNameTextView);
        phoneText=(TextView)findViewById(R.id.SecPhoneNumber);
        categoryText=(TextView)findViewById(R.id.SecCategoryofComplaint);
        complaintText=(TextView)findViewById(R.id.SecActualComplaint);
        pendingText=(TextView)findViewById(R.id.StatusofComplaint);
        completebtn=(Button)findViewById(R.id.SecComplaintSetCompleted);
        cancelcompletecomplaint=(Button)findViewById(R.id.SecComplaintCancelBtn);


        auth=FirebaseAuth.getInstance();


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            receivedObject= (ComplaintClass) bundle.getSerializable("ComplaintObject");
            String name=bundle.getString("ResName");
            Long phone =bundle.getLong("ResPhone");
            String category=bundle.getString("ResCategory");
            complaint=bundle.getString("Complaint");
            isPending=bundle.getBoolean("Status");
            uid=bundle.getString("Uid");
            complaintCode=bundle.getString("complaintCode");
            nameText.setText(name);
            phoneText.setText(String.valueOf(phone));
            categoryText.setText(category);
            pendingText.setText("");
            complaintText.setText(complaint);
            if(isPending==true) {
                pendingText.setText("Status: Pending");
                cancelcompletecomplaint.setVisibility(Button.GONE);

            }else
            {
                pendingText.setText("Status: Complete");
            }
            reference= FirebaseDatabase.getInstance().getReference().child("complaints").child(uid).child(complaintCode);

        }

        if(cancelcompletecomplaint.getVisibility()==View.VISIBLE) {
            cancelcompletecomplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                            {
                                snapshot.getRef().removeValue();
                            }
                            Intent intent=new Intent(getApplicationContext(),SecurityComplaintManager.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        completebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child("pending").setValue(Boolean.valueOf(false));
                Intent intent=new Intent(getApplicationContext(),SecurityComplaintManager.class);
                startActivity(intent);
                finish();


            }
        });

    }
}
