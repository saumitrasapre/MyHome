package com.example.myhome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisitorQuery extends AppCompatActivity {

    private ImageView visitorReceivedImage;
    private Button acceptBtn,rejectBtn;
    private RequestQueue mRequestQueue;
    private String visitorName;
    private Long visitorNumber;
    private String imageUrl;
    private String flNo;
    TextView visitorNameDisplay,visitorNumberDisplay;
    private String URL="https://fcm.googleapis.com/fcm/send";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_query);


        acceptBtn=(Button)findViewById(R.id.allowBtn);
        rejectBtn=(Button)findViewById(R.id.denyBtn);
        mRequestQueue= Volley.newRequestQueue(this);
        visitorNameDisplay=(TextView)findViewById(R.id.visitorNamedisplay);
        visitorReceivedImage=(ImageView)findViewById(R.id.visitorReceivedImage);
        visitorNameDisplay=(TextView)findViewById(R.id.visitorNamedisplay);
        visitorNumberDisplay=(TextView)findViewById(R.id.visitorNumberDisplay);
        auth=FirebaseAuth.getInstance();
        Bundle  notifData=getIntent().getExtras();
        if(notifData==null)
        {
            return;
        }
        else
        {
            String toDisplay=notifData.getString("visitorDetails");
            //visitorNameDisplay.setText(toDisplay);

            displayImage();

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sendNotificationPos();
                    finish();

                }
            });

            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendNotificationNeg();
                    finish();
                }
            });
        }
    }

    void displayImage() {

        final DatabaseReference myreference= FirebaseDatabase.getInstance().getReference().child("visitor");
        myreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
                {
                    visitorName = dataSnapshot.child(auth.getCurrentUser().getUid()).child("vName").getValue().toString();
                    visitorNumber = Long.parseLong(dataSnapshot.child(auth.getCurrentUser().getUid()).child("vNumber").getValue().toString());
                    imageUrl = dataSnapshot.child(auth.getCurrentUser().getUid()).child("mImageUrl").getValue().toString();
                    visitorNameDisplay.setText("NAME: "+ visitorName);
                    visitorNumberDisplay.setText("PHONE NUMBER: "+visitorNumber.toString());
                    Picasso.get().load(imageUrl).into(visitorReceivedImage);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void sendNotificationPos() {


        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
                {
                    flNo=dataSnapshot.child(auth.getCurrentUser().getUid()).child("flatno").getValue().toString();

                    //We are sending notification in the form of a JSON object
                    //It will look like:
        /*{
            "to": "topics/topic name", //'to' defines to whom we want to send the message
            Another JSON object:
            notification:{
                title:"some title"
                body: "our body"
            }

        }*/

                    JSONObject mainObj= new JSONObject();
                    try {
                        mainObj.put("to","/topics/"+"security");
                        JSONObject notificationObj=new JSONObject();
                        notificationObj.put("title","Visitor Response");
                        notificationObj.put("body","Visitor Allowed For "+flNo);
                        notificationObj.put("click_action",".VisitorDetails");
                        mainObj.put("notification",notificationObj);

                        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, URL, mainObj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                //If notification is successfully sent, code here will run

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                ///If an error occurs, code here will run
                            }
                        })
                        {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map <String,String> header=new HashMap<>();
                                header.put("content-type","application/json");
                                header.put("Authorization","key=AIzaSyDXSLJX8zienVnejOTtIAdAueG939L_RAY");
                                return header;
                            }
                        };

                        mRequestQueue.add(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void sendNotificationNeg() {


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("member");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                    flNo = dataSnapshot.child(auth.getCurrentUser().getUid()).child("flatno").getValue().toString();

                    //We are sending notification in the form of a JSON object
                    //It will look like:
        /*{
            "to": "topics/topic name", //'to' defines to whom we want to send the message
            Another JSON object:
            notification:{
                title:"some title"
                body: "our body"
            }

        }*/

                    JSONObject mainObj = new JSONObject();
                    try {
                        mainObj.put("to", "/topics/" + "security");
                        JSONObject notificationObj = new JSONObject();
                        notificationObj.put("title", "Visitor Response");
                        notificationObj.put("body", "Visitor Not Allowed For " + flNo);
                        notificationObj.put("click_action", ".VisitorDetails");
                        mainObj.put("notification", notificationObj);

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, mainObj, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                //If notification is successfully sent, code here will run

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                ///If an error occurs, code here will run
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> header = new HashMap<>();
                                header.put("content-type", "application/json");
                                header.put("Authorization", "key=AIzaSyDXSLJX8zienVnejOTtIAdAueG939L_RAY");
                                return header;
                            }
                        };

                        mRequestQueue.add(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
