package com.example.madlabproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewFoodItem extends AppCompatActivity {

    private static final String TAG = "AddNewFoodItem";

    static ArrayList<String> TOTALFOODS=new ArrayList<String>();
    static ArrayList<String> TOTALCALS=new ArrayList<String>();

    Button camera;
    String currentPhotoPath;
    Uri photoURI;
    String n="",c="";
    static final int REQUEST_TAKE_PHOTO = 1;
    FirebaseVisionImage image;
    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
            .getOnDeviceImageLabeler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food_item);
    }

    public void submit(View v)
    {
        n=((EditText) findViewById(R.id.foodname)).getText().toString();
        try
        {
            c=((EditText) findViewById(R.id.cal)).getText().toString();
            int test=Integer.parseInt(c);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Enter numeric value only for calories", Toast.LENGTH_SHORT).show();
        }
        if(!n.isEmpty()&&!c.isEmpty())
        {FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://yourprojectname.firebaseio.com/");
        DatabaseReference dbref= mDatabase.getReference();
        DatabaseReference myref = dbref.child("foods");
        Food f=new Food(n,c);
        String userId = dbref.push().getKey();
        myref.child(userId).setValue(f);
        //Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Food item added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TOTALFOODS.add(n);
        TOTALCALS.add(c);}
        else
        {
            Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();
        }
    }

    public void openCamera(View v) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        //startActivity(i);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void detect(View v){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getCloudImageLabeler();
        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_SHORT).show();
                String textmax=""; float max=0;
                for (FirebaseVisionImageLabel label : labels){
                    String text=label.getText();
                    //String id=label.getEntityId();
                    float confidence = label.getConfidence();
                    if(confidence>max)
                        textmax=text;

                }
                //Toast.makeText(getApplicationContext(), textmax, Toast.LENGTH_SHORT).show();
                n=textmax;
                EditText fn= ((EditText) findViewById(R.id.foodname));
                fn.setText(textmax);
            }
        });

        //FirebaseVisionCloudImageLabelerOptions options = new FirebaseVisionCloudImageLabelerOptions.Builder()
        //FirebaseVisionImageLabeler firebaseVisionImageLabeler = FirebaseVision.getInstance().getCloudImageLabeler(options);
    }
}
