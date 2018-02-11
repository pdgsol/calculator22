package com.example.pdg.calulatorapi22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.pdg.calulatorapi22.database.Ranking_DataController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup DB
        final Ranking_DataController ranking_DataController = new Ranking_DataController(this);
        ranking_DataController.resetRanking();
        ranking_DataController.newPlayerRanking("ABC", "1000", 1);
        ranking_DataController.newPlayerRanking("ABC", "1000", 1);

        //ranking_DataController.newPlayerRanking("DEF", "2000", 2);
        //ranking_DataController.newPlayerRanking("GHI", "3000", 3);


        Button startCalc = (Button) findViewById(R.id.startCalc);
        Button startGame = (Button) findViewById(R.id.startGame);
        Button rankingGame = (Button) findViewById(R.id.rankingGame);

        startCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CalculatorActivity.class);
                startActivity(intent);
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Game.class);
                startActivity(intent);
            }
        });

        rankingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Ranking.class);
                startActivity(intent);
            }
        });

    }

}
