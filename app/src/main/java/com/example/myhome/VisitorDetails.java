package com.example.myhome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VisitorDetails extends AppCompatActivity {

  EditText visitorName,visitorPhone;
  ListView flatNos;
  ImageView myImageView;
  Bitmap capturedPhoto;
  Uri mImageUri;
  private ProgressDialog pd;
  String vName;
  Long vPhone;
  String uploadID="Visitor";

  private StorageReference mStorageRef;
  private DatabaseReference mDatabaseRef;
  private FirebaseAuth mAuth;
  ArrayList<String > flats=new ArrayList<>();
  private String URL="https://fcm.googleapis.com/fcm/send";
  private RequestQueue mRequestQueue;
  String flNo;
  static final int REQUEST_IMAGE_CAPTURE=2;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_visitor_details);

    visitorName=(EditText)findViewById(R.id.nameVisitor);
    visitorPhone=(EditText)findViewById(R.id.phoneVisitor);
    flatNos=(ListView)findViewById(R.id.flatNoList);
    myImageView=(ImageView)findViewById(R.id.visitorImage);
    mRequestQueue= Volley.newRequestQueue(this);
    mAuth=FirebaseAuth.getInstance();
    mStorageRef= FirebaseStorage.getInstance().getReference("uploads"); //Save it in a folder called uploads
    mDatabaseRef=FirebaseDatabase.getInstance().getReference().child("visitor");


    pd=new ProgressDialog(this);
    pd.setMessage("Loading...");
    pd.setCancelable(true);
    pd.setCanceledOnTouchOutside(false);




    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("member");
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        collectFlatNos((Map<String,Object>)dataSnapshot.getValue());

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

    ListAdapter flatListAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,flats);
    flatNos.setAdapter(flatListAdapter);

    flatNos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        vName=visitorName.getText().toString();
        vPhone=Long.parseLong(visitorPhone.getText().toString());
        flNo=adapterView.getItemAtPosition(position).toString();

        DatabaseReference refer=FirebaseDatabase.getInstance().getReference().child("member");
        refer.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot: dataSnapshot.getChildren())
            {
              Member member=snapshot.getValue(Member.class);
                if(member.getFlatno()==flNo)
                {
                uploadID=member.getUID();

              }
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
        });


        Intent myIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myIntent,REQUEST_IMAGE_CAPTURE);//We assign the id(which we created earlier) to our intent


        //finish();



      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK)
    {
      final Bundle extras=data.getExtras();
      capturedPhoto=(Bitmap)extras.get("data");

      myImageView.setImageBitmap(capturedPhoto);
      mImageUri=getImageUri(this,capturedPhoto);
      pd.show();
      uploadFile();



    }
  }
  private  String getFileExtension(Uri uri)
  {
    ContentResolver cr=getContentResolver();
    MimeTypeMap mime=MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cr.getType(uri));
  }


  private void uploadFile() {
    if(mImageUri!=null)
    {
      final StorageReference filereference=mStorageRef.child("Visitor "+flNo+" "+System.currentTimeMillis()+"."+getFileExtension(mImageUri));
      filereference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

          Toast.makeText(VisitorDetails.this,"Upload Successful",Toast.LENGTH_SHORT).show();
          filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
              Upload upload=new Upload(visitorName.getText().toString(),Long.parseLong(visitorPhone.getText().toString()),"Visitor "+flNo,uri.toString());
              mDatabaseRef.child(uploadID).setValue(upload);
              pd.dismiss();
              sendNotification(vName,vPhone,flNo);
              finish();

            }
          });


        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(VisitorDetails.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
      }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


        }
      });
    }
    else
    {
      Toast.makeText(this,"No image generated",Toast.LENGTH_SHORT).show();
    }
  }


  private Uri getImageUri(Context context,Bitmap inImage)
  {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    inImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
    String path=MediaStore.Images.Media.insertImage(context.getContentResolver(),inImage,"Title",null);
    return Uri.parse(path);
  }

  private void collectFlatNos(Map<String, Object> users) {

    for(Map.Entry<String,Object>entry : users.entrySet())
    {
      Map singleUser=(Map)entry.getValue();
      flats.add((String)singleUser.get("flatno"));
    }
  }

  private void sendNotification(String vName, Long vNum, String flNo) {

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
      mainObj.put("to","/topics/"+"news"+flNo);
      JSONObject notificationObj=new JSONObject();
      notificationObj.put("title","New Visitor");
      notificationObj.put("body","You have a new visitor named "+vName+" having phone number "+ String.valueOf(vNum));
      notificationObj.put("click_action",".VisitorQuery");
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
