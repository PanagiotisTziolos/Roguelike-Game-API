package com.booleanuk.gameapi.controllers;


import com.booleanuk.gameapi.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/restart")
public class RestartController {
    @Autowired
    GameService game;

    @GetMapping
    public ResponseEntity<Boolean> restart() {
        return new ResponseEntity<>(game.restart(), HttpStatus.OK);
    }
}
