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

public class DeliveryEvent extends AppCompatActivity {

    EditText nameOnPackage,deliveryphoneResident,courierService;
    Button notifyDelivery;
    FirebaseUser auth;
    String flatnoforDelivery;
    DatabaseReference reference;
    String dateSelected;
    int max_id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_event);

        nameOnPackage=(EditText)findViewById(R.id.nameonPackage);
        deliveryphoneResident=(EditText)findViewById(R.id.deliveryPhoneResident);
        courierService=(EditText)findViewById(R.id.courierService);
        notifyDelivery=(Button)findViewById(R.id.notifyDelivery);
        auth= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("packageDelivery").child(auth.getUid());

        final Bundle bundle= getIntent().getExtras();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("member").child(auth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Member member=dataSnapshot.getValue(Member.class);
                flatnoforDelivery=member.getFlatno().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        notifyDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DeliveryDetails deliveryDetails=new DeliveryDetails();
                deliveryDetails.setNameOnPackage(nameOnPackage.getText().toString());
                deliveryDetails.setDeliveryPhoneResident(Long.parseLong(deliveryphoneResident.getText().toString()));
                deliveryDetails.setFlatNoForDelivery(flatnoforDelivery);
                deliveryDetails.setUid(auth.getUid());
                dateSelected=bundle.getString("Date Selected");
                deliveryDetails.setDateSelected(dateSelected);
                deliveryDetails.setReceived(false);

                Random random=new Random();
                max_id=random.nextInt(100000);
                deliveryDetails.setDeliveryCode("Delivery"+String.valueOf(max_id));
                deliveryDetails.setCourierService(courierService.getText().toString());

                reference.child("Delivery"+String.valueOf(max_id)).setValue(deliveryDetails);
                Toast.makeText(getApplicationContext(),"Delivery Details Notified",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
