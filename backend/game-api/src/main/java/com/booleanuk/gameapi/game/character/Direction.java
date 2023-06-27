package com.booleanuk.gameapi.game.character;

public class Direction {
    private final int x;
    private final int y;

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction(Direction d) {
        this.x = d.x;
        this.y = d.y;
    }

    public boolean isLeft() {
        return x == -1;
    }

    public boolean isRight() {
        return x == 1;
    }

    public boolean isDown() {
        return y == 1;
    }

    public boolean isUp() {
        return y == -1;
    }

    public Direction opposite() {
        return new Direction(-x, -y);
    }
}
