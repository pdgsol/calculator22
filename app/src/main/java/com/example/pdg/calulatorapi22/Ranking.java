package com.example.pdg.calulatorapi22;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pdg.calulatorapi22.database.Ranking_DataController;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

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
            players.add(new Player(i%1, result[i][1], result[i][0]));
        }

//        players.add(new Player(0, "2222222", "Alvaro"));
//        players.add(new Player(1, "4444222", "Rolvaro"));
//        players.add(new Player(0, "2111222", "Varoal"));

        return players;
    }

}
