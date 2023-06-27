package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.models.UserInput;

import java.io.IOException;
import java.util.Map;

public class Strategies implements Strategy {
    private final Map<String, Strategy> strategies;
    private Strategy runningStrategy;
    private final Strategy idle;

    public Strategies(Map<String, Strategy> strategies, Strategy idle) {
        this.strategies = strategies;
        this.runningStrategy = idle;
        this.idle = idle;
    }

    public void execute(String command) throws IOException {
        if (strategies.containsKey(command))
            this.runningStrategy = strategies.get(command);

        if (this.runningStrategy.executed())
            this.runningStrategy = idle;

        this.runningStrategy.execute();
    }

    public byte[] playAnimation() throws IOException {
        return this.runningStrategy.playAnimation();
    }

    public void resetAnimation(UserInput input) {
        if (strategies.containsKey(input.pressedButton))
            strategies.get(input.pressedButton).resetAnimation();
    }

    public boolean executed() {
        return this.runningStrategy.executed();
    }
}
