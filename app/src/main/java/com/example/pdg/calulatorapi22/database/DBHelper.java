package com.example.pdg.calulatorapi22.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

/**
 * Created by PDG on 02/02/2018.
 */

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String DATABASE_NAME = "A";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper instance;

    public static DBHelper getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DBHelper(context);
        }
        return  instance;
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

    public ArrayList<String> getAllRegisteredUsers(Context context) {
        Cursor cursor;
        SQLiteDatabase db = getReadableDatabase();
        cursor = db.rawQuery("SELECT DISTINCT " + DataBasePuntuacionesContract.COLUMN_USERNAME + " FROM " + DataBasePuntuacionesContract.TABLE_NAME, null);
        ArrayList<String> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex(DataBasePuntuacionesContract.COLUMN_USERNAME)));
            } while(cursor.moveToNext());
        }

        db.close();
        return result;
    }
}
