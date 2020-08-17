package com.interview.business.ui;

public class ConsoleUIService implements UIService {

  @Override
  public void printMessage(String message) {
    System.out.println(message);
  }
}
