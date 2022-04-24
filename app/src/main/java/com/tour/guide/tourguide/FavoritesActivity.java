package com.tour.guide.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.tour.guide.tourguide.adapter.AvailableCountryAndCityRecyclerAdapter;
import com.tour.guide.tourguide.adapter.FavCountriesAdapter;
import com.tour.guide.tourguide.database.Country;
import com.tour.guide.tourguide.database.DatabaseClient;
import com.tour.guide.tourguide.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FavCountriesAdapter adapter;
    public List<Country> list=new ArrayList<>();
    private FavoritesActivity.GetDataAsyncTask getDataAsyncTask;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initAll();

        setUpRecyclerView();

    }
    private void setUpRecyclerView() {
        layoutManager=new LinearLayoutManager(FavoritesActivity.this);
        adapter=new FavCountriesAdapter(FavoritesActivity.this,list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initAll() {
        recyclerView=findViewById(R.id.mainActivityAvailableCountryAndCityRecyclerViewId);
        myDatabaseHelper = new MyDatabaseHelper(FavoritesActivity.this);
    }

    class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
            Cursor cursor = myDatabaseHelper.read_all_favorites();
            if (cursor.getCount() == 0) {
                cursor.close();
                sqLiteDatabase.close();
            }
            else {
                try {
                    list.clear();
                    while (cursor.moveToNext()) {
                        Country country = new Country(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                        list.add(country);
                    }
                } finally {
                    if (!cursor.isClosed())
                        cursor.close();
                    sqLiteDatabase.close();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (adapter!=null){
                adapter.notifyDataSetChanged();
            }
            if (list.size()<=0){
                Toast.makeText(FavoritesActivity.this, "Data not added yet.", Toast.LENGTH_SHORT).show();
            }
        }
}
    @Override
    protected void onResume() {
        super.onResume();
        if (getDataAsyncTask!=null){
            getDataAsyncTask.cancel(true);
            getDataAsyncTask=null;
        }
        getDataAsyncTask=new GetDataAsyncTask();
        getDataAsyncTask.execute();
    }
}