package com.example.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComplaintLog extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_log);
        auth=FirebaseAuth.getInstance();

        reference=FirebaseDatabase.getInstance().getReference().child("complaints").child(auth.getCurrentUser().getUid());
        final TableLayout tableLayout=findViewById(R.id.myTable);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    final ComplaintClass complaintClass=snapshot.getValue(ComplaintClass.class);
                    TableRow row= new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
                    //row.setLayoutParams(lp);
                    TextView name=new TextView(getApplicationContext());
                    name.setText(complaintClass.getResidentName());
                    name.setGravity(Gravity.CENTER);
                    name.setBackgroundColor(Color.WHITE);
                    name.setPadding(20,20,20,20);
                    name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(getApplicationContext(),ComplaintDetails.class);
                            intent.putExtra("ResName",complaintClass.getResidentName());
                            intent.putExtra("ResPhone",complaintClass.getResidentPhone());
                            intent.putExtra("ResCategory",complaintClass.getComplaintCategory());
                            intent.putExtra("Complaint",complaintClass.getComplaintString());
                            startActivity(intent);
                            finish();
                        }
                    });
                    name.setLayoutParams(lp);


                    TextView phone=new TextView(getApplicationContext());
                    phone.setText(String.valueOf(complaintClass.getResidentPhone()));
                    phone.setGravity(Gravity.CENTER);
                    phone.setBackgroundColor(Color.WHITE);
                    phone.setPadding(20,20,20,20);
                    phone.setLayoutParams(lp);

                    TextView category=new TextView(getApplicationContext());
                    category.setText(complaintClass.getComplaintCategory());
                    category.setGravity(Gravity.CENTER);
                    category.setBackgroundColor(Color.WHITE);
                    category.setPadding(20,20,20,20);
                    category.setLayoutParams(lp);

                    row.addView(name);
                    row.addView(phone);
                    row.addView(category);
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
