package com.example.myhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private TextView btnLogin;
    private TextView btnSignup;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        auth=FirebaseAuth.getInstance();

        inputEmail=(EditText)findViewById(R.id.emailmain);
        inputPassword=(EditText)findViewById(R.id.passwordmain);
        btnSignup=(TextView) findViewById(R.id.signupbutton);
        btnLogin=(TextView) findViewById(R.id.signinbutton);



//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String email=inputEmail.getText().toString();
//                final  String password=inputPassword.getText().toString();
//
//                try {
//                    if (password.length() > 0 && email.length() > 0) {
//                        pd.show();
//                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull final Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");
//                                    reference.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
//                                            {
//                                                Intent intent=new Intent(getApplicationContext(),ResidentSkipLogin.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }
//                                            else {
//                                                DatabaseReference referencesec=FirebaseDatabase.getInstance().getReference().child("security");
//                                                referencesec.addValueEventListener(new ValueEventListener() {
//                                                    @Override
//                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                        if(dataSnapshot.hasChild(auth.getCurrentUser().getUid()))
//                                                        {
//                                                            Intent intent=new Intent(getApplicationContext(),SecuritySkipLogin.class);
//                                                            startActivity(intent);
//                                                            finish();
//                                                        }
//                                                        else
//                                                        {
//                                                            Intent intent=new Intent(getApplicationContext(),AfterLogin.class);
//                                                            startActivity(intent);
//                                                            finish();
//                                                        }
//                                                    }
//
//                                                    @Override
//                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                    }
//                                                });
//
//
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//
//                                } else {
//
//                                    Toast.makeText(getApplicationContext(),"Error signing in...", Toast.LENGTH_SHORT).show();
//                                    pd.dismiss();
//                                    return;
//
//                                }
//                                pd.dismiss();
//                            }
//                        });
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.Forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgotAndChangePasswordActivity.class).putExtra("Mode",0);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onResume() {

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

        super.onResume();
    }

    public void roundButtonClick(View view)
    {

        final String email=inputEmail.getText().toString();
        final  String password=inputPassword.getText().toString();

        try {
            if (password.length() > 0 && email.length() > 0) {
                pd.show();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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


                        } else {

                            Toast.makeText(getApplicationContext(),"Error signing in...", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            return;

                        }
                        pd.dismiss();
                    }
                });

            } else {
                Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
