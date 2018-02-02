package com.example.pdg.calulatorapi22.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;

/**
 * Created by PDG on 02/02/2018.
 */

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_POINTS = "CREATE TABLE" + DataBasePuntuacionesContract.TABLE_NAME + "(" +
                DataBasePuntuacionesContract.COLUMN_USERNAME + "text PRIMERA KEY," +
                DataBasePuntuacionesContract.COLUMN_POINTS + " int ," +
                DataBasePuntuacionesContract.COLUMN_TIMESTAMP + " text )";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_POINTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
