package com.booleanuk.gameapi.game.animations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FrameList {
    private final String path;
    private final int frameNumber;
    private final List<Frame> frames;

    public FrameList(String path, int frameNumber) {
        this.path = path;
        this.frameNumber = frameNumber;
        this.frames = new ArrayList<>();
    }

    public int size() {
        return this.frameNumber;
    }

    public Frame get(int index) {
        if (this.frames.size() == 0)
            this.frames.addAll(IntStream.range(0, frameNumber).boxed().map(i -> new Frame(path + i + ".png")).toList());

        return this.frames.get(index);
    }
}
