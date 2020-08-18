package com.interview.business.controller;

import java.util.Arrays;
import java.util.Scanner;

import static com.interview.business.GameConfig.MAXIMUM_CARD_COUNT_IN_HANDS;

public class ConsoleGameController implements GameController {

    @Override
    public int[] getSelectedCards() {
        Scanner in = new Scanner(System.in);
        String[] data = in.nextLine().split(" ");
        int[] numbers = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            String parsed = data[i];
            try {
                numbers[i] = Integer.parseInt(parsed);
            } catch (NumberFormatException e) {
                // ignore, other than numbers
                numbers = null;
            }
        }
        ;
        return numbers != null ? Arrays.stream(numbers).filter(value -> value <= MAXIMUM_CARD_COUNT_IN_HANDS).toArray() : null;
    }

}
