package com.example.lostandfound.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lostandfound.Model.Item;
import com.example.lostandfound.NewAdvertActivity;
import com.example.lostandfound.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME,null, Util.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE "+Util.TABLE_NAME+"("+Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.LOST + " TEXT," +Util.NAME+ " TEXT," +Util.PHONE+ " TEXT," +Util.DESCRIPTION+ " TEXT," +Util.DATE+ " TEXT," +Util.LOCATION+ " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.LOST,item.getLost());
        contentValues.put(Util.DESCRIPTION,item.getDescription());
        contentValues.put(Util.DATE,item.getDate());
        contentValues.put(Util.LOCATION,item.getLocation());
        contentValues.put(Util.NAME,item.getName());
        contentValues.put(Util.PHONE,item.getPhone());
        long newRow = database.insert(Util.TABLE_NAME,null,contentValues);
        return newRow;
    }
    public Item fetchItems(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Item item = new Item();

        Cursor cursor = database.rawQuery("SELECT * FROM " + Util.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(0)==id){
                    item.setId(id);
                    item.setLost(cursor.getString(1));
                    item.setName(cursor.getString(2));
                    item.setPhone(cursor.getString(3));
                    item.setDescription(cursor.getString(4));
                    item.setDate(cursor.getString(5));
                    item.setLocation(cursor.getString(6));
                    return item;
                }
            }while (cursor.moveToNext());
        }
        return item;
    }
    public void deleteItem(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(Util.TABLE_NAME,Util.ITEM_ID+"="+id,null);
    }
    public List<Item> fetchAllItems(){
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cur = database.rawQuery("SELECT * FROM " + Util.TABLE_NAME,null);
        if(cur.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(cur.getInt(0));
                item.setLost(cur.getString(1));
                item.setName(cur.getString(2));
                item.setPhone(cur.getString(3));
                item.setDescription(cur.getString(4));
                item.setDate(cur.getString(5));
                item.setLocation(cur.getString(6));
                itemList.add(item);
            }
            while (cur.moveToNext());
        }
        return itemList;
    }
}
