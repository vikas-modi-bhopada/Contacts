package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String select = "CREATE TABLE "+Params.TABLE_NAME+"("+
                Params.KEY_ID+" INTEGER PRIMARY KEY, "+Params.KEY_NAME+
                " TEXT, "+Params.KEY_PHONE+" TEXT"+")";
        db.execSQL(select);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addContact(Contact contact)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME,contact.getName());
        values.put(Params.KEY_PHONE,contact.getPhoneNumber());
        db.insert(Params.TABLE_NAME,null,values);
        db.close();
    }
    public List<Contact> getAllContacts()
    {
        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return  contactList;
    }

    public int totalContacts()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    public void delete(String number)
    {

        SQLiteDatabase db= getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_PHONE+"=?",new String[]{number});


    }
}
