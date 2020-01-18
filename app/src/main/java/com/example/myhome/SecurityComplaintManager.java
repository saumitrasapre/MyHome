package com.example.myhome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecurityComplaintManager extends AppCompatActivity {

    DatabaseReference reference;
    LinearLayout layoutForTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_complaint_manager);

        reference= FirebaseDatabase.getInstance().getReference().child("complaints");
        layoutForTable=(LinearLayout)findViewById(R.id.LinearLayoutForTable);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TableLayout newTable= new TableLayout(getApplicationContext());
                for(DataSnapshot outersnapshot: dataSnapshot.getChildren())
                {
                    TableLayout.LayoutParams tlp= new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                    tlp.setMargins(10,10,10,30);
                    newTable.setPadding(0,30,0,30);
                    newTable.setBackgroundResource(R.color.grey);
                    newTable.setLayoutParams(tlp);


                    TableRow row= new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);

                    TextView name=new TextView(getApplicationContext());
                    name.setText("Name");
                    name.setGravity(Gravity.CENTER);
                    name.setPadding(20,20,20,20);
                    name.setLayoutParams(lp);

                    TextView phone=new TextView(getApplicationContext());
                    phone.setText("Phone");
                    phone.setGravity(Gravity.CENTER);
                    phone.setPadding(20,20,20,20);
                    phone.setLayoutParams(lp);

                    TextView category=new TextView(getApplicationContext());
                    category.setText("Category");
                    category.setGravity(Gravity.CENTER);
                    category.setPadding(20,20,20,20);
                    category.setLayoutParams(lp);




                    row.addView(name);
                    row.addView(phone);
                    row.addView(category);
                    newTable.addView(row);
                    //layoutForTable.addView(newTable);



                    for(DataSnapshot innersnapshot: outersnapshot.getChildren())
                    {
                        final ComplaintClass myClass=innersnapshot.getValue(ComplaintClass.class);
                        TableRow rowinner= new TableRow(getApplicationContext());
                        TableRow.LayoutParams lpinner = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
                        TextView nameinner=new TextView(getApplicationContext());
                        nameinner.setText(myClass.getResidentName());
                        nameinner.setGravity(Gravity.CENTER);
                        nameinner.setBackgroundColor(Color.WHITE);
                        nameinner.setPadding(20,20,20,20);

                        nameinner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getApplicationContext(),SecurityComplaintViewDetails.class);
                                intent.putExtra("ComplaintObject",myClass);
                                intent.putExtra("ResName",myClass.getResidentName());
                                intent.putExtra("ResPhone",myClass.getResidentPhone());
                                intent.putExtra("ResCategory",myClass.getComplaintCategory());
                                intent.putExtra("Complaint",myClass.getComplaintString());
                                intent.putExtra("Status",myClass.getPending());
                                intent.putExtra("Uid",myClass.getUid());
                                intent.putExtra("complaintCode",myClass.getComplaintCode());
                                startActivity(intent);
                                finish();
                            }
                        });


                        nameinner.setLayoutParams(lpinner);

                        TextView phoneinner=new TextView(getApplicationContext());
                        phoneinner.setText(String.valueOf(myClass.getResidentPhone()));
                        phoneinner.setGravity(Gravity.CENTER);
                        phoneinner.setBackgroundColor(Color.WHITE);
                        phoneinner.setPadding(20,20,20,20);
                        phoneinner.setLayoutParams(lp);


                        TextView categoryinner=new TextView(getApplicationContext());
                        categoryinner.setText(myClass.getComplaintCategory());
                        categoryinner.setGravity(Gravity.CENTER);
                        categoryinner.setBackgroundColor(Color.WHITE);
                        categoryinner.setPadding(20,20,20,20);
                        categoryinner.setLayoutParams(lp);

                        rowinner.addView(nameinner);
                        rowinner.addView(phoneinner);
                        rowinner.addView(categoryinner);

                        if(newTable.getParent()!=null) {
                            ((ViewGroup) newTable.getParent()).removeView(newTable);

                        }
                        newTable.addView(rowinner);
                        layoutForTable.addView(newTable);




                        System.out.println(myClass.getResidentName());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
