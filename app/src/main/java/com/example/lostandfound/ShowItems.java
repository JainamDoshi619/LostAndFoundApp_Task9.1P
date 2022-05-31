package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.lostandfound.Data.DatabaseHelper;
import com.example.lostandfound.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity implements ItemAdapter.RowClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
        RecyclerView showItemsRV = findViewById(R.id.showItemsRV);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Item> itemList = databaseHelper.fetchAllItems();
        ItemAdapter a = new ItemAdapter(itemList,ShowItems.this, this);
        RecyclerView.LayoutManager vertical =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        showItemsRV.setAdapter(a);
        showItemsRV.setLayoutManager(vertical);

    }

    @Override
    public void onItemClick(int id) {
        Intent i = new Intent(getApplicationContext(),ItemDetails.class);
        i.putExtra("item id",id);
        startActivity(i);
    }
}
