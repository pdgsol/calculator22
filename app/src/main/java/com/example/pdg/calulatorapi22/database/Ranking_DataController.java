package com.example.pdg.calulatorapi22.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class Ranking_DataController {

    private Ranking_SQLiteHelper ranking_SQLiteHelper;
    private SQLiteDatabase db;
    public Ranking_DataController(Context context)
    {
        ranking_SQLiteHelper = new Ranking_SQLiteHelper(context, DBRankingContract.TABLE_NAME, null, 1);
    }

    public void newPlayerRanking(String userName, String timeStamp, Integer score)
    {
        db = ranking_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            ranking_SQLiteHelper.insertPlayerRanking(db, userName, timeStamp,score);
        }
        db.close();
    }

    public String[][] getRanking()
    {
        String[][] sResult  = null;
        db = ranking_SQLiteHelper.getWritableDatabase();
        if(db != null) {
            sResult = ranking_SQLiteHelper.selectAll(db,DBRankingContract.TABLE_NAME);
        }
        db.close();
        return sResult;
    }

    public void resetRanking()
    {
        db = ranking_SQLiteHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DBRankingContract.TABLE_NAME);
        ranking_SQLiteHelper.onCreate(db);
        db.close();
    }
}
