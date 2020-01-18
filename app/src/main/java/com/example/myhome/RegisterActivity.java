package com.example.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail,inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(RegisterActivity.this,AfterLogin.class);
            startActivity(intent);
            finish();
        }

        inputEmail=(EditText)findViewById(R.id.email);
        inputPassword=(EditText)findViewById(R.id.password);
        btnSignup=(Button)findViewById(R.id.sign_up_button);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=inputEmail.getText().toString();
                final  String password=inputPassword.getText().toString();

                try
                {
                    if(password.length()>0 && email.length()>0)
                    {
                        pd.show();
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    return;
                                    //Log.v("error",task.getResult().toString());
                                }
                                else
                                {
                                    Intent myintent=new Intent(getApplicationContext(),AfterLogin.class);
                                    startActivity(myintent);
                                    pd.dismiss();

                                    Toast.makeText(getApplicationContext(),"Sign Up successful...You will be taken to your account",Toast.LENGTH_SHORT).show();
                                    finish();
                                    return;
                                }
//                                pd.dismiss();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Fill All Fields",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });



        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
