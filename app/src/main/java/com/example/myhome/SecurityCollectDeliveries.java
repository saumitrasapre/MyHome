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

public class SecurityCollectDeliveries extends AppCompatActivity {

    DatabaseReference reference;
    LinearLayout layoutForTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_collect_deliveries);

        reference= FirebaseDatabase.getInstance().getReference().child("packageDelivery");
        layoutForTable=(LinearLayout)findViewById(R.id.LinearLayoutForDeliveryTable);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TableLayout newTable= new TableLayout(getApplicationContext());

                for(DataSnapshot outersnapshot: dataSnapshot.getChildren()) {
                    TableLayout.LayoutParams tlp = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                    tlp.setMargins(10, 10, 10, 30);
                    newTable.setPadding(0, 30, 0, 30);
                    newTable.setBackgroundResource(R.color.grey);
                    newTable.setLayoutParams(tlp);


                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

                    TextView name=new TextView(getApplicationContext());
                    name.setText("Name");
                    name.setGravity(Gravity.CENTER);
                    name.setPadding(20,20,20,20);
                    name.setLayoutParams(lp);

                    TextView flatno=new TextView(getApplicationContext());
                    flatno.setText("Flat No");
                    flatno.setGravity(Gravity.CENTER);
                    flatno.setPadding(20,20,20,20);
                    flatno.setLayoutParams(lp);

                    TextView courier=new TextView(getApplicationContext());
                    courier.setText("Courier");
                    courier.setGravity(Gravity.CENTER);
                    courier.setPadding(20,20,20,20);
                    courier.setLayoutParams(lp);

                    row.addView(name);
                    row.addView(flatno);
                    row.addView(courier);
                    newTable.addView(row);

                    for(DataSnapshot innersnapshot: outersnapshot.getChildren())
                    {

                        final  DeliveryDetails deliveryDetails=innersnapshot.getValue(DeliveryDetails.class);
                        TableRow rowinner= new TableRow(getApplicationContext());
                        TableRow.LayoutParams lpinner = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
                        TextView nameinner=new TextView(getApplicationContext());
                        nameinner.setText(deliveryDetails.getNameOnPackage());
                        nameinner.setGravity(Gravity.CENTER);
                        nameinner.setBackgroundColor(Color.WHITE);
                        nameinner.setPadding(20,20,20,20);

                        nameinner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getApplicationContext(),SecurityDeliveryDetails.class);
                                intent.putExtra("NameOnPackage",deliveryDetails.getNameOnPackage());
                                intent.putExtra("ResPhone",deliveryDetails.getDeliveryPhoneResident());
                                intent.putExtra("CourierService",deliveryDetails.getCourierService());
                                intent.putExtra("Date",deliveryDetails.getDateSelected());
                                intent.putExtra("Status",deliveryDetails.getReceived());
                                intent.putExtra("Uid",deliveryDetails.getUid());
                                intent.putExtra("DeliveryCode",deliveryDetails.getDeliveryCode());
                                startActivity(intent);
                                finish();
                            }
                        });

                        nameinner.setLayoutParams(lpinner);

                        TextView flatnoinner=new TextView(getApplicationContext());
                        flatnoinner.setText(String.valueOf(deliveryDetails.getFlatNoForDelivery()));
                        flatnoinner.setGravity(Gravity.CENTER);
                        flatnoinner.setBackgroundColor(Color.WHITE);
                        flatnoinner.setPadding(20,20,20,20);
                        flatnoinner.setLayoutParams(lp);


                        TextView courierinner=new TextView(getApplicationContext());
                        courierinner.setText(deliveryDetails.getCourierService());
                        courierinner.setGravity(Gravity.CENTER);
                        courierinner.setBackgroundColor(Color.WHITE);
                        courierinner.setPadding(20,20,20,20);
                        courierinner.setLayoutParams(lp);

                        rowinner.addView(nameinner);
                        rowinner.addView(flatnoinner);
                        rowinner.addView(courierinner);

                        if(newTable.getParent()!=null) {
                            ((ViewGroup) newTable.getParent()).removeView(newTable);

                        }
                        newTable.addView(rowinner);
                        layoutForTable.addView(newTable);


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
