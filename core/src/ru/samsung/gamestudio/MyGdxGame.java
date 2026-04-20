package ru.samsung.gamestudio;

import static ru.samsung.gamestudio.GameSettings.POSITION_ITERATIONS;
import static ru.samsung.gamestudio.GameSettings.STEP_TIME;
import static ru.samsung.gamestudio.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.screens.GameScreen;


public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera camera;

	public GameScreen gameScreen;
	public World world;
	public BitmapFont commonBlackFont;
	public BitmapFont largeWhiteFont;
	float accumulator = 0;
	public Vector3 touch;
	public BitmapFont commonWhiteFont;


	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += delta;

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}


	@Override
	public void create() {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

		touch = new Vector3();
		gameScreen = new GameScreen(this);


		setScreen(gameScreen);

	}


	@Override
	public void dispose() {
		batch.dispose();
	}
}