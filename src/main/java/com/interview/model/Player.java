package com.interview.model;

import com.interview.business.controller.GameController;
import com.interview.business.ui.UIService;

import java.util.*;

import static com.interview.business.GameConfig.INITIAL_PLAYER_HEALTH;
import static com.interview.business.GameConfig.INITIAL_PLAYER_MANA;

public class Player {

    private final String name;
    private int health;
    private int mana;
    private int manaSlot;
    private Map<Integer, Card> cards;
    private final UIService uiService;
    private final GameController gameController;

    public Player(String name, UIService uiService, GameController gameController) {
        this.name = name;
        this.uiService = uiService;
        this.gameController = gameController;
        this.health = INITIAL_PLAYER_HEALTH;
        this.mana = INITIAL_PLAYER_MANA;
        this.cards = new HashMap<>(5);
    }

    public String getName() {
        return name;
    }

    public void refillMana(int manaSlot) {
        this.manaSlot = manaSlot;
        this.mana = manaSlot;
        if (this.mana > 10) {
            this.mana = 10;
        }
    }

    public void receiveCards(List<Card> receivedCards) {
        if (this.cards.size() == 5) {
            uiService.printMessage("Your hands are full. Overloaded.");
            return;
        }

        for (Card card : receivedCards) {
            this.cards.put(this.cards.size() + 1, card);
        }
    }

    public Turn playCards() {
        showCards();
        // get moves
        int[] indexes = gameController.getSelectedCards();
        if (indexes == null) {
            return new Turn(Collections.emptyList());
        }
        int totalDamage = 0;
        for (int cardPositionInHand : indexes) {
            Card card = cards.get(cardPositionInHand);
            if (card != null) {
                totalDamage += card.getDamage();
            }
        }
        if (this.mana < totalDamage || (this.mana - totalDamage < 0)) {
            System.out.println("Not enough mana to play. Choose again. Your mana is "
                    + this.mana + " and your selected card's total damage is " + totalDamage + ".");
            return playCards();
        }

        List<Card> moves = new ArrayList<>();
        for (int cardPositionInHand : indexes) {
            Card card = cards.get(cardPositionInHand);
            int cardDamage = 0;
            if (card != null) {
                cardDamage = card.getDamage();
                this.mana -= cardDamage;
                cards.remove(cardPositionInHand);
                moves.add(card);
            }
        }

        // arrange cards in hands
        Map<Integer, Card> tempCards = new HashMap<>();
        Collection<Card> values = cards.values();
        for (Card card : values) {
            tempCards.put(tempCards.size() + 1, card);
        }
        this.cards = tempCards;

        return new Turn(moves);
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void applyDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public void applyDamage(Turn turn) {
        turn.getPlayedCards().forEach(card -> applyDamage(card.getDamage()));
    }


    private void showCards() {
        uiService.printMessage("Player " + name
                + " cards in his/her hand are shown in below. Select one or more card or just enter to skip the turn.");
        int count = 1;
        for (Map.Entry<Integer, Card> entry : cards.entrySet()) {
            uiService.printMessage("Select number " + (count++) + " card with damage " + entry.getValue().getDamage());
        }
    }

    public String getStatus() {
        return "Health " + this.health + ", Mana " + this.mana + "/" + this.manaSlot + ".";
    }

}
