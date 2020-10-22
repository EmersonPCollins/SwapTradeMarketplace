package com.example.myfirstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoodsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleText;
    private EditText dateText;
    private EditText locationText;
    private EditText descriptionText;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        Spinner spinner = findViewById(R.id.categoriesSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        titleText = findViewById(R.id.titleText);
        dateText = findViewById(R.id.dateText);
        locationText = findViewById(R.id.locationText);
        descriptionText = findViewById(R.id.descriptionText);
        errorMessage = (TextView) findViewById(R.id.errorMessageTextView);

    }

    // Currently this saves the text of the selected category
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Basic error handling below, refactoring needed here, in the test and in UI
    private boolean validateTitle(){
        String titleInput = titleText.getText().toString().trim();
        if(titleInput.isEmpty()) {
            errorMessage.setText("Error: Enter a valid title.");
            return false;
        }
    return true;
    }

    private boolean validateLocation(){
        String locationInput = locationText.getText().toString().trim();
        if(locationInput.isEmpty()) {
            errorMessage.setText("Error: Enter a location.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateDate(){
        String dateInput = dateText.getText().toString().trim();
        boolean goodDate = goodDate(dateInput);
        if(!goodDate) {
            errorMessage.setText("Error: Enter a valid date.");
            return false;
        }
        return true;
    }

    private boolean validateDescription(){
        String descriptionInput = descriptionText.getText().toString().trim();
        if(descriptionInput.isEmpty()) {
            errorMessage.setText("Error: Enter a description.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSubmit(View view) {
        errorMessage.setText(null);
        validateTitle();
        validateLocation();
        validateDate();
        validateDescription();

        if(validateTitle() && validateLocation() && validateDate() && validateDescription()) {
            //insertGood(title.getText().toString(), date.getText().toString(), "email@email.com");
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

    public void insertGood(String title, String date, String user_email){
        Good good = new Good(title, date, user_email);
        //get the db connection
        DatabaseService db = new DatabaseService();
        db.writeGood(good);
    }



    /**
     *
     * to be examined
     *
     *
     *     @RequiresApi(api = Build.VERSION_CODES.O)
     *     public void validate() throws ParseException {
     *         EditText title = (EditText) findViewById(R.id.titleText);
     *         EditText description = (EditText) findViewById(R.id.descriptionText);
     *         EditText date = (EditText) findViewById(R.id.dateText);
     *         EditText location = (EditText) findViewById(R.id.locationText);
     *         TextView errorMessage = (TextView) findViewById(R.id.errorMessageTextView);
     *         if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && goodDate(date.getText().toString())){
     *             //ADD TO DATABASE
     *             //replace "email@email.com" with the logged in user email
     *             insertGood(title.getText().toString(), date.getText().toString(), "email@email.com");
     *
     *         }else{
     *             if(title.getText().toString().isEmpty()){
     *                 errorMessage.setText("Error: Enter a valid title.");
     *             }else{
     *                 if(description.getText().toString().isEmpty()){
     *                     errorMessage.setText("Error: Enter a description.");
     *                 }else{
     *                     if(!goodDate(date.getText().toString())){
     *                         errorMessage.setText("Error: Enter a valid date.");
     *                     }else{
     *                         if(location.getText().toString().equals("")){
     *                             errorMessage.setText("Error: Enter a location.");
     *                         }
     *                     }
     *                 }
     *             }
     *         }
     *     }
     *
     *     //populate after merging the goods db and pass to it the firebase db used in the sign in
     *     public void insertGood(String title, String date, String user_email){
     *         Good good = new Good(title, date, user_email);
     *         //get the db connection
     *         DatabaseService db = new DatabaseService();
     *         db.writeGood(good);
     *     }
     *
     *     //METHODS I USE TO CHECK THE DATE ARE VALID REQUIRE THIS API PART
     *     @RequiresApi(api = Build.VERSION_CODES.O)
     *     public boolean goodDate(String sdate) throws ParseException {
     *         if(sdate.equals("")){
     *             return false;
     *         }
     *         LocalDate d1 = java.time.LocalDate.now();
     *         DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
     *         LocalDate date = LocalDate.parse(sdate,f);
     *         if(!sdate.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")){
     *             return false;
     *         }
     *         if(date.isBefore(d1)){
     *             return false;
     *         }
     *         return true;
     *     }
     * **/
}
