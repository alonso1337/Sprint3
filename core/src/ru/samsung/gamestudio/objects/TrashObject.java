package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSettings;

public class TrashObject extends GameObject {
    public static final int paddingHorizontal = 10;

    public TrashObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                world
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));

    }
    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }
}