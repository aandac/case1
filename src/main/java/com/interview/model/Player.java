package com.interview.model;

import com.interview.business.controller.GameController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {

  private final String name;
  private final int playOrder;
  private int health;
  private int mana;
  private int manaSlot;
  private final Card[] hands;
  private final GameController gameController;

  public Player(int playOrder, String name, GameController gameController) {
    this.name = name;
    this.playOrder = playOrder;
    this.gameController = gameController;
    this.health = 30;
    this.mana = 0;
    this.hands = new Card[5];
  }

  public int getPlayOrder() {
    return playOrder;
  }

  public String getName() {
    return name;
  }

  public int getHealth() {
    return health;
  }

  private void receiveDamage(int damage) {
    health -= damage;
    if (health < 0) {
      health = 0;
    }
  }

  public void refillMana(int manaSlot) {
    this.manaSlot = manaSlot;
    this.mana = manaSlot;
    if (this.mana > 10) {
      this.mana = 10;
    }
  }

  public void receiveCards(List<Card> cards) {
    if (Arrays.stream(hands).allMatch(Objects::nonNull)) {
      System.out.println("Your hands are full. Overloaded.");
      return;
    }
    for (Card card : cards) {
      for (int i = 0; i < hands.length; i++) {
        Card hand = hands[i];
        if (hand != null) {
          continue;
        }

        hands[i] = card;
        break;
      }
    }
  }

  public Move playCards() {
    showCards();
    // get moves
    int[] indexes = gameController.getSelectedCards();
    if (indexes == null) {
      return new Move(Collections.emptyList());
    }
    List<Card> moves = new ArrayList<>();
    for (int i = 0; i < indexes.length; i++) {
      int cardPositionInHand = indexes[i];
      Card card = hands[cardPositionInHand];
      int cardDamage = card.getDamage();
      if (this.mana < cardDamage || (this.mana - cardDamage < 0)) {
        System.out
            .println("Not enough mana to play. Choose again. Your mana is " + this.mana + ".");
        return playCards();
      }
      this.mana -= cardDamage;

      hands[cardPositionInHand] = null;
      moves.add(card);
    }
    return new Move(moves);
  }


  public void receiveMove(Move move) {
    move.getMoves().forEach(card -> receiveDamage(card.getDamage()));
  }


  public void showCards() {
    System.out.println("Player " + name
        + " cards in his/her hand are shown in below. Select one or more card or just enter to skip the turn.");
    for (int i = 0; i < hands.length; i++) {
      Card card = hands[i];
      if (card != null) {
        System.out.println("Select number " + i + " card with damage " + card.getDamage());
      }
    }
  }

  public int getMana() {
    return mana;
  }

  public int getManaSlot() {
    return manaSlot;
  }
}
