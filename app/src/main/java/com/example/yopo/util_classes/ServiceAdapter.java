package com.example.yopo.util_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private Context context;
    private List<Service> services;

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView price;
        public final TextView service;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            service = itemView.findViewById(R.id.service);
        }
    }

    public ServiceAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }


    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_list_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        Service service = services.get(position);
        holder.service.setText("Service: " + service.getService());
        holder.price.setText("Price: " + service.getPrice());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
