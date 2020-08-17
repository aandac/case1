package com.interview.business;

import com.interview.model.Card;
import com.interview.model.Deck;
import com.interview.model.Move;
import com.interview.model.Player;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class TradingCardGameEngine {

  private final Deck[] playerDecks = new Deck[2];
  private final Queue<Player> playerQueue = new LinkedList<>();

  private int round = 1;


  public void run() {
    while (true) {
      Player winner = checkWinner();
      if (winner != null) {
        System.out.println(winner.getName() + " has win the game.");
        break;
      }

      Player currentPlayer = playerQueue.poll();
      currentPlayer.refillMana(getMana());
      System.out.println(currentPlayer.getName() + " started to play.");

      Card cardFromDeck = getACardFromDeck(currentPlayer.getPlayOrder());
      if (cardFromDeck != null) {
        currentPlayer
            .receiveCards(Collections.singletonList(cardFromDeck));
      } else {
        // bleeding out
        currentPlayer.receiveMove(new Move(Collections.singletonList(new Card(1))));
      }

      Move move = currentPlayer.playCards();
      System.out.println(currentPlayer.getName() + " moved with " + move);
      Player nextPlayer = playerQueue.peek();
      nextPlayer.receiveMove(move);
      System.out.println(currentPlayer.getName() + " ended to play.");
      playerQueue.add(currentPlayer);
      round++;
    }
  }


  public void startGame(String playerNameFirst, String playerNameSecond) {
    Random random = new Random();
    String playerOneName = random.nextBoolean() ? playerNameFirst : playerNameSecond;
    System.out.println(playerOneName + " is player one.");
    Player playerOne = new Player(1, playerOneName);
    Player playerTwo = new Player(2,
        playerOneName.equals(playerNameFirst) ? playerNameSecond : playerNameFirst);

    playerQueue.add(playerOne);
    playerQueue.add(playerTwo);

    Deck playerOneDeck = new Deck();
    Deck playerTwoDeck = new Deck();

    List<Card> playerOneInitialCards = playerOneDeck.drawCard(3);
    playerOne.receiveCards(playerOneInitialCards);

    List<Card> playerTwoInitialCards = playerTwoDeck.drawCard(3);
    playerTwo.receiveCards(playerTwoInitialCards);

    playerDecks[0] = playerOneDeck;
    playerDecks[1] = playerTwoDeck;
  }


  public Player checkWinner() {
    Player winner = null;
    Iterator<Player> iterator = playerQueue.iterator();
    while (iterator.hasNext()) {
      Player player = iterator.next();
      if (player.getHealth() < 1) {
        winner = iterator.next();
        break;
      }
    }
    return winner;
  }

  public int getMana() {
    return (round % 2) + (round / 2);
  }

  public Card getACardFromDeck(int playOrder) {
    List<Card> cards = playerDecks[playOrder - 1].drawCard(1);
    return cards != null ? cards.get(0) : null;
  }

  public static void main(String[] args) {
    TradingCardGameEngine tradingCardGameEngine = new TradingCardGameEngine();
    tradingCardGameEngine.startGame("ali","turgut");
    tradingCardGameEngine.run();
  }
}
