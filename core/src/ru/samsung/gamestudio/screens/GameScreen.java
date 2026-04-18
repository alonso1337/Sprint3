package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.ShipObject;
import ru.samsung.gamestudio.objects.TrashObject;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    GameSession gameSession;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;

    ShipObject shipObject;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        gameSession = new GameSession();
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
    }
    public void show() {
        gameSession.startGame();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shipObject.move(myGdxGame.touch);
        }
    }


    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        handleInput();
        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(
                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                    GameResources.TRASH_IMG_PATH,
                    myGdxGame.world
            );
            trashArray.add(trashObject);
        }
        if (shipObject.needToShoot()) {
            BulletObject bulletObject = new BulletObject(GameResources.BULLET_IMG_PATH,shipObject.getX(),shipObject.getY() + 25 + GameSettings.SHIP_HEIGHT / 2,GameSettings.BULLET_WIDTH,GameSettings.BULLET_HEIGHT,myGdxGame.world);
            bulletArray.add(bulletObject);
        }
        updateTrash();
        draw();
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }
    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            if (!trashArray.get(i).isInFrame()) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }
    private void updateBullet() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (!bulletArray.get(i).isInFrame()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }



}