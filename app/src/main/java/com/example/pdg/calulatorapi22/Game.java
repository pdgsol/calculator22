package com.example.pdg.calulatorapi22;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements View.OnClickListener{

    boolean gameOngoing = true;
    Integer numFlippedCardsInTurn = 0;
    Integer maxNumFlippedCards = 2;
    Integer waitSecsToFlipCard = 2;
    int incScore = 10;
    int decScore = -5;
    Integer score = 0;
    int numCards = 4;
    String m_Text = "";
    int nullId = 0xFFFF;

    List<Integer> flippedCardsList = new ArrayList<Integer>();

    List<Pair<Integer, ImageView>> imageViewList = new ArrayList<Pair<Integer, ImageView>>();

    private void setUpImageViews()
    {
        addViewImageList(R.drawable.images, R.id.card11);
        addViewImageList(R.drawable.images, R.id.card12);
        addViewImageList(R.drawable.images, R.id.card21);
        addViewImageList(R.drawable.images, R.id.card22);

        for(int i = 0; i < imageViewList.size(); ++i)
        {
            imageViewList.get(i).second.setOnClickListener(this);
        }

    }

    private void addViewImageList(int imageId, int imageViewId)
    {
        imageViewList.add(Pair.create(imageId, (ImageView) findViewById(imageViewId)));
    }

    private int getDrawableIdFromImageViewId(int imageViewId)
    {
        int drawableId = nullId;
        ImageView imageView;
        for(int i = 0; i < imageViewList.size(); ++i)
        {
            if( imageViewList.get(i).second.getId() == imageViewId )
            {
                drawableId = imageViewList.get(i).first;
                break;
            }
        }

        return drawableId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setUpImageViews();

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
            imageView.setImageResource(getDrawableIdFromImageViewId(view.getId()));

            ++numFlippedCardsInTurn;
            flippedCardsList.add((Integer) imageView.getId());

            if (numFlippedCardsInTurn == maxNumFlippedCards) {
                gameOngoing = false;

                if(checkFlippedCards()) {
                    gameOngoing = true;
                    updateScore(incScore);
                } else {
                    BackgroundCount bc = new BackgroundCount();
                    bc.execute(waitSecsToFlipCard);
                }

                numFlippedCardsInTurn = 0;
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
        boolean flippedCardsMatch= true;

        if(!flippedCardsList.isEmpty()) {
            int imageId_1 = getDrawableIdFromImageViewId(flippedCardsList.get(0));
            int imageId_2 = 0;
            for (int i = 1; i < flippedCardsList.size() && flippedCardsMatch; ++i){
                imageId_2 = getDrawableIdFromImageViewId(flippedCardsList.get(i));
                if( imageId_1 != imageId_2) {
                    flippedCardsMatch = false;
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
        numFlippedCardsInTurn = 0;
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
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
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
