package com.interview.business.player;

import static com.interview.business.GameConfig.INITIAL_PLAYER_HEALTH;
import static com.interview.business.GameConfig.INITIAL_PLAYER_MANA;
import static com.interview.business.GameConfig.MAXIMUM_MANA_SLOT;
import static org.mockito.Mockito.mock;

import com.interview.business.controller.GameController;
import com.interview.business.controller.TestGameController;
import com.interview.business.ui.UIService;
import com.interview.model.Card;
import com.interview.model.Player;
import com.interview.model.Turn;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {


  private Player player;
  private UIService uiService;

  @Before
  public void init() {
    GameController gameController = mock(GameController.class);
    uiService = mock(UIService.class);
    player = new Player("Test", uiService, gameController);

  }

  @Test
  public void test_receiveCards() {
    TestGameController testController = new TestGameController();
    player = new Player("Test", uiService, testController);
    player.refillMana(11);
    Assert.assertEquals(0, player.playCards().getPlayedCards().size());

    testController.setSelectCount(2);
    player.receiveCards(Arrays.asList(new Card(7), new Card(3)));
    Assert.assertEquals(2, player.playCards().getPlayedCards().size());
  }

  @Test
  public void should_receive_damage() {
    Assert.assertFalse(player.isDead());
    player.applyDamage(INITIAL_PLAYER_HEALTH);
    Assert.assertTrue(player.isDead());
  }

  @Test
  public void should_receive_damage_with_cards() {
    Assert.assertFalse(player.isDead());
    player.applyDamage(new Turn(Collections.singletonList(new Card(INITIAL_PLAYER_HEALTH))));
    Assert.assertTrue(player.isDead());
  }

  @Test
  public void should_zero_initial_mana() {
    Assert.assertEquals("Health " + INITIAL_PLAYER_HEALTH + ", Mana " + INITIAL_PLAYER_MANA + "/"
        + INITIAL_PLAYER_MANA + ".", player.getStatus());
  }

  @Test
  public void maximum_mana() {
    player.refillMana(MAXIMUM_MANA_SLOT);
    Assert.assertEquals("Health " + INITIAL_PLAYER_HEALTH + ", Mana " + MAXIMUM_MANA_SLOT + "/"
        + MAXIMUM_MANA_SLOT + ".", player.getStatus());

    int expectedManaSlot = 50;
    player.refillMana(expectedManaSlot);
    Assert.assertEquals("Health " + INITIAL_PLAYER_HEALTH + ", Mana " + MAXIMUM_MANA_SLOT + "/"
        + expectedManaSlot + ".", player.getStatus());
  }

}
