package com.tour.guide.tourguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tour.guide.tourguide.CountryDetailsActivity;
import com.tour.guide.tourguide.MainActivity;
import com.tour.guide.tourguide.R;
import com.tour.guide.tourguide.database.Country;
import com.tour.guide.tourguide.database.DatabaseClient;
import com.tour.guide.tourguide.model.CountryInfoModel;

import java.io.Serializable;
import java.util.List;

public class AvailableCountryAndCityRecyclerAdapter extends RecyclerView.Adapter<AvailableCountryAndCityRecyclerAdapter.AvailableCountryAndCityViewHolder> {

    private Context context;
    private List<Country> list;
    private MainActivity mainActivity;

    public AvailableCountryAndCityRecyclerAdapter(Context context, List<Country> list) {
        this.context = context;
        this.list = list;

        if (mainActivity == null) {
            mainActivity = (MainActivity) context;
        }
    }

    @NonNull
    @Override
    public AvailableCountryAndCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_country_and_city_recycler_view_model, parent, false);
        return new AvailableCountryAndCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableCountryAndCityViewHolder holder, int position) {
        holder.numberingTextView.setText(String.valueOf(position + 1));
        if (list.get(position).getType().equalsIgnoreCase("country")){
            holder.countryOrCityNameTextView.setText("Country:- "+list.get(position).getName());
        }else {
            holder.countryOrCityNameTextView.setText("City:- "+list.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class AvailableCountryAndCityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView numberingTextView, countryOrCityNameTextView;
        CardView rootLayout;
        ImageView deleteImageView;

        public AvailableCountryAndCityViewHolder(@NonNull View view) {
            super(view);

            numberingTextView = view.findViewById(R.id.availableCountryAndCityRecyclerViewNumberingTextViewId);
            countryOrCityNameTextView = view.findViewById(R.id.availableCountryAndCityRecyclerViewCountryOrCityNameTextViewId);
            rootLayout = view.findViewById(R.id.availableCountryAndCityRecyclerViewRootLayoutId);
            deleteImageView = view.findViewById(R.id.availableCountryAndCityRecyclerViewDeleteImageViewId);

            rootLayout.setOnClickListener(this);
            deleteImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.availableCountryAndCityRecyclerViewDeleteImageViewId:
                    DeleteDataAsyncTask deleteDataAsyncTask=new DeleteDataAsyncTask();
                    deleteDataAsyncTask.execute();
                    break;

                case R.id.availableCountryAndCityRecyclerViewRootLayoutId:
                    Intent intent = new Intent(context, CountryDetailsActivity.class);
                    intent.putExtra("pos",getAdapterPosition());
                    intent.putExtra("name",list.get(getAdapterPosition()).getName());
                    intent.putExtra("cur",list.get(getAdapterPosition()).getCurrency());
                    intent.putExtra("des",list.get(getAdapterPosition()).getDescription());
                    intent.putExtra("type",list.get(getAdapterPosition()).getType());
                    context.startActivity(intent);
                    break;
            }
        }

        class DeleteDataAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Country country = new Country();
                country.setId(list.get(getAdapterPosition()).getId());
                country.setName(list.get(getAdapterPosition()).getName());
                country.setCurrency(list.get(getAdapterPosition()).getCurrency());
                country.setDescription(list.get(getAdapterPosition()).getDescription());
                country.setType(list.get(getAdapterPosition()).getType());
                DatabaseClient.getInstance(context.getApplicationContext())
                        .getAppDatabase()
                        .countryDao()
                        .delete(country);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mainActivity.list.remove(getAdapterPosition());
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
