package com.interview.business;

import com.interview.business.controller.GameController;
import com.interview.business.ui.UIService;
import com.interview.model.Card;
import com.interview.model.Deck;
import com.interview.model.Player;
import com.interview.model.Turn;

import java.util.*;

import static com.interview.business.GameConfig.*;

public class GameEngine {

    private final Map<String, Deck> playerDecks = new HashMap<>(NUMBER_OF_PLAYERS);
    private final Queue<Player> playerQueue = new LinkedList<>();
    /**
     * If the game continues for multiple rounds, this will hold the current count.
     */
    private int round = 1;

    private final GameController gameController;
    private final UIService uiService;

    public GameEngine(GameController gameController, UIService uiService) {
        this.gameController = gameController;
        this.uiService = uiService;
    }

    public void newGame(String playerNameFirst, String playerNameSecond) {
        Random random = new Random();
        String playerOneName = random.nextBoolean() ? playerNameFirst : playerNameSecond;
        uiService.printMessage("New game started. " + playerOneName + " is player one.");

        Player playerOne = new Player(playerOneName, uiService, gameController);
        String playerTwoName = playerOneName.equals(playerNameFirst) ? playerNameSecond : playerNameFirst;
        Player playerTwo = new Player(playerTwoName, uiService, gameController);

        playerQueue.add(playerOne);
        playerQueue.add(playerTwo);

        Deck playerOneDeck = new Deck();
        Deck playerTwoDeck = new Deck();

        List<Card> playerOneInitialCards = playerOneDeck.drawCard(INITIAL_DRAW_CARD_SIZE);
        playerOne.receiveCards(playerOneInitialCards);

        List<Card> playerTwoInitialCards = playerTwoDeck.drawCard(INITIAL_DRAW_CARD_SIZE);
        playerTwo.receiveCards(playerTwoInitialCards);

        playerDecks.put(playerOneName, playerOneDeck);
        playerDecks.put(playerTwoName, playerTwoDeck);
    }

    public void run() {
        while (true) {
            Player winner = checkWinner();
            if (winner != null) {
                uiService.printMessage(winner.getName() + " has won the game.");
                break;
            }

            uiService.printMessage("----------------------------------");
            Player currentPlayer = playerQueue.poll();
            currentPlayer.refillMana(getManaSlot());
            uiService.printMessage(currentPlayer.getName() + " started to play. " + currentPlayer.getStatus());

            Card cardFromDeck = getACardFromDeck(currentPlayer.getName());
            if (cardFromDeck != null) {
                currentPlayer.receiveCards(Collections.singletonList(cardFromDeck));
            } else {
                // bleeding out
                currentPlayer.applyDamage(BLEED_OUT_DAMAGE);
            }

            Turn turn = currentPlayer.playCards();
            Player nextPlayer = playerQueue.peek();
            nextPlayer.applyDamage(turn);
            uiService.printMessage(currentPlayer.getName() +
                    (turn.getPlayedCards().isEmpty() ? " skipped his/her turn. " : " played with card(s) "
                            + turn + "."));
            uiService.printMessage(currentPlayer.getName() + " ended to play.");
            playerQueue.add(currentPlayer);
            round++;
            uiService.printMessage("----------------------------------");
        }
    }


    public Player checkWinner() {
        Player winner = null;
        Iterator<Player> iterator = playerQueue.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.isDead()) {
                winner = iterator.next();
                break;
            }
        }
        return winner;
    }

    public int getManaSlot() {
        int slot = (round % 2) + (round / 2);
        return Math.min(slot, MAXIMUM_MANA_SLOT);
    }

    public Card getACardFromDeck(String playerName) {
        List<Card> cards = playerDecks.get(playerName).drawCard(1);
        return cards != null ? cards.get(0) : null;
    }

}
