package com.example.medisoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medisoft.Model.OrderByClient;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.databinding.PurchaseorderlistBinding;
import com.example.medisoft.databinding.StockcardBinding;
import com.example.medisoft.databinding.ViewplacedordersBinding;

import java.util.List;

public class ViewPlacedOrdersAdapter extends RecyclerView.Adapter<ViewPlacedOrdersAdapter.MyViewHolder> {


    Context context;
    List<OrderByClient> orderByClientList;
    RecyclerViewClickInterface recyclerViewClickInterface;


    public ViewPlacedOrdersAdapter(Context context, List<OrderByClient> orderByClientList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.orderByClientList = orderByClientList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    public ViewPlacedOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(ViewplacedordersBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewPlacedOrdersAdapter.MyViewHolder holder, int position) {

        OrderByClient orderByClient=orderByClientList.get(position);
        holder.binding.OrderNO.setText(orderByClient.getOrderNo());

    }

    @Override
    public int getItemCount() {
        return orderByClientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewplacedordersBinding binding;

        public MyViewHolder(@NonNull ViewplacedordersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewHolderpdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"view");
                }
            });

            binding.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"download");
                }
            });
        }
    }
}
