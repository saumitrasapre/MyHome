package com.example.myhome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CalendarScheduledResident extends AppCompatActivity {

    CompactCalendarView compactCalendarView;
    TextView monthTextView;
    String dateSelected;
    DatabaseReference reference,ref;
    Button removeDeliveryBtn;
   LinearLayout residentCalendarLayout;
    FirebaseUser auth;
    private SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MMM-YYYY", Locale.UK);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_scheduled_resident);

        monthTextView = (TextView) findViewById(R.id.month_text_view);
        removeDeliveryBtn=(Button)findViewById(R.id.removeDeliveryBtn);
        residentCalendarLayout=(LinearLayout)findViewById(R.id.LinearLayoutCalendarEvents);
        removeDeliveryBtn.setVisibility(View.GONE);

        DateTime dt = new DateTime();
        DateTime.Property propertymonth = dt.monthOfYear();
        DateTime.Property propertyyear = dt.year();
        String month = propertymonth.getAsShortText();
        String year = propertyyear.getAsShortText();
        monthTextView.setText(month + "-" + year);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("packageDelivery").child(auth.getUid());
        ref = FirebaseDatabase.getInstance().getReference().child("packageDelivery").child(auth.getUid());

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DeliveryDetails deliveryDetails = snapshot.getValue(DeliveryDetails.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    try {
                        Date mDate = sdf.parse(deliveryDetails.getDateSelected());
                        long timeInMilliseconds = mDate.getTime();

                        if(deliveryDetails.getReceived()==true)
                        {
                            Event packageDelivery = new Event(Color.CYAN, timeInMilliseconds, deliveryDetails.getCourierService() + " Delivery For " + deliveryDetails.getNameOnPackage() + " is collected at Security Gate ");

                            compactCalendarView.addEvent(packageDelivery);

                        }
                        else {

                            Event packageDelivery = new Event(Color.BLUE, timeInMilliseconds, deliveryDetails.getCourierService() + " Delivery For " + deliveryDetails.getNameOnPackage() + " at " + deliveryDetails.getFlatNoForDelivery());
                            compactCalendarView.addEvent(packageDelivery);
                        }



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
            public void onDayClick(final Date dateClicked) {

                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Iterator iterator=events.iterator();
                residentCalendarLayout.removeAllViews();

                if(events.isEmpty())
                {
                    removeDeliveryBtn.setVisibility(View.GONE);
                    TextView textView=new TextView(getApplicationContext());
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextSize(20);
                    textView.setText("No Delivery for this day...");
                    residentCalendarLayout.addView(textView);
                }
                else
                {
                    removeDeliveryBtn.setVisibility(View.VISIBLE);
                    String eventString;
                    Log.d("Calendar",events.toString());
                    while(iterator.hasNext())
                    {
                        TextView textView=new TextView(getApplicationContext());
                        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setTextSize(20);
                        eventString=iterator.next().toString().substring(56);
                        textView.setText(eventString);
                        residentCalendarLayout.addView(textView);
                        //Log.d("Calendar", "Day was clicked: " + dateClicked + " with events " + eventString);
                    }
                    removeDeliveryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Query myQuery=reference.orderByChild("dateSelected").equalTo(String.valueOf(dateClicked));
                            myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                                    {
                                        snapshot.getRef().removeValue();
                                    }

                                    Intent intent=new Intent(getApplicationContext(),ManageDeliveryScreen.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(getApplicationContext(),"Delivery Schedule Removed",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

//                if(dateClicked.toString().compareTo("Mon Oct 21 00:00:00 GMT+05:30 2019")==0)
//                {
//                    //Toast.makeText(context,"Flipkart Delivery For C193",Toast.LENGTH_SHORT).show();
//
//                }
//                else
//                {
//
//                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                residentCalendarLayout.removeAllViews();
                removeDeliveryBtn.setVisibility(View.GONE);
                monthTextView.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }



}

