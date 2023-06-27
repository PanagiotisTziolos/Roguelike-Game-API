package com.booleanuk.gameapi.game.strategies;

import java.io.IOException;

public interface Strategy {
    default void execute() throws IOException {}

    byte[] playAnimation() throws IOException;

    default void resetAnimation() {}

    boolean executed();
}
