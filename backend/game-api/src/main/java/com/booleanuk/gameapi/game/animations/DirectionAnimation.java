package com.booleanuk.gameapi.game.animations;

import com.booleanuk.gameapi.game.character.Character;

import java.io.IOException;

public class DirectionAnimation {
    private final Character c;
    private final Animation upAnimation;
    private final Animation downAnimation;
    private final Animation rightAnimation;
    private final Animation leftAnimation;

    public DirectionAnimation(Character c, Animation upAnimation, Animation downAnimation, Animation rightAnimation, Animation leftAnimation) {
        this.c = c;
        this.upAnimation = upAnimation;
        this.downAnimation = downAnimation;
        this.rightAnimation = rightAnimation;
        this.leftAnimation = leftAnimation;
    }


    public byte[] frame() throws IOException {
        if(c.isFacingLeft())
            return leftAnimation.nextFrame();
        else if(c.isFacingRight())
            return rightAnimation.nextFrame();
        else if(c.isFacingUp())
            return upAnimation.nextFrame();
        else
            return downAnimation.nextFrame();
    }

    public void reset() {
        if(c.isFacingLeft())
            leftAnimation.reset();
        else if(c.isFacingRight())
            rightAnimation.reset();
        else if(c.isFacingUp())
            upAnimation.reset();
        else
            downAnimation.reset();
    }

    public boolean finished() {
        if(c.isFacingLeft())
            return leftAnimation.finished();
        else if(c.isFacingRight())
            return rightAnimation.finished();
        else if(c.isFacingUp())
            return upAnimation.finished();
        else
            return downAnimation.finished();
    }
}