package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.screens.GameScreen;


public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera camera;

	public GameScreen gameScreen;
	public World world;

	@Override
	public void create() {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		gameScreen = new GameScreen(this);

		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}