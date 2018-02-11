package com.example.pdg.calulatorapi22;

import android.util.Pair;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by PDG on 11/02/2018.
 */

public class CardGame {


    private int numCards = 12;
    private int nullId = 0xFFFF;
    private List<Integer> listDrawableId = new ArrayList<Integer>();
    private List<Pair<Integer, Integer>> imageViewList = new ArrayList<Pair<Integer, Integer>>();

    public CardGame() {
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    private void buildDrawableIdsArray()
    {
        listDrawableId.add(R.drawable.corpetit);
        listDrawableId.add(R.drawable.ozaru);
        listDrawableId.add(R.drawable.freezer1);
        listDrawableId.add(R.drawable.gohan);
        listDrawableId.add(R.drawable.ginyu);
        listDrawableId.add(R.drawable.gokusuperguerrer2);
        listDrawableId.add(R.drawable.jeice);
        listDrawableId.add(R.drawable.nappa);
        listDrawableId.add(R.drawable.trunks);
        listDrawableId.add(R.drawable.vegeta1);
        listDrawableId.add(R.drawable.vegetasuperguerrer);
    }

    private List<Integer> generateRandomCardDistribution(int numCards)
    {
        int numImages = listDrawableId.size();
        List<Integer> randomCombination = new ArrayList<Integer>();
        //TODO : Avoid shuffle twice, pending to implement an efficient way...
        Collections.shuffle(listDrawableId);
        List<Integer> auxList = listDrawableId.subList(0, numCards/2);
        randomCombination.addAll(auxList);
        randomCombination.addAll(auxList);
        Collections.shuffle(randomCombination);

        return randomCombination;
    }

    private void addViewImageList(int imageId, int imageViewId)
    {
        imageViewList.add(Pair.create(imageId, imageViewId));
    }

    public List<Pair<Integer, Integer>> getCardGameList()
    {
        return imageViewList;
    }

    public void buildCardGame()
    {
        buildDrawableIdsArray();

        List<Integer> randomCombination = generateRandomCardDistribution(numCards);

        addViewImageList(randomCombination.get(0), R.id.card11);
        addViewImageList(randomCombination.get(1), R.id.card12);
        addViewImageList(randomCombination.get(2), R.id.card13);
        addViewImageList(randomCombination.get(3), R.id.card14);
        addViewImageList(randomCombination.get(4), R.id.card21);
        addViewImageList(randomCombination.get(5), R.id.card22);
        addViewImageList(randomCombination.get(6), R.id.card23);
        addViewImageList(randomCombination.get(7), R.id.card24);
        addViewImageList(randomCombination.get(8), R.id.card31);
        addViewImageList(randomCombination.get(9), R.id.card32);
        addViewImageList(randomCombination.get(10), R.id.card33);
        addViewImageList(randomCombination.get(11), R.id.card34);
    }

    public int getDrawableIdFromImageViewId(int imageViewId)
    {
        int drawableId = nullId;
        ImageView imageView;
        for(int i = 0; i < imageViewList.size(); ++i)
        {
            if( imageViewList.get(i).second == imageViewId )
            {
                drawableId = imageViewList.get(i).first;
                break;
            }
        }
        return drawableId;
    }

    public boolean checkFlippedCards(List<Integer> flippedCardsList)
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

}
