package com.interview.business.player;

import static org.mockito.Mockito.mock;

import com.interview.business.controller.GameController;
import com.interview.business.controller.TestGameController;
import com.interview.business.ui.UIService;
import com.interview.model.Card;
import com.interview.model.Player;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {


  private Player player;

  @Before
  public void init(){
    GameController gameController = mock(GameController.class);
    UIService uiService = mock(UIService.class);
    player = new Player( "Test", uiService, gameController);

  }

  @Test
  public void test_receiveCards() {
    TestGameController gameController = new TestGameController();
    UIService uiService = mock(UIService.class);
    Player player = new Player("Test", uiService, gameController);
    player.refillMana(11);
    Assert.assertEquals(0, player.playCards().getPlayedCards().size());

    gameController.setSelectCount(2);
    player.receiveCards(Arrays.asList(new Card(7), new Card(3)));
    Assert.assertEquals(2, player.playCards().getPlayedCards().size());
  }

}
