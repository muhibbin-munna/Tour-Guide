package com.tour.guide.tourguide.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient databaseClient;
    private CountryDatabse countryDatabse;


    private DatabaseClient(Context context) {
        this.context = context;
        countryDatabse = Room.databaseBuilder(context, CountryDatabse.class, "CountryDatabase").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public CountryDatabse getAppDatabase() {
        return countryDatabse;
    }
}
