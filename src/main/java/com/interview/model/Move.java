package com.interview.model;

import java.util.List;

public class Move {

  private final List<Card> moves;

  public Move(List<Card> moves) {
    this.moves = moves;
  }

  public List<Card> getMoves() {
    return moves;
  }

  @Override
  public String toString() {
    return "Move{" +
        "moves=" + moves +
        '}';
  }
}
