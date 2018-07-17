package com.krishna.medicinereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Rminder extends SQLiteOpenHelper {
    public Rminder(Context context) {
        super(context,"USERDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE MEDICINE (Name text,Date text,Time text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MEDICINE");
        onCreate(db);

    }
    public  void addData(String name_r,String date_r,String time_r)
    {
        ContentValues contentValues =  new ContentValues();
        contentValues.put("Name",name_r);
        contentValues.put("Date",date_r);
        contentValues.put("Time",time_r);


        SQLiteDatabase db =  getWritableDatabase();
        db.insert("MEDICINE",null,contentValues);
    }

    public Cursor getdata()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =  db.query("MEDICINE",null,null,null,null,null,null);
        return cursor;
    }

    public void ondelete(String name )
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] arr =  {name};
        db.delete("MEDICINE","Name=?",arr);
    }

}
