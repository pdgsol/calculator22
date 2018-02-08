package com.example.pdg.calulatorapi22;

/**
 * Created by PDG on 06/02/2018.
 */

public class Player {
    private String name;
    private String score;
    private int icon;

    public Player(int icon, String score, String name) {
        this.name = name;
        this.score = score;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public int getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}

