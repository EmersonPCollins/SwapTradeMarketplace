package com.example.myfirstapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.service.DatabaseService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GoodsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleText;
    private EditText dateText;
    private EditText locationText;
    private EditText descriptionText;
    private TextView errorMessage;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView goodImage;
    private String imageURL;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        storageReference = FirebaseStorage.getInstance().getReference();

        Button button = (Button) findViewById(R.id.ImageUploadButton);
        goodImage = (ImageView) findViewById(R.id.good_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    System.out.println(MediaStore.Images.ImageColumns.DATA);
                }catch (Exception e){
                    System.out.println("error -> " + e.toString());
                }
            }
        });

        Spinner spinner = findViewById(R.id.categoriesSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        titleText = findViewById(R.id.titleText);
        dateText = findViewById(R.id.dateText);
        locationText = findViewById(R.id.locationText);
        descriptionText = findViewById(R.id.descriptionText);
        errorMessage = findViewById(R.id.errorMessageTextView);

    }

    // Currently this saves the text of the selected category
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            goodImage.setImageBitmap(imageBitmap);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpeg";

            uploadImageToFirebase(imageFileName, imageBitmap);
        }

    }

    private void uploadImageToFirebase(String imageFileName, Bitmap imageBitmap) {
        final StorageReference imageReference = storageReference.child("images/" + imageFileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads
                //imageReference.getDownloadUrl(););
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageURL = uri.toString();
                    }
                });
            }
        });
    }

    private boolean validateTitle(String titleInput){
        if(titleInput.isEmpty()) {
            errorMessage.setText("Error: Enter a valid title.");
            return false;
        }
        return true;
    }

    private boolean validateLocation(String locationInput){
        if(locationInput.isEmpty()) {
            errorMessage.setText("Error: Enter a location.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateDate(String dateInput){
        boolean goodDate = goodDate(dateInput);
        if(!goodDate) {
            errorMessage.setText("Error: Enter a valid date.");
            return false;
        }
        return true;
    }

    private boolean validateDescription(String descriptionInput){
        if(descriptionInput.isEmpty()) {
            errorMessage.setText("Error: Enter a description.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSubmit(View view) {
        errorMessage.setText(null);
        String titleInput = titleText.getText().toString().trim();
        String endDate = dateText.getText().toString().trim();
        String descriptionInput = descriptionText.getText().toString().trim();
        String locationInput = locationText.getText().toString().trim();
        LocalDate startDate = LocalDate.now();

        if(validateTitle(titleInput) && validateLocation(locationInput) && validateDate(endDate) && validateDescription(descriptionInput)) {
            insertGood(titleInput, startDate.toString(), endDate, descriptionInput, locationInput, "email@example.com", "url", "type");
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean goodDate(String sdate) {
        if(sdate.isEmpty()){
            return false;
        }
        if(!sdate.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")){
            return false;
        }

        return true;
    }

    public void insertGood(String title, String startDate, String endDate, String description, String location, String email, String imageURL, String type){
        Good good = new Good(title, startDate, endDate, description, location, email, imageURL, type);
        DatabaseService db = new DatabaseService(FirebaseDatabase.getInstance());
        db.writeGood(good);

        returnToHomePage();
    }

    private void returnToHomePage() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

}
