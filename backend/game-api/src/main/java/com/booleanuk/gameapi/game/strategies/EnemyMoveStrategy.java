package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.Animation;
import com.booleanuk.gameapi.game.character.Character;
import com.booleanuk.gameapi.game.character.Direction;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class EnemyMoveStrategy implements Strategy {
    private final Character enemy;
    private final Character hero;
    private final Animation animation;
    private final Collision collision;

    public EnemyMoveStrategy(Character enemy, Character hero, Animation animation, Collision collision) {
        this.enemy = enemy;
        this.hero = hero;
        this.animation = animation;
        this.collision = collision;
    }

    private double distance(Direction direction) throws IOException {
        enemy.move(direction);

        int heroX = hero.coordinates()[0];
        int heroY = hero.coordinates()[1];

        int enemyX = enemy.coordinates()[0];
        int enemyY = enemy.coordinates()[1];

        double distance = Math.sqrt(Math.pow(heroX - enemyX, 2) + Math.pow(heroY - enemyY, 2));

        enemy.move(direction.opposite());

        return distance;
    }

    @Override
    public void execute() throws IOException {
        if (animation.finished())
            animation.reset();

        Direction up = new Direction(0, -1);
        Direction down = new Direction(0, 1);
        Direction left = new Direction(-1, 0);
        Direction right = new Direction(1, 0);

        final Map<Double, Direction> directions = new HashMap<>() {{
            put(distance(up), up);
            put(distance(down), down);
            put(distance(left), left);
            put(distance(right), right);
        }};

        Iterator<Double> sortedDistances = directions.keySet().stream().sorted().toList().iterator();
        boolean moved = false;

        while(!moved && sortedDistances.hasNext()) {
            Direction d = directions.get(sortedDistances.next());
            enemy.move(d);
            if (collision.occurred(Map.entry(enemy, ImageIO.read(new ByteArrayInputStream(animation.nextFrame())))))
                enemy.move(d.opposite());
            else
                moved = true;
        }
    }

    @Override
    public byte[] playAnimation() throws IOException {
        return this.animation.nextFrame();
    }

    @Override
    public void resetAnimation() {
        this.animation.reset();
    }

    @Override
    public boolean executed() {
        return false;
    }
}
