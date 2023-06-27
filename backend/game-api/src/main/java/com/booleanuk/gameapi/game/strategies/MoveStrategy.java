package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.Animation;

import com.booleanuk.gameapi.game.character.Direction;
import com.booleanuk.gameapi.game.character.Character;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MoveStrategy implements Strategy {
    private final Character c;
    private final Collision collision;
    private final Direction direction;
    private final Animation animation;

    public MoveStrategy(Character c, Collision collision, Direction direction, Animation animation) {
        this.c = c;
        this.collision = collision;
        this.direction = direction;
        this.animation = animation;
    }

    @Override
    public void execute() throws IOException {
        if (!this.animation.finished()) {
            c.changeDirection(this.direction);
            c.move(this.direction);
            if (collision.occurred(Map.entry(c, ImageIO.read(new ByteArrayInputStream(this.animation.nextFrame())))))
                c.move(this.direction.opposite());
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
        return this.animation.finished();
    }
}
