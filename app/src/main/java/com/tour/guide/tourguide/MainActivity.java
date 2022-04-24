package com.tour.guide.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tour.guide.tourguide.adapter.AvailableCountryAndCityRecyclerAdapter;
import com.tour.guide.tourguide.database.Country;
import com.tour.guide.tourguide.database.DatabaseClient;
import com.tour.guide.tourguide.model.CountryInfoModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addNewRecordButton,favButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AvailableCountryAndCityRecyclerAdapter adapter;
    public List<Country> list=new ArrayList<>();
    private GetDataAsyncTask getDataAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        layoutManager=new LinearLayoutManager(MainActivity.this);
        adapter=new AvailableCountryAndCityRecyclerAdapter(MainActivity.this,list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initAll() {
        addNewRecordButton=findViewById(R.id.mainActivityAdNewRecordButtonId);
        favButton=findViewById(R.id.favButtonId);

        recyclerView=findViewById(R.id.mainActivityAvailableCountryAndCityRecyclerViewId);

        addNewRecordButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.mainActivityAdNewRecordButtonId:
                intent=new Intent(MainActivity.this,AddNewRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.favButtonId:
                Intent intent1;
                intent1=new Intent(MainActivity.this,FavoritesActivity.class);
                startActivity(intent1);
        }
    }

    class GetDataAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                List<Country> temporaryList=DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .countryDao()
                        .getAll();
                list.clear();
                list.addAll(temporaryList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (adapter!=null){
                    adapter.notifyDataSetChanged();
                }
                if (list.size()<=0){
                    Toast.makeText(MainActivity.this, "Data not added yet.", Toast.LENGTH_SHORT).show();
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
