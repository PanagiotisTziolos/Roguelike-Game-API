package com.booleanuk.gameapi.game.strategies;

import com.booleanuk.gameapi.game.animations.Frame;
import com.booleanuk.gameapi.game.character.Character;
import com.booleanuk.gameapi.game.character.Characters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Collision {
    private final Characters characters;
    private final Frame gameMap;

    public Collision(Characters characters, Frame gameMap) {
        this.characters = characters;
        this.gameMap = gameMap;
    }

    private List<int[]> pixelCoords(Character c, BufferedImage frame) {
        final List<int[]> pixelCoords = new ArrayList<>();

        for (int y = 0; y < frame.getHeight(); y++) {
            for (int x = 0; x < frame.getWidth(); x++) {
                if ((frame.getRGB(x, y) >> 24) != 0x00)
                    pixelCoords.add(
                            new int[] {
                                    c.coordinates()[0] - frame.getWidth()/2 + x,
                                    c.coordinates()[1] - frame.getHeight()/2 + y
                            }
                    );
            }
        }

        return pixelCoords;
    }

    private Character hitCharacterWithError(Map.Entry<Character, BufferedImage> character, int hitboxError) throws IOException {
        // save the coords of each pixel of the hero frame
        // Character.coordinates() depict the top right corner of the frame
        // so i reduce from that the image's dimensions to place the coords at the center of the image
        final List<int[]> heroPixelCoords = pixelCoords(character.getKey(), character.getValue());

        final Map<Character, List<int[]>> enemyPixelCoords = new HashMap<>();

        for (Map.Entry<Character, Strategies> enemy : characters.allBut(character.getKey()).entrySet())
            enemyPixelCoords.put(enemy.getKey(), pixelCoords(enemy.getKey(), ImageIO.read(new ByteArrayInputStream(enemy.getValue().playAnimation()))));

        // check if the position of each pixel of the hero frame
        // is the same as the pixels of the enemy frame
        for (Map.Entry<Character, List<int[]>> epc : enemyPixelCoords.entrySet()) {
            for (int[] hp : heroPixelCoords) {
                for (int[] ep : epc.getValue()) {
                    if (Math.abs(hp[0] - ep[0]) < hitboxError && Math.abs(hp[1] - ep[1]) < hitboxError)
                        return epc.getKey();
                }
            }
        }

        return null;
    }

    public Character hitCharacter(Map.Entry<Character, BufferedImage> character) throws IOException {
        return hitCharacterWithError(character, 5);
    }

    public boolean occurred(Map.Entry<Character, BufferedImage> character) throws IOException {
        final List<int[]> charPixelCoords = pixelCoords(character.getKey(), character.getValue());
        final BufferedImage map = ImageIO.read(new ByteArrayInputStream(gameMap.image()));
        Color allowedToWalkcolor = new Color(6, 16, 17);

        // check if the position of each pixel of the hero frame
        // is the same as the pixels of the enemy frame
        for (int[] hp : charPixelCoords) {
            if (map.getRGB(hp[0], hp[1]) != allowedToWalkcolor.getRGB())
                return true;
        }

        return hitCharacterWithError(character, 1) != null;
    }
}
