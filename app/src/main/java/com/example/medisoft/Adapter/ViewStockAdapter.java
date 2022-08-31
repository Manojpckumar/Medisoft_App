package com.example.medisoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medisoft.Model.AllStock;
import com.example.medisoft.Model.Stock;
import com.example.medisoft.R;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.ViewStock.ViewStockActivity;
import com.example.medisoft.databinding.PurchaseorderlistBinding;
import com.example.medisoft.databinding.StockcardBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewStockAdapter extends RecyclerView.Adapter<ViewStockAdapter.ViewHolder> implements Filterable {

    Context context;
    List<AllStock> productList = new ArrayList<>();
    private List<AllStock> productFiltered = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;
    StockcardBinding binding;

    public ViewStockAdapter(Context context, List<AllStock> productList) {
        this.context = context;
        this.productList = productList;
    }

    public ViewStockAdapter() {

    }


    public void setMovieList(Context context, final List<AllStock> movieList) {
        this.context = context;
        if (this.productList == null) {
            this.productList = movieList;
            this.productFiltered = movieList;

            notifyItemChanged(0, productFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ViewStockAdapter.this.productList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ViewStockAdapter.this.productList.get(oldItemPosition).getProductName() == productList.get(newItemPosition).getProductName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    AllStock newMovie = ViewStockAdapter.this.productList.get(oldItemPosition);

                    AllStock oldMovie = productList.get(newItemPosition);

                    return newMovie.getProductName() == oldMovie.getProductName();
                }
            });
            this.productList = movieList;
            this.productFiltered = movieList;
            result.dispatchUpdatesTo(this);
        }
    }
//    copy close

    @NonNull
    @Override
    public ViewStockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(StockcardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewStockAdapter.ViewHolder holder, int position) {

        holder.binding.tvMedName.setText(productFiltered.get(position).getProductName());
        holder.binding.tvMedPrice.setText( "₹ " + productFiltered.get(position).getMrp());

        if(!productFiltered.get(position).getStockId().equalsIgnoreCase(""))
        {

            holder.binding.tvMedStatus.setTextColor(context.getResources().getColor(R.color.green));
        }
        else
        {
            holder.binding.tvMedStatus.setTextColor(context.getResources().getColor(R.color.red));
        }



    }

    @Override
    public int getItemCount() {
        if(productList != null){
            return productFiltered.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productFiltered = productList;

                } else {
                    List<AllStock> filteredList = new ArrayList<>();
                    for (AllStock movie : productList) {
                        if (movie.getProductName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    productFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productFiltered = (ArrayList<AllStock>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        StockcardBinding binding;

        public ViewHolder(@NonNull StockcardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
