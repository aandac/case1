package com.interview.business.player;

import static org.mockito.Mockito.mock;

import com.interview.business.controller.GameController;
import com.interview.business.controller.TestGameController;
import com.interview.business.ui.UIService;
import com.interview.model.Card;
import com.interview.model.Player;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {


  @Test
  public void test_playerMana() {
    GameController gameController = mock(GameController.class);
    UIService uiService = mock(UIService.class);
    Player player = new Player(1, "Test", uiService, gameController);
    Assert.assertEquals(0, player.getMana());

    player.refillMana(1);
    Assert.assertEquals(1, player.getMana());

    player.refillMana(5);
    Assert.assertEquals(5, player.getMana());

    player.refillMana(11);
    Assert.assertEquals(10, player.getMana());
  }

  @Test
  public void test_receiveCards() {
    TestGameController gameController = new TestGameController();
    UIService uiService = mock(UIService.class);
    Player player = new Player(1, "Test", uiService, gameController);
    player.refillMana(11);
    Assert.assertEquals(0, player.playCards().getMoves().size());

    gameController.setSelectCount(2);
    player.receiveCards(Arrays.asList(new Card(7), new Card(3)));
    Assert.assertEquals(2, player.playCards().getMoves().size());
  }

}
