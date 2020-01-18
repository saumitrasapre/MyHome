package com.example.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashlogo;
    private TextView splashtext;
    private static int splashTimeOut=3000;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashlogo=(ImageView) findViewById(R.id.splashlogo);
        splashtext=(TextView)findViewById(R.id.splashtext);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mysplashanimation);
        splashlogo.startAnimation(myanim);
        splashtext.startAnimation(myanim);
        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(auth.getCurrentUser()!=null) {

                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
                            {
                                Intent intent=new Intent(getApplicationContext(),ResidentSkipLogin.class);
                                startActivity(intent);
                                finish();
                            }
                            else {

                                DatabaseReference referencesec=FirebaseDatabase.getInstance().getReference().child("security");
                                referencesec.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
                                        {
                                            Intent intent=new Intent(getApplicationContext(),SecuritySkipLogin.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            Intent intent=new Intent(getApplicationContext(),AfterLogin.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//            Intent intent = new Intent(getApplicationContext(), AfterLogin.class);
//            startActivity(intent);
//            finish();
                }
                else {

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        },splashTimeOut);


    }
}
