package com.example.medisoft.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medisoft.Model.PurchaseOrder;
import com.example.medisoft.Utils.RecyclerViewClickInterface;
import com.example.medisoft.databinding.PurchaseorderlistBinding;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderListAdapter extends RecyclerView.Adapter<PurchaseOrderListAdapter.MyViewHolder> {

    Context context;
    List<PurchaseOrder> purchaseOrderList;
    RecyclerViewClickInterface recyclerViewClickInterface;
    boolean isOnTextChanged = false;
    double ExpenseFinalTotal = 0.00;
    ArrayList<Double> ExpAmtArray = new ArrayList<Double>();
    public PurchaseOrderListAdapter(Context context, List<PurchaseOrder> purchaseOrderList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.purchaseOrderList = purchaseOrderList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override

    public PurchaseOrderListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PurchaseOrderListAdapter.MyViewHolder(PurchaseorderlistBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOrderListAdapter.MyViewHolder holder, int position) {

        PurchaseOrder purchaseOrder = purchaseOrderList.get(position);
        holder.binding.itemDesc.setText(purchaseOrder.getItemDescription());
        holder.binding.tvPack.setText(purchaseOrder.getPack());
        holder.binding.tvSupplier.setText(purchaseOrder.getSupplierName());
        holder.binding.tvStock.setText(purchaseOrder.getStock());
        holder.binding.tvManufacturer.setText(purchaseOrder.getCompanyName());
        holder.binding.tvSale.setText(purchaseOrder.getSalesQuantity());
        holder.binding.tvRate.setText(purchaseOrder.getUnitRate());
        holder.binding.quantityAc.setText(purchaseOrder.getQuantity());
        holder.binding.slNo.setText(String.valueOf(holder.getAdapterPosition() + 1) + " .");


    }

    public void setItems(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrderList = purchaseOrders;
    }

    @Override
    public int getItemCount() {
        return purchaseOrderList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        PurchaseorderlistBinding binding;


        public MyViewHolder(@NonNull PurchaseorderlistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.addQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {

                        int stock= Integer.parseInt(purchaseOrderList.get(getAdapterPosition()).getStock());
                        int quantity1=Integer.parseInt(s.toString());

                        if(quantity1>stock){

                            binding.addQuantity.setError("Stock Limit Exceeds");

                        }
                        else{
                            ExpenseFinalTotal = 0;
                            Double prate = Double.valueOf(purchaseOrderList.get(getAdapterPosition()).getUnitRate());
                            int pack = Integer.parseInt(purchaseOrderList.get(getAdapterPosition()).getPack());
                            int quantity = Integer.parseInt(String.valueOf(s));
                            Double sum1 = (double) quantity / pack;
                            Double sum2 = sum1 * prate;
                            binding.addQuantitytest.setText(sum2.toString());
                            binding.quantityAc.setText(s);

                        }

                    }

                    catch (NumberFormatException e){
                        binding.addQuantitytest.setText("0.00");

                    }

                }
            });

            binding.addQuantitytest.addTextChangedListener(new TextWatcher() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    isOnTextChanged = true;

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    ExpenseFinalTotal = 0;
                    if (isOnTextChanged) {
                        isOnTextChanged = false;

                        try {


                            ExpenseFinalTotal = 0;


                            for (int i = 0; i <= getAdapterPosition(); i++) {

                                int inposition1 = getAdapterPosition();
                                if (i != getAdapterPosition()) {
                                    //store 0  where user select position in not equal/
                                    ExpAmtArray.add(0.00);

                                }else {

                                    // store user entered value to Array list (ExpAmtArray) at particular position
                                    ExpAmtArray.add(0.00);
                                    ExpAmtArray.set(inposition1, Double.valueOf(s.toString()));

                                    break;
                                }

                            }

                            // for statement to loop to the array, to calculate the Expense total.
                            for (int i = 0; i <= ExpAmtArray.size() - 1; i++) {

                                double tempTotalExpenase = Double.parseDouble(String.valueOf(ExpAmtArray.get(i)));
                                ExpenseFinalTotal  = ExpenseFinalTotal + tempTotalExpenase;

                            }

                            recyclerViewClickInterface.onItemClick(getAdapterPosition(),String.valueOf(ExpenseFinalTotal));




                        }catch (NumberFormatException e)
                        {
                            // catch is used because, when used enter value in editText and remove the value it
                            // it will trigger NumberFormatException, so to prevent it and remove data value from array ExpAmtArray
                            //then
                            // re-perform loop total expense calculation and display the total.

                            ExpenseFinalTotal = 0;
                            for (int i = 0; i <= getAdapterPosition(); i++) {
                                Log.d("TimesRemoved", " : " + i);
                                int newposition = getAdapterPosition();
                                if (i == newposition) {
                                    ExpAmtArray.set(newposition,0.00);

                                }

                            }
                            for (int i = 0; i <= ExpAmtArray.size() - 1; i++) {

                                int tempTotalExpenase = Integer.parseInt(String.valueOf(ExpAmtArray.get(i)));
                                ExpenseFinalTotal  = ExpenseFinalTotal + tempTotalExpenase;

                            }
                            recyclerViewClickInterface.onItemClick(getAdapterPosition(),String.valueOf(ExpenseFinalTotal));

                        }

                    }


                }
            });
        }
    }
}