package com.example.pdg.calulatorapi22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_session), Context.MODE_PRIVATE);

        TextView userNameTextView = findViewById(R.id.userNameMainActivity);
        userNameTextView.setText(sharedPref.getString(getString(R.string.user_session), "Error!"));

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
                Intent intent = new Intent(view.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });

        rankingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RankingActivity.class);
                startActivity(intent);
            }
        });

    }

}
