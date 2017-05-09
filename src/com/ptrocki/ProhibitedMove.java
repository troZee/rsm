package com.ptrocki;

public class ProhibitedMove {

    int position;
    int secondPosition;

    public ProhibitedMove(int position, int secondPosition) {
        this.position = position;
        this.secondPosition = secondPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(int secondPosition) {
        this.secondPosition = secondPosition;
    }
}
