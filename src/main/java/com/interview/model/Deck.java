package com.interview.model;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {

    private LinkedList<Card> cards;

    public Deck() {
        List<Integer> manaCosts = Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);
        Collections.shuffle(manaCosts);
        this.cards = manaCosts.stream()
                .map(Card::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<Card> drawCard(int cardSize) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardSize; i++) {
            cards.add(this.cards.pop());
        }
        return cards;
    }
}
