package com.example.lostandfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final List<Item> itemList;
    private final Context context;
    private final RowClickListener listener;

    public interface RowClickListener{
        void onItemClick(int id);
    }
    public ItemAdapter(List<Item> itemList, Context context, RowClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new ItemViewHolder(itemView,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.item_one.setText(itemList.get(position).getLost()+":"+itemList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item_one;
        public RowClickListener onRowClickListener;
        public ItemViewHolder(@NonNull View itemView,RowClickListener listener) {
            super(itemView);
            this.onRowClickListener = listener;
            itemView.setOnClickListener(this);
            item_one = itemView.findViewById(R.id.item_one);
        }
        @Override
        public void onClick(View view) {
            onRowClickListener.onItemClick(itemList.get(getAdapterPosition()).getId());
        }
    }

}