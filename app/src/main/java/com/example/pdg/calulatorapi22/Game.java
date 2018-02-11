package com.example.pdg.calulatorapi22;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pdg.calulatorapi22.database.Ranking_DataController;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements View.OnClickListener{

    boolean gameOngoing = false;
    Integer maxNumFlippedCards = 2;
    Integer waitSecsToFlipCard = 2;
    int incScore = 10;
    int decScore = -5;
    Integer score = 0;
    Activity gameActivity;

    CardGame cardGame;
    List<Integer> gameFlippedCardsList;
    List<Integer> turnFlippedCardsList;

    private void setUpGame()
    {
        gameFlippedCardsList = new ArrayList<>();
        turnFlippedCardsList = new ArrayList<>();
        cardGame = new CardGame();
        cardGame.buildCardGame();
        List<Pair<Integer, Integer>> imageViewList = cardGame.getCardGameList();
        for(int i = 0; i < imageViewList.size(); ++i)
        {
            findViewById(imageViewList.get(i).second).setOnClickListener(this);
        }

        gameOngoing = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameActivity = this;
        setUpGame();

        Button checkResult = findViewById(R.id.restartGame);
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
        if (gameOngoing  && !alreadyFlipped(view.getId())) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(cardGame.getDrawableIdFromImageViewId(imageView.getId()));

            turnFlippedCardsList.add(imageView.getId());

            if (turnFlippedCardsList.size() == maxNumFlippedCards) {
                gameOngoing = false;

                if(cardGame.checkFlippedCards(turnFlippedCardsList)) {
                    gameOngoing = true;
                    updateScore(incScore);
                    gameFlippedCardsList.addAll(turnFlippedCardsList);
                    turnFlippedCardsList.clear();
                } else {
                    BackgroundCount bc = new BackgroundCount();
                    bc.execute(waitSecsToFlipCard);
                }
            }
        }

//        if(gameFlippedCardsList.size() == totalNumCardsInGame) {
            finishGame();
//        }
    }

    private boolean alreadyFlipped(int imageViewId) {
        return gameFlippedCardsList.contains(imageViewId) || turnFlippedCardsList.contains(imageViewId);
    }

    private void hideCardsInList(List<Integer> cardsList)
    {
        for(int i = 0; i < cardsList.size(); ++i) {
            ImageView card = findViewById(cardsList.get(i));
            card.setImageResource(R.drawable.cardback);
        }
    }

    private void hideFlippedCards()
    {
        hideCardsInList(turnFlippedCardsList);
        gameOngoing = false;
        turnFlippedCardsList.clear();
    }

    private void updateScore(int incrementScore){
        TextView textView = findViewById(R.id.scoreScreen);
        score +=incrementScore;
        textView.setText("Score : " + score.toString());
    }

    private void restartGame()
    {
        score = 0;
        TextView textView = findViewById(R.id.scoreScreen);
        textView.setText("Score : " + score.toString());
        hideFlippedCards();
        hideCardsInList(gameFlippedCardsList);
        gameFlippedCardsList.clear();
        setUpGame();
    }

    private class BackgroundCount extends AsyncTask<Integer, String, Integer>{

        Integer acummulation = 0;

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            updateScore(decScore);
            hideFlippedCards();
            gameOngoing = true;
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
        showDialog();
        //Intent intent = new Intent(view.getContext(), Ranking.class);
        //startActivity(intent);
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final Ranking_DataController  rankingDataController = new Ranking_DataController(this);
        // Set up the input
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT); // | InputType.TrankingDataControllerYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userName = input.getText().toString();
                rankingDataController.newPlayerRanking(userName, "1000", score );
                Intent intent = new Intent(gameActivity, Ranking.class);
                gameActivity.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
