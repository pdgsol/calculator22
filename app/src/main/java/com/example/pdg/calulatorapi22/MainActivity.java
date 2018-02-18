package com.example.pdg.calulatorapi22;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Activity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.user_session), Context.MODE_PRIVATE);
        TextView userNameTextView = findViewById(R.id.userNameMainActivity);
        userNameTextView.setText(sharedPref.getString(getString(R.string.user_session), "Error!"));

        Button startCalc = findViewById(R.id.startCalc);
        Button startGame = findViewById(R.id.startGame);
        Button rankingGame = findViewById(R.id.rankingGame);
        Button mediaPlayer = findViewById(R.id.mediaPlayer);

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

        mediaPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

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

            Intent intent = new Intent(mMainActivity, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
