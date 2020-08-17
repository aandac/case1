package com.interview.business;

import com.interview.model.Card;
import com.interview.model.Deck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeckTest {


  @Test
  public void initialize_deck() {
    Deck deck = new Deck();
    List<Card> cards = deck.drawCard(1);
    Assert.assertEquals(1, cards.size());
  }

  @Test
  public void deck_collection_control() {
    Deck deck = new Deck();
    List<Integer> manaCosts = new ArrayList<>(Arrays
        .asList(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8));
    Iterator<Integer> iterator = manaCosts.iterator();
    List<Card> cards = deck.drawCard(20);
    while (iterator.hasNext()) {
      Integer cost = iterator.next();
      for (Card card : cards) {
        if (cost == card.getDamage()) {
          iterator.remove();
          break;
        }
      }
    }
    Assert.assertEquals(0, manaCosts.size());
  }

}
