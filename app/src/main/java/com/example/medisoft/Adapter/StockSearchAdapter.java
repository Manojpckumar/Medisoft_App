package com.example.medisoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medisoft.Model.AllStock;
import com.example.medisoft.databinding.StockcardBinding;

import java.util.ArrayList;
import java.util.List;

public class StockSearchAdapter extends RecyclerView.Adapter<StockSearchAdapter.ViewHolder> {

    Context context;
    List<AllStock> stockList;

    public StockSearchAdapter(Context context, List<AllStock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StockSearchAdapter.ViewHolder(StockcardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StockSearchAdapter.ViewHolder holder, int position) {

        AllStock allStock=stockList.get(position);
        holder.binding.tvMedName.setText(allStock.getProductName());
        holder.binding.tvMedPrice.setText("Rs "+allStock.getMrp());

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public void updateList(List<AllStock> list){
        stockList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        StockcardBinding binding;


        public ViewHolder(@NonNull StockcardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
