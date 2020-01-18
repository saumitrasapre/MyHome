package com.example.myhome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.concurrent.Executor;

public class GetDeviceTokenService extends FirebaseMessagingService {

  @Override
  public void onNewToken(String s) {
    super.onNewToken(s);
    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener((Executor) this, new OnSuccessListener<InstanceIdResult>() {
      @Override
      public void onSuccess(InstanceIdResult instanceIdResult) {
        String deviceToken=instanceIdResult.getToken();
        Log.d("DEVICE TOKEN",deviceToken);


      }
    });

    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
      @Override
      public void onComplete(@NonNull Task<InstanceIdResult> task) {
        if(!task.isSuccessful())
        {
          Log.i("DEVICE TOKEN","Task Failed");
        }
        else
        {
          Log.i("DEVICE TOKEN","The result "+ task.getResult().getToken());
        }
      }
    });

  }



}
