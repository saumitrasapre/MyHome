package com.example.myhome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Complaints extends AppCompatActivity {

    EditText complaintResidentName,complaintResidentphone,complaintText,complaintCategory;
    Button complaintRegister;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("complaints").child(user.getUid());
    int max_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        complaintResidentName=(EditText)findViewById(R.id.nameResidentforComplaint);
        complaintResidentphone=(EditText)findViewById(R.id.phoneResidentComplaint);
        complaintCategory=(EditText)findViewById(R.id.complaintCategory);
        complaintText=(EditText)findViewById(R.id.complaintText);
        complaintRegister=(Button)findViewById(R.id.complaintRegister);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               max_id=dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        complaintRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintClass complaintClass=new ComplaintClass();
                complaintClass.setResidentName(complaintResidentName.getText().toString());
                complaintClass.setResidentPhone(Long.parseLong(complaintResidentphone.getText().toString()));
                complaintClass.setComplaintCategory(complaintCategory.getText().toString());
                complaintClass.setComplaintString(complaintText.getText().toString());
                complaintClass.setPending(true);
                complaintClass.setUid(user.getUid());

                Random random=new Random();
                max_id=random.nextInt(100000);
                complaintClass.setComplaintCode("Complaint"+String.valueOf(max_id));
                reference.child("Complaint"+String.valueOf(max_id)).setValue(complaintClass);
                Toast.makeText(Complaints.this,"Complaint Successfully added",Toast.LENGTH_SHORT).show();
                finish();


            }
        });





    }
}
