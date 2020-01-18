package com.example.myhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PreQRScan extends AppCompatActivity {

    public static TextView resultTextView,verifiedornot,nameStaff,designationStaff;
    public  static ImageView tickorcross;
    Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_qrscan);

        resultTextView=(TextView)findViewById(R.id.result_text);
        verifiedornot=(TextView)findViewById(R.id.verifiedornot);
        nameStaff=(TextView)findViewById(R.id.NameStaff);
        designationStaff=(TextView)findViewById(R.id.DesignationStaff);
        scan_btn=(Button)findViewById(R.id.btn_scan);
        tickorcross=(ImageView)findViewById(R.id.tickorcross);


        scan_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),QRScan.class));

            }
        });
    }
}

