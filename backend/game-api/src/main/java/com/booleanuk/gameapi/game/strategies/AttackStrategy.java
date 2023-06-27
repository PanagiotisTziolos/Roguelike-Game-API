package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.DirectionAnimation;
import com.booleanuk.gameapi.game.character.Character;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttackStrategy implements Strategy {
    private final Character hero;
    private final Collision collision;
    private final DirectionAnimation animation;
    private final List<Character> hitCharacters;

    public AttackStrategy(Character hero, Collision collision, DirectionAnimation animation) {
        this.hero = hero;
        this.collision = collision;
        this.animation = animation;
        this.hitCharacters = new ArrayList<>();
    }

    @Override
    public void execute() throws IOException {
        Character hitChar = collision.hitCharacter(Map.entry(hero, ImageIO.read(new ByteArrayInputStream(this.animation.frame()))));

        if (hitChar != null && !hitCharacters.contains(hitChar)) {
            hero.attack(hitChar);
            hitCharacters.add(hitChar);
        }
    }

    @Override
    public byte[] playAnimation() throws IOException {
        return this.animation.frame();
    }

    @Override
    public void resetAnimation() {
        hitCharacters.clear();
        this.animation.reset();
    }

    @Override
    public boolean executed() {
        return this.animation.finished();
    }
}