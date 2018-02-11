package com.example.pdg.calulatorapi22;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pdg.calulatorapi22.database.Ranking_DataController;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private List<Player> players;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private mAdapter playerAdapter;
    private Ranking_DataController ranking_DataController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setupRecyclerView();
        loadRanking();
    }

    void setupRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        playerAdapter = initAdapter();
        recyclerView.setAdapter(playerAdapter);


    }

    private mAdapter initAdapter() {
        return new mAdapter(this, loadRanking());
    }
    List<Player> loadRanking() {

        final Ranking_DataController ranking_DataController = new Ranking_DataController(this);
        String[][] result = ranking_DataController.getRanking();

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

}
