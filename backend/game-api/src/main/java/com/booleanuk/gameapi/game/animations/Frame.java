package com.booleanuk.gameapi.game.animations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Frame {
    private final String path;

    public Frame(String imagePath) {
        this.path = imagePath;
    }

    public byte[] image() throws IOException {
        return Files.readAllBytes(Path.of(path));
    }
}
