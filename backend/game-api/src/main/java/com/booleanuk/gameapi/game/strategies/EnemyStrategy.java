package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.Animation;
import com.booleanuk.gameapi.game.animations.DirectionAnimation;
import com.booleanuk.gameapi.game.character.Character;
import com.booleanuk.gameapi.game.character.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class EnemyStrategy implements Strategy {
    private final Character enemy;
    private final Character hero;
    private final Move move;
    private final Attack attack;
    private final Collision collision;
    private byte[] frame;

    public EnemyStrategy(Character enemy, Character hero, DirectionAnimation moveAnimation, DirectionAnimation attackAnimation, Collision collision) {
        this.enemy = enemy;
        this.hero = hero;
        this.move = new Move(moveAnimation);
        this.attack = new Attack(attackAnimation);
        this.collision = collision;
    }

    public void execute() throws IOException {
        this.frame = move.playAnimation();
        if (move.distance() < 150) {
            if (move.distance() < 70) {
                attack.execute();
                frame = attack.playAnimation();
            } else {
                move.execute();
                frame = move.playAnimation();
            }
        }
    }

    @Override
    public byte[] playAnimation() throws IOException {
        return frame;
    }

    @Override
    public void resetAnimation() {
        Strategy.super.resetAnimation();
    }

    @Override
    public boolean executed() {
        return false;
    }

    class Move {
        private final DirectionAnimation animation;

        public Move(DirectionAnimation animation) {
            this.animation = animation;
        }
//        public void execute() throws IOException {
//            if (animation.finished())
//                animation.reset();
//
//            Direction up = new Direction(0, -1);
//            Direction down = new Direction(0, 1);
//            Direction left = new Direction(-1, 0);
//            Direction right = new Direction(1, 0);
//
//            final Map<Double, Direction> directions = new HashMap<>() {{
//                put(distance(up), up);
//                put(distance(down), down);
//                put(distance(left), left);
//                put(distance(right), right);
//            }};
//
//            Iterator<Double> sortedDistances = directions.keySet().stream().sorted().toList().iterator();
//            boolean moved = false;
//
//            while(!moved && sortedDistances.hasNext()) {
//                Direction d = directions.get(sortedDistances.next());
//                enemy.move(d);
//                if (collision.occurred(Map.entry(enemy, ImageIO.read(new ByteArrayInputStream(animation.frame())))))
//                    enemy.move(d.opposite());
//                else {
//                    moved = true;
//                    enemy.changeDirection(d);
//                }
//            }
//        }

        private double distance() {
            int heroX = hero.coordinates()[0];
            int heroY = hero.coordinates()[1];

            int enemyX = enemy.coordinates()[0];
            int enemyY = enemy.coordinates()[1];

            return Math.sqrt(Math.pow(heroX - enemyX, 2) + Math.pow(heroY - enemyY, 2));
        }

        public double distance(Direction direction) {
            enemy.move(direction);

            int heroX = hero.coordinates()[0];
            int heroY = hero.coordinates()[1];

            int enemyX = enemy.coordinates()[0];
            int enemyY = enemy.coordinates()[1];

            double distance = Math.sqrt(Math.pow(heroX - enemyX, 2) + Math.pow(heroY - enemyY, 2));

            enemy.move(direction.opposite());

            return distance;
        }

        private int distanceX(Direction direction) {
            enemy.move(direction);

            int heroX = hero.coordinates()[0];
            int enemyX = enemy.coordinates()[0];

            int distance = Math.abs(heroX - enemyX);

            enemy.move(direction.opposite());

            return distance;
        }

        private int distanceY() {
            int heroY = hero.coordinates()[1];
            int enemyY = enemy.coordinates()[1];

            return Math.abs(heroY - enemyY);
        }

        private int distanceX( ){
            int heroX = hero.coordinates()[0];
            int enemyX = enemy.coordinates()[0];

            return Math.abs(heroX - enemyX);
        }

        private int distanceY(Direction direction) {
            enemy.move(direction);

            int heroY = hero.coordinates()[1];
            int enemyY = enemy.coordinates()[1];

            int distance = Math.abs(heroY - enemyY);

            enemy.move(direction.opposite());

            return distance;
        }

        public void execute() throws IOException {
            if (animation.finished())
                animation.reset();

            Direction up = new Direction(0, -1);
            Direction down = new Direction(0, 1);
            Direction left = new Direction(-1, 0);
            Direction right = new Direction(1, 0);

            final Map<Direction, int[]> directions = new HashMap<>() {{
                put(up, new int[] { distanceX(up), distanceY(up) });
                put(down, new int[] { distanceX(down), distanceY(down) });
                put(left, new int[] { distanceX(left), distanceY(left) });
                put(right, new int[] { distanceX(right), distanceY(right) });
            }};

            Iterator<Map.Entry<Direction, int[]>> sortedX = directions.entrySet().stream().filter(e -> e.getKey().equals(left) || e.getKey().equals(right)).sorted(Comparator.comparing(e -> e.getValue()[0])).toList().iterator();
            Iterator<Map.Entry<Direction, int[]>> sortedY = directions.entrySet().stream().filter(e -> e.getKey().equals(down) || e.getKey().equals(up)).sorted(Comparator.comparing(e -> e.getValue()[1])).toList().iterator();

            Direction direction = sortedX.next().getKey();
            boolean moved = false;


            while(!moved) {
                if (distanceX() > 5) {
                    enemy.changeDirection(direction);
                    enemy.move(direction);
                    if (collision.occurred(Map.entry(enemy, ImageIO.read(new ByteArrayInputStream(this.animation.frame()))))) {
                        enemy.move(direction.opposite());
                        if (sortedY.hasNext())
                            direction = sortedY.next().getKey();
                    }
                    else
                        moved = true;
                } else if (sortedY.hasNext()) {
                    direction = sortedY.next().getKey();
                } else {
                    moved = true;
                }

                if (distanceY() > 2) {
                    enemy.changeDirection(direction);
                    enemy.move(direction);
                    if (collision.occurred(Map.entry(enemy, ImageIO.read(new ByteArrayInputStream(this.animation.frame()))))) {
                        enemy.move(direction.opposite());
                        if (sortedX.hasNext()) {
                            direction = sortedX.next().getKey();
                        }
                    }
                    else
                        moved = true;
                } else if (sortedX.hasNext()) {
                    direction = sortedX.next().getKey();
                } else {
                    moved = true;
                }
            }
        }

        public byte[] playAnimation() throws IOException {
            return this.animation.frame();
        }

        public void resetAnimation() {
            this.animation.reset();
        }

        public boolean executed() {
            return false;
        }
    }

    class Attack {
        private final DirectionAnimation animation;
        private final List<Character> hitCharacters;

        public Attack(DirectionAnimation animation) {
            this.animation = animation;
            this.hitCharacters = new ArrayList<>();
        }
        public void execute() throws IOException {
            if (animation.finished()) {
                animation.reset();
                hitCharacters.clear();
            }

            Character hitChar = collision.hitCharacter(Map.entry(enemy, ImageIO.read(new ByteArrayInputStream(this.animation.frame()))));

            if (hitChar != null && hitChar.equals(hero) && !hitCharacters.contains(hitChar)) {
                hero.attack(hitChar);
                hitCharacters.add(hitChar);
            }
        }

        public byte[] playAnimation() throws IOException {
            return this.animation.frame();
        }

        public void resetAnimation() {
            this.animation.reset();
        }

        public boolean executed() {
            return false;
        }
    }
}
