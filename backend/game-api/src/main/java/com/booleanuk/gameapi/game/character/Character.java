package com.booleanuk.gameapi.game.character;

public class Character {
    private double hp;
    private final double atk;
    private final double def;
    private final int speed;
    private final Position position;
    private Direction direction;

    public Character(double hp, double atk, double def, int speed, Position position, Direction direction) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.position = position;
        this.direction = direction;
    }

    public Character(Character c) {
        this.hp = c.hp;
        this.atk = c.atk;
        this.def = c.def;
        this.speed = c.speed;
        this.position = new Position(c.position);
        this.direction = new Direction(c.direction);
    }

    public void changeDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(Direction direction) {
        this.position.update(direction, speed);
    }

    public void attack(Character c) {
        c.takeDamage(this.atk);
    }

    public void takeDamage(double atk) {
        this.hp -= atk - atk * this.def / 100;

        if (this.hp < 0.0)
            this.hp = 0.0;
    }

    public boolean isAlive() {
        return this.hp != 0.0;
    }

    public boolean isFacingLeft() {
        return this.direction.isLeft();
    }

    public boolean isFacingRight() {
        return this.direction.isRight();
    }

    public boolean isFacingUp() {
        return this.direction.isUp();
    }

    public boolean isFacingDown() {
        return this.direction.isDown();
    }

    public int[] coordinates() {
        return this.position.coordinates();
    }

    public double hp() {
        return this.hp;
    }
}
