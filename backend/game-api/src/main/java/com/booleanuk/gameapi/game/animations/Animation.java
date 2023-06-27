package com.booleanuk.gameapi.game.animations;

import com.booleanuk.gameapi.game.strategies.IdleStrategy;

import java.io.IOException;

public class Animation {
    private final FrameList frames;
    private int currentFrame;
    private boolean finished;

    public Animation(FrameList frames) {
        this.frames = frames;
        this.currentFrame = -1;
        this.finished = false;
    }

    public byte[] nextFrame() throws IOException {
        if (currentFrame == frames.size() - 1) {
            this.finished = true;
            return frames.get(0).image();
        }

        this.currentFrame++;
        return frames.get(currentFrame).image();
    }

    public void reset() {
        this.finished = false;
        this.currentFrame = -1;
    }

    public boolean finished() {
        return this.finished;
    }
}
