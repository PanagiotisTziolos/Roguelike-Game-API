package com.booleanuk.gameapi.game;

import com.booleanuk.gameapi.game.animations.Animation;
import com.booleanuk.gameapi.game.animations.DirectionAnimation;
import com.booleanuk.gameapi.game.animations.Frame;
import com.booleanuk.gameapi.game.animations.FrameList;

import com.booleanuk.gameapi.game.character.Character;
import com.booleanuk.gameapi.game.character.Characters;
import com.booleanuk.gameapi.game.character.Direction;
import com.booleanuk.gameapi.game.character.Position;

import com.booleanuk.gameapi.game.strategies.*;

import com.booleanuk.gameapi.models.GameObject;
import com.booleanuk.gameapi.models.UserInput;

import java.io.IOException;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private final Frame gameMap;
    private final Characters characters;
    private final Strategies heroStrategies;
    private UserInput input;

    public Game() {
        Character hero = new Character(
                30,
                10.0,
                2.0,
                5,
                new Position(200, 300),
                new Direction(1, 0)
        );

        Map<String, Strategy> heroStrategiesMap = new HashMap<>();

        Map.Entry<Character, Strategies> heroMap = Map.entry(
                hero,
                new Strategies(
                        heroStrategiesMap,
                        new IdleStrategy(
                                hero,
                                new Animation(new FrameList("src/main/resources/static/hero/move/up/", 1)),
                                new Animation(new FrameList("src/main/resources/static/hero/move/down/", 1)),
                                new Animation(new FrameList("src/main/resources/static/hero/move/right/", 1)),
                                new Animation(new FrameList("src/main/resources/static/hero/move/left/", 1))
                        )
                )
        );

        List<Position> positions = new ArrayList<>() {{
            add(new Position(400, 200));
            add(new Position(600, 400));
        }};

        List<Character> enemies = IntStream.range(0, 2)
                .boxed()
                .map(i -> new Character(
                        15,
                        5.0,
                        1.0,
                        4,
                        positions.get(i),
                        new Direction(new Random().nextInt(-1, 2), new Random().nextInt(-1, 2))
                )).toList();

        List<HashMap<String, Strategy>> enemyStrategiesMap = enemies.stream().map(i -> new HashMap<String, Strategy>()).toList();

        Map<Character, Strategies> enemiesMap = IntStream.range(0, enemies.size())
                .boxed()
                .map(i -> Map.entry(
                        enemies.get(i),
                        new Strategies(
                                enemyStrategiesMap.get(i),
                                new IdleStrategy(
                                        enemies.get(i),
                                        new Animation(new FrameList("src/main/resources/static/goblin/move/up/", 1)),
                                        new Animation(new FrameList("src/main/resources/static/goblin/move/down/", 1)),
                                        new Animation(new FrameList("src/main/resources/static/goblin/move/right/", 1)),
                                        new Animation(new FrameList("src/main/resources/static/goblin/move/left/", 1))
                                )
                        ))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        heroStrategies = heroMap.getValue();
        characters = new Characters(heroMap, enemiesMap);
        gameMap = new Frame("src/main/resources/static/map.png");

        Collision collision = new Collision(characters, gameMap);

        heroStrategiesMap.put("a", new MoveStrategy(hero, collision, new Direction(-1, 0), new Animation(new FrameList("src/main/resources/static/hero/move/left/", 8))));
        heroStrategiesMap.put("d", new MoveStrategy(hero, collision, new Direction(1, 0), new Animation(new FrameList("src/main/resources/static/hero/move/right/", 8))));
        heroStrategiesMap.put("w", new MoveStrategy(hero, collision, new Direction(0, -1), new Animation(new FrameList("src/main/resources/static/hero/move/up/", 8))));
        heroStrategiesMap.put("s", new MoveStrategy(hero, collision, new Direction(0, 1), new Animation(new FrameList("src/main/resources/static/hero/move/down/", 8))));
        heroStrategiesMap.put(" ", new AttackStrategy(
                                            hero,
                                            collision,
                                            new DirectionAnimation(
                                                    hero,
                                                    new Animation(new FrameList("src/main/resources/static/hero/attack/up/", 6)),
                                                    new Animation(new FrameList("src/main/resources/static/hero/attack/down/", 6)),
                                                    new Animation(new FrameList("src/main/resources/static/hero/attack/right/", 6)),
                                                    new Animation(new FrameList("src/main/resources/static/hero/attack/left/", 6))
                                            )
                                    )
        );

        IntStream.range(0, enemies.size()).forEach(i -> {
            enemyStrategiesMap.get(i).put(
                    "",
                    new EnemyStrategy(
                            enemies.get(i),
                            hero,
                            new DirectionAnimation(
                                    enemies.get(i),
                                    new Animation(new FrameList("src/main/resources/static/goblin/move/up/", 8)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/move/down/", 8)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/move/right/", 8)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/move/left/", 8))
                            ),
                            new DirectionAnimation(
                                    enemies.get(i),
                                    new Animation(new FrameList("src/main/resources/static/goblin/attack/up/", 6)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/attack/down/", 6)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/attack/right/", 6)),
                                    new Animation(new FrameList("src/main/resources/static/goblin/attack/left/", 6))
                            ),
                            collision
                    ));
        });

        this.input = new UserInput("");
    }

    public UserInput registerInput(UserInput input) {
        if (!this.input.pressedButton.equals(input.pressedButton))
            heroStrategies.resetAnimation(this.input);
        else if (heroStrategies.executed())
            heroStrategies.resetAnimation(input);

        this.input = input;
        return this.input;
    }

    public List<GameObject> state() throws IOException {
        List<GameObject> state = characters.reaction(this.input.pressedButton);
        state.add(new GameObject(gameMap.image(), new int[] { 0, 0 }, 0.0));

        return state;
    }
}