package com.cs360_project_alayman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360_project_alayman.data.entities.Weight;

import java.util.ArrayList;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {

    Context context;
    ArrayList<Weight> weightArrayList;

    public WeightAdapter(Context context, ArrayList<Weight> weightArrayList) {
        this.context = context;
        this.weightArrayList = weightArrayList;
    }
    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_entry, parent, false);
        return new WeightViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        Weight weight = weightArrayList.get(position);
        holder.txtWeight.setText(String.format("%.1f", weight.getWeight()));
    }

    @Override
    public int getItemCount() {
        return weightArrayList.size();
    }

    public static class WeightViewHolder extends RecyclerView.ViewHolder {

        TextView txtWeight;

        //FIXME: Should this be a date object?
        TextView txtDate;

        public WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWeight = itemView.findViewById(R.id.entry_list_weight);
            //FIXME: Add onClickListener to edit entries
        }
    }
}


