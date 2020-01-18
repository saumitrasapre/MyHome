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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ComplaintDetails extends AppCompatActivity {
    TextView nameText,phoneText,categoryText,complaintText;
    FirebaseAuth auth;
    Button cancelbtn;
    String complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        nameText=(TextView)findViewById(R.id.NameTextView);
        phoneText=(TextView)findViewById(R.id.PhoneNumber);
        categoryText=(TextView)findViewById(R.id.CategoryofComplaint);
        complaintText=(TextView)findViewById(R.id.ActualComplaint);
        cancelbtn=(Button)findViewById(R.id.Cancel_complaint);
        auth=FirebaseAuth.getInstance();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String name=bundle.getString("ResName");
            Long phone =bundle.getLong("ResPhone");
            String category=bundle.getString("ResCategory");
            complaint=bundle.getString("Complaint");
            nameText.setText(name);
            phoneText.setText(String.valueOf(phone));
            categoryText.setText(category);
            complaintText.setText(complaint);


        }
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("complaints");
                Query removeComplaintQuery=reference.child(auth.getCurrentUser().getUid()).orderByChild("complaintString").equalTo(complaint);

                removeComplaintQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot complaintSnapshot: dataSnapshot.getChildren())
                        {
                            complaintSnapshot.getRef().removeValue();
                        }
                        Intent intent=new Intent(getApplicationContext(),ComplaintLog.class);
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
}
