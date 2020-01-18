package com.example.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageDeliveryScreen extends AppCompatActivity {

    ImageView viewScheduled,newDelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_delivery_screen);

        viewScheduled=(ImageView)findViewById(R.id.viewScheduled);
        newDelivery=(ImageView)findViewById(R.id.newDelivery);

        viewScheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),CalendarScheduledResident.class);
                startActivity(intent);
            }
        });

        newDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CalendarEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
