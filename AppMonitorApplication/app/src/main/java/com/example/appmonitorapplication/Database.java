package com.example.appmonitorapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "USER_INFO.db";
    public static final String TABLE_NAME = "signup_table";
    public static final String COL_2 = "Username";
    public static final String COL_1 = "ID";
    public static final String COL_3 = "Password";
    public static final String COL_4 = "Name";
    public static final String COL_5 = "Email";
    public static final String COL_6 = "Phone";
    public static final String COL_7 = "Age";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1 );
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
      //  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS AppList");
        System.out.println(" In database creation start");
        db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Password TEXT, Name TEXT, Email TEXT, Phone TEXT, Age INTEGER )");
        db.execSQL("create table AppList (ID INTEGER PRIMARY KEY AUTOINCREMENT, AppName TEXT UNIQUE , PackageName TEXT )");
        System.out.println(" In database creation end");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS AppList");
        onCreate(db);

    }

    public boolean insertData(String Username,String Password,String Name, String Email,String Phone,Integer Age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Username);
        contentValues.put(COL_3,Password);
        contentValues.put(COL_4,Name);
        contentValues.put(COL_5,Email);
        contentValues.put(COL_6,Phone);
        contentValues.put(COL_7,Age);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    String searchPass(String Username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Username, Password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);

                if(a.equals(Username))
                {
                    b = cursor.getString(1);
                    break;
                }

            }
            while(cursor.moveToNext());
        }
        return b;
    }

    public boolean populateListInDb(List<AppInformation> appList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long result=0;
        for(AppInformation app: appList) {
            contentValues.put("AppName", app.getAppName());
            contentValues.put("PackageName", app.getPackageName());

            try {
                result = db.insert("AppList", null, contentValues);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (result == -1)
            return false;
        else
            return true;
    }

    public List<AppInformation> getAppsFromDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select AppName, PackageName from AppList";
        Cursor cursor = db.rawQuery(query, null);
        String appName="", packageName="";
        List<AppInformation> appList = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                appName = cursor.getString(0);
                packageName = cursor.getString(1);
                appList.add(new AppInformation(appName,packageName));



            }
            while(cursor.moveToNext());
        }
        return appList;
    }


}
