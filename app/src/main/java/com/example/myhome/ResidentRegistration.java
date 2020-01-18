package com.example.myhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResidentRegistration extends AppCompatActivity {

  EditText name,phone,flatno,numofmembers;
  Button registerResident;
  DatabaseReference reference;
  Member member;
  long maxID=0;
  FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_resident_registration);
    member=new Member();
    name=(EditText)findViewById(R.id.nameResident);
    phone=(EditText)findViewById(R.id.phoneResident);
    flatno=(EditText)findViewById(R.id.flatNo);
    numofmembers=(EditText)findViewById(R.id.members);
    registerResident=(Button)findViewById(R.id.residentRegister);
    reference= FirebaseDatabase.getInstance().getReference().child("member");
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists())
        {
          maxID=(dataSnapshot.getChildrenCount());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

    registerResident.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String resname=name.getText().toString();
        Long phNo=Long.parseLong(phone.getText().toString().trim());
        String flatNo=flatno.getText().toString().trim();
        int numMembers =Integer.parseInt(numofmembers.getText().toString().trim());

        member.setName(resname);
        member.setPhone(phNo);
        member.setFlatno(flatNo);
        member.setNumMember(numMembers);
        member.setEmail(user.getEmail());
        member.setRegistered(true);
        member.setUID(user.getUid());
        reference.child(user.getUid()).setValue(member);
        Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_SHORT).show();
        finish();

      }
    });
  }
}
