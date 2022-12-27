package com.example.yopo.util_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;

import java.util.List;

public class OpenRangeAdapter extends RecyclerView.Adapter<OpenRangeAdapter.ViewHolder> {
    private final Context context;
    private final List<OpenRange> ranges;

    public OpenRangeAdapter(Context context, List<OpenRange> ranges) {
        this.context = context;
        this.ranges = ranges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_range_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Create arrayAdapter for the list of hours
        ArrayAdapter<CharSequence> start_adapter = ArrayAdapter.createFromResource(context,
                R.array.list_of_hours, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> end_adapter = ArrayAdapter.createFromResource(context,
                R.array.list_of_hours, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        start_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for both spinners
        holder.start_hour.setAdapter(start_adapter);
        holder.end_hour.setAdapter(end_adapter);

        // set starting position
        holder.start_hour.setSelection(this.ranges.get(position).getStart_hour());
        holder.end_hour.setSelection(this.ranges.get(position).getEnd_hour());

        // Set on selection event listener for the starting time
        holder.start_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When an hour is chosen. set the starting hour in the proper
                // OpenRange instance to be that index
                ranges.get(holder.getAdapterPosition()).setStart_hour(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.end_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When an hour is chosen. set the ending hour in the proper
                // OpenRange instance to be that index
                ranges.get(holder.getAdapterPosition()).setEnd_hour(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return ranges.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Spinner start_hour, end_hour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initiate spinners
            start_hour = itemView.findViewById(R.id.start_hour);
            end_hour = itemView.findViewById(R.id.end_hour);

        }
    }
}
