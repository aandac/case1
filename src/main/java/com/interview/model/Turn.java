package com.interview.model;

import java.util.List;

public class Turn {

    private final List<Card> playedCards;

    public Turn(List<Card> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    @Override
    public String toString() {
        return "("+playedCards.toString()+")";
    }
}
