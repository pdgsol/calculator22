package com.example.pdg.calulatorapi22.database;

/**
 * Created by PDG on 17/02/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBUsers_DataController {

    private DB_SQLiteHelper db_SQLiteHelper;
    private SQLiteDatabase db;
    public DBUsers_DataController(Context context)
    {
        db_SQLiteHelper = new DB_SQLiteHelper(context, DBUsersContract.TABLE_NAME, null, 1);
    }

    public void newUser(String userName, String password)
    {
        db = db_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            db_SQLiteHelper.insertUser(db, userName, password);
        }
        db.close();
    }



    public String[] getUsers()
    {
        String[] sResult  = null;
        db = db_SQLiteHelper.getWritableDatabase();
        if(db != null) {
           sResult = db_SQLiteHelper.selectAllUsers(db,DBUsersContract.TABLE_NAME);
        }
        db.close();
        return sResult;
    }


    public void resetUsers()
    {
        db = db_SQLiteHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBUsersContract.TABLE_NAME);
        db_SQLiteHelper.onCreate(db);
        db.close();
    }
}
