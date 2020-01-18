package com.example.myhome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarEventActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    Button btnNext;
    String dateSelected;
    DatabaseReference reference;
    TextView monthTextView;
    FirebaseUser auth;
    private SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MMM-YYYY", Locale.UK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event);
        monthTextView=(TextView)findViewById(R.id.month_text_view);

        DateTime dt=new DateTime();
        DateTime.Property propertymonth=dt.monthOfYear();
        DateTime.Property propertyyear=dt.year();
        String month=propertymonth.getAsShortText();
        String year=propertyyear.getAsShortText();
        monthTextView.setText(month+"-"+year);

        auth= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("packageDelivery").child(auth.getUid());
        compactCalendarView=(CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        btnNext=(Button)findViewById(R.id.btnNext);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    DeliveryDetails deliveryDetails=snapshot.getValue(DeliveryDetails.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    try {
                        Date mDate = sdf.parse(deliveryDetails.getDateSelected());
                        long timeInMilliseconds = mDate.getTime();
                        //System.out.println("Date in milli :: " + timeInMilliseconds);

                        Event packageDelivery =new Event(Color.RED,timeInMilliseconds,deliveryDetails.getCourierService()+" Delivery For "+deliveryDetails.getFlatNoForDelivery());
                        compactCalendarView.addEvent(packageDelivery);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {

                Context context=getApplicationContext();
                // Toast.makeText(context,dateClicked.toString(),Toast.LENGTH_LONG).show();

                dateSelected=dateClicked.toString();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }

        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DeliveryEvent.class);
                intent.putExtra("Date Selected",dateSelected);
                startActivity(intent);
                finish();
            }
        });
    }

    }

