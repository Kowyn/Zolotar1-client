package com.example.renovikov.zolotarclient1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by renovikov on 21.02.2018.
 */

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "objectsDB";
    public static final String TABLE_NAME = "objects";
    public static final String OBJ_NAME = "name";
    public static final String  OBJ_DISTRICT= "district";
    public static final String OBJ_ADRESS = "adress";
    public static final String OBJ_RANGE = "range";
    public static final String OBJ_VOLUME = "volume";

    public DBhelper(Context context) {
        // конструктор суперкласса
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ("
                + "id integer primary key autoincrement,"
                + OBJ_NAME+" text,"
                + OBJ_DISTRICT+" text,"
                + OBJ_ADRESS+" text,"
                + OBJ_RANGE+" numeric,"
                + OBJ_VOLUME+" numeric"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

