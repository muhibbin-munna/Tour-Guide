package com.tour.guide.tourguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tour.guide.tourguide.MainActivity;
import com.tour.guide.tourguide.R;
import com.tour.guide.tourguide.database.Country;

import java.util.List;

public class FavCountriesAdapter extends RecyclerView.Adapter<FavCountriesAdapter.FavViewHolder> {

    private Context context;
    private List<Country> list;

    public FavCountriesAdapter(Context context, List<Country> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public FavCountriesAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_view_model, parent, false);
        return new FavCountriesAdapter.FavViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavCountriesAdapter.FavViewHolder holder, int position) {

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

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView numberingTextView, countryOrCityNameTextView;
        CardView rootLayout;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            numberingTextView = itemView.findViewById(R.id.numberId);
            countryOrCityNameTextView = itemView.findViewById(R.id.nameId);
            rootLayout = itemView.findViewById(R.id.favRecyclerViewRootLayoutId);
            rootLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
