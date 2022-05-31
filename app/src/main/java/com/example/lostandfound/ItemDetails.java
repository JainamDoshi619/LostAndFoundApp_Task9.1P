package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lostandfound.Data.DatabaseHelper;
import com.example.lostandfound.Model.Item;

public class ItemDetails extends AppCompatActivity {
    TextView itemName,itemPhone,itemDesc,itemLocation,itemDate;
    int id;
    DatabaseHelper databaseHelper;
    Item item;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        itemName = findViewById(R.id.itemName);
        itemDate = findViewById(R.id.itemDate);
        itemDesc = findViewById(R.id.itemDesc);
        itemLocation = findViewById(R.id.itemLocation);
        itemPhone = findViewById(R.id.itemPhone);

        Intent i = getIntent();
        id = i.getIntExtra("item id",0);

        databaseHelper = new DatabaseHelper(this);
        item = databaseHelper.fetchItems(id);

        itemName.setText(item.getName());
        itemPhone.setText(item.getPhone());
        itemDesc.setText(item.getDescription());
        itemDate.setText(item.getDate());
        itemLocation.setText(item.getLocation());

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteItem(id);
                Intent i = new Intent(getApplicationContext(),ShowItems.class);
                startActivity(i);
                finish();
            }
        });

    }
}