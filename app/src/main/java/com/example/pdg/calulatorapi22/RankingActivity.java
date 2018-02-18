package com.example.pdg.calulatorapi22;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pdg.calulatorapi22.database.DBRankingContract;
import com.example.pdg.calulatorapi22.database.DBRanking_DataController;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private List<Player> players;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private mAdapter playerAdapter;
    private DBRanking_DataController db_Ranking_DataController;
    private Activity mRankingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setupRecyclerView();
        loadRanking();
        mRankingActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRanking);
        setSupportActionBar(toolbar);
        //FloatingActionButton nextActionButton = findViewById(R.id.nextActionButton);

        registerForContextMenu(recyclerView);
    }

    void setupRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        playerAdapter = initAdapter();
        recyclerView.setAdapter(playerAdapter);

    }

    private mAdapter initAdapter() {
        return new mAdapter(this, loadRanking());
    }
    List<Player> loadRanking() {

        final DBRanking_DataController db_Ranking_DataController = new DBRanking_DataController(this);
        String[][] result = db_Ranking_DataController.getRanking();
        players = new ArrayList<Player>();

        for(int i = 0; i < result.length; ++i)
        {
            players.add(new Player(i%1, result[i][1], result[i][0], result[i][2]));
        }

        return players;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.call:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.user_session), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_session), "");
            editor.apply();

            Intent intent = new Intent(mRankingActivity, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
