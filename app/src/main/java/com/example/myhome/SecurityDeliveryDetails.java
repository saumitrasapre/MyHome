package com.example.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SecurityDeliveryDetails extends AppCompatActivity {

    TextView nameOnPackage,deliveryPhoneResident,courierService,dateofDelivery,deliveryStatus;
    Button deliveryCompleted,cancelDelivery;
    String uid,deliveryCode;
    Boolean isReceived;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_delivery_details);

        nameOnPackage=(TextView)findViewById(R.id.nameonPackageTextView);
        deliveryPhoneResident=(TextView)findViewById(R.id.deliveryPhoneResidentTextView);
        courierService=(TextView)findViewById(R.id.courierServiceTextView);
        dateofDelivery=(TextView)findViewById(R.id.dateTextView);
        deliveryStatus=(TextView)findViewById(R.id.StatusofDelivery);

        deliveryCompleted=(Button)findViewById(R.id.SecDeliverySetCompleted);
        cancelDelivery=(Button)findViewById(R.id.SecDeliveryCancelBtn);


        auth=FirebaseAuth.getInstance();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {

            String name=bundle.getString("NameOnPackage");
            String date=bundle.getString("Date");
            String dateOmit=date.substring(11,29);
            String finalDate=date.replace(dateOmit,"");
            Long phone =bundle.getLong("ResPhone");
            String courierServiceText=bundle.getString("CourierService");
            isReceived=bundle.getBoolean("Status");
            uid=bundle.getString("Uid");
            deliveryCode=bundle.getString("DeliveryCode");

            nameOnPackage.setText("Name: "+name);
            dateofDelivery.setText("Date of Delivery: "+finalDate);
            deliveryPhoneResident.setText("Phone Number: "+String.valueOf(phone));
            courierService.setText("Courier Service: "+courierServiceText);
            deliveryStatus.setText("");

            if(isReceived==false)
            {
                deliveryStatus.setText("Status: Not Yet Received");
                cancelDelivery.setVisibility(Button.GONE);
            }
            else
            {
                deliveryStatus.setText("Status: Package Collected");
            }

            reference= FirebaseDatabase.getInstance().getReference().child("packageDelivery").child(uid).child(deliveryCode);
        }


        if(cancelDelivery.getVisibility()== View.VISIBLE) {
            cancelDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot: dataSnapshot.getChildren())
                            {
                                snapshot.getRef().removeValue();
                            }
                            Intent intent=new Intent(getApplicationContext(),SecurityCollectDeliveries.class);
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

        deliveryCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child("received").setValue(Boolean.valueOf(true));
                Intent intent=new Intent(getApplicationContext(),SecurityCollectDeliveries.class);
                startActivity(intent);
                finish();


            }
        });





    }
}
