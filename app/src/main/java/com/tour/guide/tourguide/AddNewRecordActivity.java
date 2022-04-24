package com.tour.guide.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tour.guide.tourguide.database.Country;
import com.tour.guide.tourguide.database.DatabaseClient;
import com.tour.guide.tourguide.database.MyDatabaseHelper;

public class AddNewRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText,currencyEditText,descriptionEditText;
    private RadioGroup radioGroup;
    private RadioButton countryRadioButton,cityRadioButton;
    private Button saveButton;
    private String name,currency,description,type;
    private SaveDataAsyncTask saveDataAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        initAll();

        checkSelectedRadioButton();

        radioButtonListener();

    }

    private void radioButtonListener() {
        countryRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    type="Country";
                }
            }
        });

        cityRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    type="City";
                }
            }
        });
    }

    private void checkSelectedRadioButton() {
        int selectedRadioButtonId=radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId==R.id.addNewRecordActivityCountryRadioButtonId){
            type="Country";
        }else {
            type="City";
        }
    }

    private void initAll() {
        nameEditText=findViewById(R.id.addNewRecordActivityCountryOrCityNameEditTextId);
        currencyEditText=findViewById(R.id.addNewRecordActivityCurrencyNameEditTextId);
        descriptionEditText=findViewById(R.id.addNewRecordActivityDescriptionEditTextId);
        radioGroup=findViewById(R.id.addNewRecordActivityRadioGroupId);
        countryRadioButton=findViewById(R.id.addNewRecordActivityCountryRadioButtonId);
        cityRadioButton=findViewById(R.id.addNewRecordActivityCityRadioButtonId);
        saveButton=findViewById(R.id.addNewRecordActivitySaveButtonId);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewRecordActivitySaveButtonId:
                startSavingOperation();
                break;
        }
    }

    private void startSavingOperation(){
        name=nameEditText.getText().toString();
        currency=currencyEditText.getText().toString();
        description=descriptionEditText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(currency) || TextUtils.isEmpty(description) || TextUtils.isEmpty(type)){
            Toast.makeText(this, "Please input all field properly and try again.", Toast.LENGTH_SHORT).show();
        }else {
            if (saveDataAsyncTask!=null){
                saveDataAsyncTask.cancel(true);
                saveDataAsyncTask=null;
            }
            saveDataAsyncTask=new SaveDataAsyncTask();
            saveDataAsyncTask.execute();


        }
    }

    class SaveDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MyDatabaseHelper myDatabaseHelper;
            myDatabaseHelper = new MyDatabaseHelper(AddNewRecordActivity.this);
            SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
            myDatabaseHelper.insertData(name, currency, description, type, 0);
            sqLiteDatabase.close();

            Country country = new Country();
            country.setName(name);
            country.setCurrency(currency);
            country.setDescription(description);
            country.setType(type);
            DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .countryDao()
                    .insert(country);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddNewRecordActivity.this, "Successfully added.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (saveDataAsyncTask!=null){
            saveDataAsyncTask.cancel(true);
            saveDataAsyncTask=null;
        }
        super.onDestroy();
    }
}
