package com.booleanuk.gameapi.services;

import com.booleanuk.gameapi.models.GameObject;
import com.booleanuk.gameapi.models.UserInput;
import com.booleanuk.gameapi.game.Game;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GameService {
    private Game game = new Game();

    public synchronized List<GameObject> state() throws Exception {
        try {
            return this.game.state();
        } catch(IOException e) {
            System.out.println("Error in state method of the GameService class " + e.getMessage());
            throw new Exception("Server Error");
        }
    }

    public UserInput registerInput(UserInput input) {
        return game.registerInput(input);
    }

    public boolean restart() {
        game = new Game();
        return true;
    }
}
