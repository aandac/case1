package com.interview.business.controller;

import java.util.Arrays;
import java.util.Scanner;

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
        return numbers != null ? Arrays.stream(numbers).filter(value -> value <= 5).toArray() : null;
    }

}
