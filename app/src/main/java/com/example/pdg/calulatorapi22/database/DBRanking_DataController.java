package com.example.pdg.calulatorapi22.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DBRanking_DataController {

    private DB_SQLiteHelper db_SQLiteHelper;
    private SQLiteDatabase db;
    public DBRanking_DataController(Context context)
    {
        db_SQLiteHelper = new DB_SQLiteHelper(context, DBRankingContract.TABLE_NAME, null, 1);
    }

    public void newPlayerRanking(String userName, String timeStamp, Integer score)
    {
        db = db_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            db_SQLiteHelper.insertPlayerRanking(db, userName, timeStamp,score);
        }
        db.close();
    }

    public void newUser(String userName, String password)
    {
        db = db_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            db_SQLiteHelper.insertUser(db, userName, password);
        }
        db.close();
    }


    public String[][] getRanking()
    {
        String[][] sResult  = null;
        db = db_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            sResult = db_SQLiteHelper.selectAllRanking(db,DBRankingContract.TABLE_NAME);
        }
        db.close();
        return sResult;
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

    public void resetRanking()
    {
        db = db_SQLiteHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBRankingContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBUsersContract.TABLE_NAME);
        db_SQLiteHelper.onCreate(db);
        db.close();
    }

    public void resetUsers()
    {
        db = db_SQLiteHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBUsersContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBRankingContract.TABLE_NAME);
        db_SQLiteHelper.onCreate(db);
        db.close();
    }
}
