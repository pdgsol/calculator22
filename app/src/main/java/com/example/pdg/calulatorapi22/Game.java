package com.example.pdg.calulatorapi22;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity implements View.OnClickListener{

    boolean startHideCardProcess = false;
    Integer numFlippedCards = 0;
    Integer maxNumFlippedCards = 2;

    Integer score = 0;

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

    }

    @Override
    public void onClick(View view) {

        if (!startHideCardProcess) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(R.drawable.images);

            ++numFlippedCards;
            flippedCardsList.add((Integer) imageView.getId());

            if (numFlippedCards > maxNumFlippedCards) {
                startHideCardProcess = true;
                hideFlippedCards();
            }
        }
    }

    boolean checkFlippedCards()
    {
        boolean flippedCardsMatch = false;

        if(!flippedCardsList.isEmpty()) {
            int imageId_1 = 0;
            int imageId_2 = 0;
            ImageView card = (ImageView) findViewById(flippedCardsList.get(0));
            int i = 1;
            while (i < flippedCardsList.size()){
                card = (ImageView) findViewById(flippedCardsList.get(i));
                imageId_2 = ((Integer) card.getTag()).intValue();

                ++i;
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
        startHideCardProcess = false;
        numFlippedCards = 0;
        flippedCardsList.clear();
    }


}
