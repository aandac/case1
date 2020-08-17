package com.interview;

import com.interview.business.controller.ConsoleGameController;
import com.interview.business.GameEngine;
import com.interview.business.ui.ConsoleUIService;

public class TradingCardGame {

  public static void main(String[] args) {
    ConsoleGameController gameController = new ConsoleGameController();
    ConsoleUIService uiService = new ConsoleUIService();
    GameEngine gameEngine = new GameEngine(gameController, uiService);
    gameEngine.newGame("ali", "turgut");
    gameEngine.run();
  }

}
