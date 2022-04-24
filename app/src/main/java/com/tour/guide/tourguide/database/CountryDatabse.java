package com.tour.guide.tourguide.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class}, version = 1)
public abstract class CountryDatabse extends RoomDatabase {
    public abstract CountryDao countryDao();
}
