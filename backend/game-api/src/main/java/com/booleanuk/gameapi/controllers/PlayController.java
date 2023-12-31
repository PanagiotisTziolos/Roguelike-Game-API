package com.booleanuk.gameapi.controllers;

import com.booleanuk.gameapi.models.GameObject;
import com.booleanuk.gameapi.models.UserInput;
import com.booleanuk.gameapi.services.GameService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/play")
public class PlayController {
    @Autowired
    GameService game;

    @GetMapping
    public ResponseEntity<List<GameObject>> all() {
        try {
            return new ResponseEntity<>(game.state(), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error in GET request" + e.getMessage()
            );
        }
    }

    @PostMapping
    public ResponseEntity<UserInput> update(@RequestBody UserInput input) {
        return new ResponseEntity<>(game.registerInput(input), HttpStatus.OK);
    }
}
