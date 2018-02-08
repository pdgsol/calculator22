package com.example.pdg.calulatorapi22;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

    private List<Player> contactos;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private mAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setupRecyclerView();
        loadContactos();

    }

    void setupRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        contactsAdapter = initAdapter();
        recyclerView.setAdapter(contactsAdapter);
    }

    private mAdapter initAdapter() {
        return new mAdapter(this, loadContactos());
    }
    List<Player> loadContactos() {
        contactos = new ArrayList<Player>();

        contactos.add(new Player(0, "2222222", "Alvaro"));
        contactos.add(new Player(1, "4444222", "Rolvaro"));
        contactos.add(new Player(0, "2111222", "Varoal"));

        return contactos;
    }

}
