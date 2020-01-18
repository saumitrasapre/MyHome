package com.example.myhome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("staff");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScannerView=new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    @Override
    public void handleResult(final Result result) {

        PreQRScan.resultTextView.setText(result.getText());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(PreQRScan.resultTextView.getText().toString()))
                {
                    PreQRScan.verifiedornot.setText("Verified!");

                    Staff staff=dataSnapshot.child(PreQRScan.resultTextView.getText().toString()).getValue(Staff.class);
                    PreQRScan.nameStaff.setText(staff.getName());
                    PreQRScan.designationStaff.setText(staff.getDesignation());
                    PreQRScan.tickorcross.setImageResource(R.drawable.greentick);
                }
                else
                {
                    PreQRScan.nameStaff.setText(" ");
                    PreQRScan.designationStaff.setText(" ");
                    PreQRScan.tickorcross.setImageResource(R.drawable.redcross);
                    PreQRScan.verifiedornot.setText("Invalid Staff ID...");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onBackPressed();


    }
}
