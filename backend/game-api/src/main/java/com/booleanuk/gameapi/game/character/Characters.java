package com.booleanuk.gameapi.game.character;

import com.booleanuk.gameapi.game.strategies.Strategies;
import com.booleanuk.gameapi.models.GameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Characters {
    private final Map.Entry<Character, Strategies> hero;
    private final Map<Character, Strategies> enemies;

    public Characters(Map.Entry<Character, Strategies> hero, Map<Character, Strategies> enemies) {
        this.hero = hero;
        this.enemies = enemies;
    }

    public Map<Character, Strategies> allBut(Character c) {
        if (hero.getKey().equals(c))
            return enemies;

        var chars = enemies.entrySet().stream().filter(e -> !e.getKey().equals(c)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        chars.put(hero.getKey(), hero.getValue());

        return chars;
    }

    public List<GameObject> reaction(String command) throws IOException {
        this.hero.getValue().execute(command);

        List<Character> deadChars = this.enemies.keySet().stream().filter(e -> !e.isAlive()).toList();
        deadChars.forEach(this.enemies::remove);

        for (Strategies strategies : this.enemies.values())
            strategies.execute("");

        final List<GameObject> go = new ArrayList<>();

        go.add(new GameObject(this.hero.getValue().playAnimation(), this.hero.getKey().coordinates(), this.hero.getKey().hp()));

        for (Map.Entry<Character, Strategies> enemy : enemies.entrySet())
            go.add(new GameObject(enemy.getValue().playAnimation(), enemy.getKey().coordinates(), enemy.getKey().hp()));

        return go;
    }
}
