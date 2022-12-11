package com.example.yopo.util_classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Session;
import com.example.yopo.layout_classes.BusinessLandingPageActivity;
import com.example.yopo.layout_classes.ClientSearchActivity;

import java.util.HashMap;
import java.util.List;

public class BusinessesAdapter extends RecyclerView.Adapter<BusinessesAdapter.ViewHolder> {
    private Context context;
    private final List<HashMap<String, Object>> businesses;

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView business_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.business_name = itemView.findViewById(R.id.card_business_name);
        }
    }

    public BusinessesAdapter(Context context, List<HashMap<String, Object>> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    @NonNull
    @Override
    public BusinessesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_search_card, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CardClick", "Click detected");

                // get session instance
                Session session = Session.getInstance();

                // get list of businesses
                List<HashMap<String, Object>> businesses = (List<HashMap<String, Object>>) session.get_session_attribute("list_of_businesses");

                // get text view
                TextView business_name = view.findViewById(R.id.card_business_name);

                // find the data about the chosen business
                for (int i = 0; i < businesses.size(); ++i) {
                    if (((String) businesses.get(i).get("business_name")).equals(business_name.getText().toString())) {
                        Intent intent = new Intent(context, BusinessLandingPageActivity.class);
                        intent.putExtra("business_username", (String) businesses.get(i).get("username"));
                        context.startActivity(intent);
                    }
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, Object> business = businesses.get(position);
        holder.business_name.setText((String) business.get("business_name"));
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }
}
