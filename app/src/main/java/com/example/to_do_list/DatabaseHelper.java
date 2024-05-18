package com.example.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "myDatabase", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table MY_TABLE(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,msg TEXT,date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table  if exists  MY_TABLE");

    }

    public void InsertedData(String title,String msg,String date){

       SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("msg",msg);
        values.put("date",date);
        sqLiteDatabase.insert("MY_TABLE",null,values);




    }


    public Cursor GetallData(){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * FROM MY_TABLE ORDER BY id DESC; ",null);

        return  cursor;


    }


    public void updateData(String title,String msg,int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("msg", msg);
        database.update("MY_TABLE",values,"id = ?",new String[]{String.valueOf(id)});

    }


    public Cursor searchAllData(String msg) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM MY_TABLE WHERE msg LIKE '%" + msg + "%'", null);

        return cursor;
    }

    public void Delete(int id){
        SQLiteDatabase database=this.getWritableDatabase();
        database.execSQL("delete from MY_TABLE where id like "+id);

    }











}
