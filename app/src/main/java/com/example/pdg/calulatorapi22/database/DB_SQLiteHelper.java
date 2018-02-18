package com.example.pdg.calulatorapi22.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DB_SQLiteHelper extends SQLiteOpenHelper {
    String sqlCreateRanking = "CREATE TABLE " + DBRankingContract.TABLE_NAME + " (" + DBRankingContract.COLUMN_USERNAME + " TEXT, "
            + DBRankingContract.COLUMN_TIMESTAMP + " TEXT, " + DBRankingContract.COLUMN_SCORE + " INTEGER)";

    String sqlCreateUsers = "CREATE TABLE " + DBUsersContract.TABLE_NAME + " (" + DBRankingContract.COLUMN_USERNAME + " TEXT PRIMARY KEY, "
            + DBUsersContract.COLUMN_PASSWORD + " TEXT)";

    public DB_SQLiteHelper(Context contexto, String nombre,
                           CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if(this.getDatabaseName().equals(DBUsersContract.TABLE_NAME)) {
            db.execSQL(sqlCreateUsers);
        }

        if(this.getDatabaseName().equals(DBRankingContract.TABLE_NAME)) {
            db.execSQL(sqlCreateRanking);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
                          int versionNueva) {

        db.execSQL("DROP TABLE IF EXISTS " + DBRankingContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBUsersContract.TABLE_NAME);
        db.execSQL(sqlCreateRanking);
        db.execSQL(sqlCreateUsers);
    }


    public void insertPlayerRanking(SQLiteDatabase db, String username, String timeStamp, Integer score )
    {
        ContentValues newInsert = new ContentValues();
        newInsert.put(DBRankingContract.COLUMN_USERNAME, username);
        newInsert.put(DBRankingContract.COLUMN_TIMESTAMP,timeStamp);
        newInsert.put(DBRankingContract.COLUMN_SCORE,score);
        db.insert(DBRankingContract.TABLE_NAME,null,newInsert);
    }

    public void insertUser(SQLiteDatabase db, String username, String password)
    {
        ContentValues newInsert = new ContentValues();
        newInsert.put(DBUsersContract.COLUMN_USERNAME, username);
        newInsert.put(DBUsersContract.COLUMN_PASSWORD,password);
        db.insert(DBUsersContract.TABLE_NAME,null,newInsert);
    }


    public String[][] selectAllRanking(SQLiteDatabase db, String nameTable)
    {
        Cursor c = db.rawQuery(" SELECT * FROM " + nameTable + " ORDER BY score ASC", null);

        String[][] sResult = new String[c.getCount()][4];
        if (c.moveToFirst()) {
            int i = 0;
            do {
                sResult[i][0] = c.getString(0);
                sResult[i][1] = c.getString(1);
                sResult[i][2] = c.getString(2);
                //sResult[i][3] = c.getString(3);
                ++i;
            } while(c.moveToNext());
        }
        c.close();
        return sResult;
    }

    public String[] selectAllUsers(SQLiteDatabase db, String nameTable)
    {
        Cursor c = db.rawQuery(" SELECT * FROM " + nameTable, null);

        String[] sResult = new String[c.getCount()];
        if (c.moveToFirst()) {
            int i = 0;
            do {
                sResult[i] = c.getString(0) + ":" + c.getString(1);
                ++i;
            } while(c.moveToNext());
        }
        c.close();
        return sResult;
    }

    public void resetRanking(SQLiteDatabase db)
    {
        db.delete(DBRankingContract.TABLE_NAME, null, null);
    }


}