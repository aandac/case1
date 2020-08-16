package com.interview.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Player {

  private int health;
  private int mana;
  private final List<Card> hands;
  private String name;
  private int playOrder;

  public Player(int playOrder, String name) {
    this.name = name;
    this.playOrder = playOrder;
    this.health = 30;
    this.mana = 0;
    this.hands = new ArrayList<Card>(5);
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

  public void refillMana(int mana) {
    this.mana += mana;
    if (this.mana > 10) {
      this.mana = 10;
    }
  }

  public void receiveCards(List<Card> cards) {
    if (hands.size() == 5) {
      System.out.println("Overload case");
      return;
    }
    this.hands.addAll(cards);
  }

  public Move playCards() {
    showCards();
    // get moves
    int[] indexes = getMoveFromConsole();
    if (indexes == null) {
      return new Move(Collections.emptyList());
    }
    List<Card> moves = new ArrayList<>();
    for (int i = 0; i < indexes.length; i++) {
      Card card = hands.get(indexes[i]);
      int cardDamage = card.getDamage();
      if (this.mana < cardDamage || (this.mana - cardDamage < 0)) {
        System.out
            .println("Not enough mana to play. Choose again. Your mana is " + this.mana + ".");
        return playCards();
      }
      this.mana -= cardDamage;

      moves.add(card);
    }
    return new Move(moves);
  }

  private int[] getMoveFromConsole() {
    Scanner in = new Scanner(System.in);
    String[] data = in.nextLine().split(" ");
    int[] numbers = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      numbers[i] = Integer.parseInt(data[i]);
    }
    return  numbers;
  }

  public void receiveMove(Move move) {
    move.getMoves().forEach(card -> receiveDamage(card.getDamage()));
  }


  public void showCards() {
    System.out.println("Your cards in your hand");
    for (int i = 0; i < hands.size(); i++) {
      Card card = hands.get(i);
      System.out.println("Number " + i + " card with damage " + card.getDamage());
    }
  }
}
