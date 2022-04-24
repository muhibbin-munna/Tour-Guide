package com.tour.guide.tourguide.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM countryname")
    List<Country> getAll();

    @Insert
    void insert(Country country);

    @Delete
    void delete(Country country);


}
