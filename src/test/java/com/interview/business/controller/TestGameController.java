package com.interview.business.controller;

public class TestGameController implements GameController {

    private int selectCount;

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    @Override
    public int[] getSelectedCards() {
        int[] selectedCards = new int[selectCount];
        for (int i = 0; i < selectCount; i++) {
            selectedCards[i] = i + 1;
        }
        return selectedCards;
    }


}
