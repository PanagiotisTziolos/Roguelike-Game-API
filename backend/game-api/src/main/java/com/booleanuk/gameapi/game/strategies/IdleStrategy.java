package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.Animation;
import com.booleanuk.gameapi.game.character.Character;

import java.io.IOException;

public class IdleStrategy implements Strategy {
    private final Character hero;
    private final Animation upAnimation;
    private final Animation downAnimation;
    private final Animation rightAnimation;
    private final Animation leftAnimation;

    public IdleStrategy(Character hero, Animation upAnimation, Animation downAnimation, Animation rightAnimation, Animation leftAnimation) {
        this.hero = hero;
        this.upAnimation = upAnimation;
        this.downAnimation = downAnimation;
        this.rightAnimation = rightAnimation;
        this.leftAnimation = leftAnimation;
    }

    @Override
    public byte[] playAnimation() throws IOException {
        if(hero.isFacingLeft())
            return leftAnimation.nextFrame();
        else if(hero.isFacingRight())
            return rightAnimation.nextFrame();
        else if(hero.isFacingUp())
            return upAnimation.nextFrame();
        else
            return downAnimation.nextFrame();
    }

    @Override
    public boolean executed() {
        return true;
    }
}
