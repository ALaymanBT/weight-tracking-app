package com.cs360_project_alayman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.viewmodel.WeightViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {

    Context context;
    List<Weight> weightList;
    WeightViewModel weightViewModel;

    public WeightAdapter(Context context, WeightViewModel weightViewModel) {
        this.context = context;
        this.weightViewModel = weightViewModel;
    }
    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_entry, parent, false);
        return new WeightViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        Weight weight = weightList.get(position);
        holder.txtWeight.setText(String.format("%.1f", weight.getWeight()));
        holder.btnDelete.setOnClickListener((v) -> {
            weightViewModel.deleteWeight(weight);
            weightList.remove(weight);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        if (weightList != null) {
            return weightList.size();
        }
        return -1;
    }

    public void setWeightList(List<Weight> weightList) {
        this.weightList = weightList;
        notifyDataSetChanged();
    }

    public static class WeightViewHolder extends RecyclerView.ViewHolder {

        TextView txtWeight;
        ImageButton btnDelete;

        //FIXME: Should this be a date object?
        TextView txtDate;

        public WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWeight = itemView.findViewById(R.id.entry_list_weight);
            btnDelete = itemView.findViewById(R.id.entry_list_button_delete);
            //FIXME: Add onClickListener to edit entries
        }
    }
}


