package com.satturi.android.activitytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ActivityTracker.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table profile (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT,AGE TEXT,GENDER TEXT,HEIGHT TEXT, BEGININGWEIGHT TEXT,GOALWEIGHT TEXT,GOALDATE TEXT)");
        db.execSQL("create table Weight (ID INTEGER PRIMARY KEY AUTOINCREMENT,WEIGHT_DATE TEXT,CURRENT_WEIGHT TEXT,PICTURE BLOB)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public boolean insertProfile(String Name,String Age, String GENDER,String HEIGHT,String BEGININGWEIGHT, String GOALWEIGHT, String GOALDATE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("AGE",Age);
        contentValues.put("GENDER",GENDER);
        contentValues.put("HEIGHT",HEIGHT);
        contentValues.put("BEGININGWEIGHT",BEGININGWEIGHT);
        contentValues.put("GOALWEIGHT",GOALWEIGHT);
        contentValues.put("GOALDATE",GOALDATE);
        long result = db.insert("profile",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean updateProfile( String Name,String Age,String GENDER,String Height,String BWeight,String Gweight,String GDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("AGE",Age);
        contentValues.put("GENDER",GENDER);
        contentValues.put("HEIGHT",Height);
        contentValues.put("BEGININGWEIGHT",BWeight);
        contentValues.put("GOALWEIGHT",Gweight);
        contentValues.put("GOALDATE",GDate);
        db.update("profile", contentValues, "ID=1", null);
        return true;
    }
    public Cursor getProfile()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from profile where id=1", null);
        return res;
    }
    public boolean insertweight(String date, String weight, byte[] pic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("WEIGHT_DATE",date);
        contentValues.put("CURRENT_WEIGHT",weight);
        contentValues.put("PICTURE",pic);
        long result = db.insert("Weight",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllWeights()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from Weight", null);
        return res;
    }

    public Boolean deleteWeight(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Weight","id="+id,null);
        return true;
    }

}
