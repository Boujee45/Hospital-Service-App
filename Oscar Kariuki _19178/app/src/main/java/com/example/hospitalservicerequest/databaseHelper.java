package com.example.hospitalservicerequest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HospitalApp.db";
    private static final int DATABASE_VERSION = 3;

    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_REQUESTS = "requests";

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users Table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, email TEXT UNIQUE, password TEXT)");

        // Requests Table
        db.execSQL("CREATE TABLE " + TABLE_REQUESTS + " (req_id INTEGER PRIMARY KEY AUTOINCREMENT, service_name TEXT, ward_number TEXT, bed_number TEXT, notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUESTS);
        onCreate(db);
    }

    // --- AUTH METHODS ---
    public boolean registerUser(String user, String mail, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("username", user);
        v.put("email", mail);
        v.put("password", String.valueOf(pass.hashCode()));
        return db.insert(TABLE_USERS, null, v) != -1;
    }

    public boolean checkUser(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username=? AND password=?",
                new String[]{user, String.valueOf(pass.hashCode())});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }




    // --- REQUEST METHODS ---
    public boolean insertRequest(String service, String ward, String bed, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("service_name", service);
        v.put("ward_number", ward);
        v.put("bed_number", bed);
        v.put("notes", notes);
        return db.insert(TABLE_REQUESTS, null, v) != -1;
    }

    public Cursor getAllRequests() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_REQUESTS, null);
    }

    public boolean deleteRequest(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REQUESTS, "req_id=?", new String[]{String.valueOf(id)}) > 0;
    }

    // --- USER MANAGEMENT ---
    public Cursor getAllUsers() {
        return this.getReadableDatabase().rawQuery("SELECT username, email FROM " + TABLE_USERS, null);
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERS, "username=?", new String[]{username}) > 0;
    }
}