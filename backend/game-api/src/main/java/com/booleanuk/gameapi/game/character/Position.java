package com.booleanuk.gameapi.game.character;


public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void update(Direction direction, int speed) {
        if (direction.isLeft())
            x -= speed;
        else if (direction.isRight())
            x += speed;
        else if (direction.isUp())
            y -= speed;
        else if (direction.isDown())
            y += speed;
    }

    public int[] coordinates() {
        return new int[] {x, y};
    }
}