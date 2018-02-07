package com.example.pdg.calulatorapi22;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements View.OnClickListener{

    boolean gameOngoing = true;
    Integer numFlippedCards = 0;
    Integer maxNumFlippedCards = 2;
    Integer waitSecsToFlipCard = 2;
    int incScore = 10;
    int decScore = -5;
    Integer score = 0;
    int numCards = 4;

    List<Integer> flippedCardsList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ImageView card11 = (ImageView) findViewById(R.id.card11);
        ImageView card12 = (ImageView) findViewById(R.id.card12);
        ImageView card21 = (ImageView) findViewById(R.id.card21);
        ImageView card22 = (ImageView) findViewById(R.id.card22);

        card11.setOnClickListener(this);
        card12.setOnClickListener(this);
        card21.setOnClickListener(this);
        card22.setOnClickListener(this);

        Button checkResult = (Button) findViewById(R.id.restartGame);

        checkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    @Override
    public void onClick(View view) {

        //Log.i("GameActivity",  "onClick");
        if (gameOngoing  && !alreadyFlipped(view)) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(R.drawable.images);

            ++numFlippedCards;
            flippedCardsList.add((Integer) imageView.getId());

            if (numFlippedCards == maxNumFlippedCards) {
                gameOngoing = false;

                if(checkFlippedCards()) {
                    gameOngoing = true;
                    updateScore(incScore);
                } else {
                    BackgroundCount bc = new BackgroundCount();
                    bc.execute(waitSecsToFlipCard);
                }
            }
        }

        if(flippedCardsList.size() == numCards) {
            finishGame();
        }
    }

    private boolean alreadyFlipped(View view) {
        ImageView imageView = (ImageView) view;
        return flippedCardsList.contains(view.getId());
    }

    private boolean checkFlippedCards()
    {
        boolean flippedCardsMatch = true;

        if(!flippedCardsList.isEmpty()) {
            int imageId_1 = 0; //TODO
            int imageId_2 = 0;
            ImageView card = (ImageView) findViewById(flippedCardsList.get(0));
            int i = 1;
            while (i < flippedCardsList.size() && flippedCardsMatch){
                card = (ImageView) findViewById(flippedCardsList.get(i));
                //TODO imageId_2 = ((Integer) card.getTag()).intValue();

                if( imageId_1 != imageId_2) {
                    flippedCardsMatch = false;
                } else {
                    ++i;
                }
            }
        }

        return flippedCardsMatch;
    }

    private void hideFlippedCards()
    {
        for(int i = 0; i < flippedCardsList.size(); ++i) {
            ImageView card = (ImageView) findViewById(flippedCardsList.get(i));
            card.setImageResource(R.drawable.cardbacksmall);
        }

        gameOngoing = false;
        numFlippedCards = 0;
        flippedCardsList.clear();
    }

    private void updateScore(int incrementScore){
        TextView textView = (TextView) findViewById(R.id.scoreScreen);
        score +=incrementScore;
        textView.setText("Score : " + score.toString());
    }

    private void restartGame()
    {
        score = 0;
        TextView textView = (TextView) findViewById(R.id.scoreScreen);
        textView.setText("Score : " + score.toString());
        hideFlippedCards();
        gameOngoing = true;
    }

    private class BackgroundCount extends AsyncTask<Integer, String, Integer>{

        Integer acummulation = 0;

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            updateScore(decScore);
            hideFlippedCards();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int i = integers[0];
            Integer acummulation = 0;
            while (i > acummulation)
            {
                acummulation +=i;
                publishProgress("Secs "  + String.valueOf(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return i;
        }
    }

    private void finishGame() {
        TextView textView = (TextView) findViewById(R.id.scoreScreen);
        textView.setText("Final Score : " + score.toString());
    }


}
