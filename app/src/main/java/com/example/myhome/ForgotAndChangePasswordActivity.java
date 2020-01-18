package com.example.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotAndChangePasswordActivity extends AppCompatActivity {

  private EditText editmail;
  private TextView txtmail,txtinst;
  private Button submit;
  private FirebaseAuth auth;
  private ProgressDialog pd;
  //private TextInputLayout labelMode;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_and_change_password);

    pd=new ProgressDialog(this);
    pd.setMessage("Loading...");
    pd.setCancelable(true);
    pd.setCanceledOnTouchOutside(false);

    auth=FirebaseAuth.getInstance();

    editmail=(EditText)findViewById(R.id.emailforgot);
    txtmail=(TextView)findViewById(R.id.title);
    submit=(Button)findViewById(R.id.submit_button);
    txtinst=(TextView)findViewById(R.id.instructions);

    final int mode=getIntent().getIntExtra("Mode",0);
    if(mode==0)
    {
      txtmail.setText("Forgot Password?");
      editmail.setHint("Enter Registered Email");
      txtinst.setText("We just need your registered email to send you the password reset instructions.");
    }
    else if(mode==1)
    {
      txtmail.setText("Forgot Password");
      editmail.setHint("Enter new Password");
    }
    else if(mode==2)
    {
      txtmail.setText("Change Email");
      editmail.setHint("Enter new Email ID");
    }
    else
    {
      txtmail.setText("Forgot Password");
      editmail.setVisibility(View.GONE);
    }

    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        callFunction(mode);
      }
    });
  }

  public void callFunction(int mode)
  {
    FirebaseUser user=auth.getCurrentUser();
    final String modeString=editmail.getText().toString();

    if(mode==0)
    {
      if(TextUtils.isEmpty(modeString))
      {
        Toast.makeText(getApplicationContext(),"Value Required",Toast.LENGTH_SHORT).show();
      }
      else
      {
        pd.show();
        auth.sendPasswordResetEmail(modeString).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
            {
              Toast.makeText(getApplicationContext(),"We have sent you instructions to reset your password...",Toast.LENGTH_SHORT).show();
            }
            else
            {
              Toast.makeText(getApplicationContext(),"Failed to send the password reset email",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
          }
        });
      }
    }

    else if(mode==1)
    {
      if(TextUtils.isEmpty(modeString))
      {
        Toast.makeText(getApplicationContext(),"Value Required",Toast.LENGTH_SHORT).show();
      }
      else
      {
        pd.show();
        user.updatePassword(modeString).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
            {
              Toast.makeText(getApplicationContext(),"Password updated successfully...",Toast.LENGTH_SHORT).show();
            }
            else
            {
              Toast.makeText(getApplicationContext(),"Failed to update password",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
          }
        });
      }
    }
    else if(mode==2)
    {
      if(TextUtils.isEmpty(modeString))
      {
        Toast.makeText(getApplicationContext(),"Value required",Toast.LENGTH_SHORT).show();
      }
      else
      {
        pd.show();
        user.updateEmail(modeString).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
            {
              Toast.makeText(getApplicationContext(),"Email address updated...",Toast.LENGTH_SHORT).show();
            }
            else
            {
              Toast.makeText(getApplicationContext(),"Failed to update email...",Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();

          }
        });
      }

    }
    else
    {
      if(user!=null)
      {
        pd.show();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful())
            {
              Toast.makeText(getApplicationContext(),"Your profile is deleted :( make a new one...",Toast.LENGTH_LONG).show();
            }
            else
            {
              Toast.makeText(getApplicationContext(),"Failed to delete your profile...",Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
          }
        });
      }
    }
  }

}


