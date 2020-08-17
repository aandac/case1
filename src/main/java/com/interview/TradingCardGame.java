package com.interview;

import com.interview.business.controller.ConsoleGameController;
import com.interview.business.GameEngine;

public class TradingCardGame {

  public static void main(String[] args) {
    ConsoleGameController gameController = new ConsoleGameController();
    GameEngine gameEngine = new GameEngine(gameController);
    gameEngine.newGame("ali", "turgut");
    gameEngine.run();
  }

}
