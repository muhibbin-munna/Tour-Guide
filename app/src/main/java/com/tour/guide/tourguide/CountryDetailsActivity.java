package com.tour.guide.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tour.guide.tourguide.database.MyDatabaseHelper;

public class CountryDetailsActivity extends AppCompatActivity {

    int pos;
    String name,cur,des,type;
    TextView countryOrCityNameTextView,currencyNameTextView,descriptionTextView;
    Button favButton;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        countryOrCityNameTextView = findViewById(R.id.countryOrCityNameTextView);
        currencyNameTextView = findViewById(R.id.currencyNameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        favButton = findViewById(R.id.favCountryDetailsButtonId);
        myDatabaseHelper = new MyDatabaseHelper(CountryDetailsActivity.this);

        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", 0);
        name = intent.getStringExtra("name");
        cur = intent.getStringExtra("cur");
        des = intent.getStringExtra("des");
        type = intent.getStringExtra("type");

        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        Cursor cursor = myDatabaseHelper.read_all_favorites();
        if (cursor.getCount() == 0) {
            favButton.setText("Add To Favorites");
            cursor.close();
            sqLiteDatabase.close();
        } else {
            try {
                while (cursor.moveToNext()) {
                    String nameA = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COUNTRY_NAME));
                    if (nameA.equals(name)) {

                        favButton.setText("Delete From Favorites");
                        cursor.close();
                    } else {

                        favButton.setText("Add To Favorites");
                        cursor.close();
                    }
                }
            } finally {
                if (!cursor.isClosed())
                    cursor.close();
                sqLiteDatabase.close();
            }
        }

        countryOrCityNameTextView.setText(name);
        currencyNameTextView.setText(cur);
        descriptionTextView.setText(des);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper;
                myDatabaseHelper = new MyDatabaseHelper(CountryDetailsActivity.this);
                SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = myDatabaseHelper.read_all_data(name);
                if (cursor.getCount() == 0) {
                    myDatabaseHelper.insertData(name, cur, des, type,1);
                    favButton.setText("Delete From Favorites");
                    cursor.close();
                    sqLiteDatabase.close();
                } else {
                    try {
                        while (cursor.moveToNext()) {
                            int favoritestatus = cursor.getInt(cursor.getColumnIndex(MyDatabaseHelper.FAVORITE));
                            if (favoritestatus == 1) {
                                myDatabaseHelper.updateData(name, cur, des,type, 0);
                                favButton.setText("Add To Favorites");
                                cursor.close();
                            } else {
                                myDatabaseHelper.updateData(name, cur, des,type, 1);
                                favButton.setText("Delete From Favorites");
                                cursor.close();
                            }
                        }
                    } finally {
                        if (!cursor.isClosed())
                            cursor.close();
                        sqLiteDatabase.close();
                    }
                }
            }
        });

    }
}