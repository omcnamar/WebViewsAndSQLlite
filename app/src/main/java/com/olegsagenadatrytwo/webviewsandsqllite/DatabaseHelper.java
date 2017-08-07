package com.olegsagenadatrytwo.webviewsandsqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by omcna on 8/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Contacts";
    public static final String CONTACT_NAME = "Name";
    public static final String CONTACT_NUMBER = "Number";
    public static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                CONTACT_NAME + " TEXT," +
                CONTACT_NUMBER + " TEXT PRIMARY KEY" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveNewContact(MyContact myContact)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME, myContact.getName());
        contentValues.put(CONTACT_NUMBER, myContact.getNumber());
        long saved = database.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "saveNewContact: ");
        return saved;
    }

    public ArrayList<MyContact> getContacts(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<MyContact> listOfContacts = new ArrayList<>();

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                MyContact myContact = new MyContact(cursor.getString(0), cursor.getString(1));
                listOfContacts.add(myContact);
            }while(cursor.moveToNext());
        }
        return listOfContacts;
    }
}
