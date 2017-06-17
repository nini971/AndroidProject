package com.example.nico.cityfinder.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nico.cityfinder.R;
import com.example.nico.cityfinder.model.Result;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    //------------------
    //  ATTRIBUT
    //------------------

    private ArrayList<Result> listCity;
    private OnCityListener onCityListener;

    //------------------
    //  CONSTRUCTOR
    //------------------

    public CityAdapter(ArrayList<Result> listCity, OnCityListener onCityListener) {
        this.listCity = listCity;
        this.onCityListener = onCityListener;
    }

    //------------------
    //  INNER CLASS
    //------------------

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView row_tv_cityName, row_tv_cp;
        private View root;

        public ViewHolder(View itemView) {
            super(itemView);
            row_tv_cityName = (TextView) itemView.findViewById(R.id.tv_cityName);
            row_tv_cp = (TextView) itemView.findViewById(R.id.tv_cp);
            root = itemView.findViewById(R.id.root);
        }


    }

    //------------------
    //  OVERRRIDE
    //------------------

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_city, parent, false);
        return new CityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result city = listCity.get(position);
        holder.row_tv_cityName.setText(city.getVille());
        holder.row_tv_cp.setText(city.getCp());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityListener.onCityClick(city);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }

    //------------------
    //  INTERFACE
    //------------------
    public interface OnCityListener {
        void onCityClick(Result city);
    }


}
