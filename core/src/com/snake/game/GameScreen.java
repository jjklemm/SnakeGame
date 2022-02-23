package com.snake.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final Snake game;

	Texture snakeImage;
	Texture pelletImage;
	//Sound dropSound;
	//Music rainMusic;
	OrthographicCamera camera;
	Rectangle pellet;
	ArrayList<Rectangle> snakes = new ArrayList<>();
	int pelletsGathered;
    int snakeLength;

    final int PELLET_SIZE = 32;
    final int SNAKE_SIZE = 32;

	public GameScreen(final Snake gam) {
		this.game = gam;

		// load the images for the snake and pellet 
		snakeImage = new Texture(Gdx.files.internal("snake.png"));
		pelletImage = new Texture(Gdx.files.internal("pellet.png"));

		// load the drop sound effect and the rain background "music"
		//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		//rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the pellet 
		pellet = new Rectangle();
		pellet.x = MathUtils.random(0,800 - PELLET_SIZE); // center the bucket horizontally
		pellet.y = MathUtils.random(0,480 - PELLET_SIZE; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		pellet.width = PELLET_SIZE;
		pellet.height = PELLET_SIZE;

		// Create first snake pixel
		snakes = new Array<Rectangle>();
        Rectangle snake = new Rectangle();
        snake.x = 800 / 2;
        snake.y = 480 / 2;
        snake.width = SNAKE_SIZE;
        snake.height = SNAKE_SIZE;
        snakes.add(snake);
        snakeLength = 1;

	}

    private void moveSnakeX(float delta) {
        // need each pixel to take position of pixel that is one position ahead
        Iterator<Rectangle> iter = snakes.iterator();
	    while (iter.hasNext()) {
		    Rectangle snake = iter.next();
		    snake.x -= 200 * Gdx.graphics.getDeltaTime();
		    if (raindrop.y + 64 < 0)
		        iter.remove();
		    if (raindrop.overlaps(bucket)) {
			    dropsGathered++;
			    dropSound.play();
			    iter.remove();
		    }
	    }
    }

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0, 0, 0, 1);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.font.draw(game.batch, "Pellets Collected: " + pelletsGathered, 0, 480);
		game.batch.draw(pelletImage, pellet.x, pellet.y);
		for (Rectangle snake : snakes) {
			game.batch.draw(snakeImage, snake.x, snake.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			snakes[0].x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			snakes[0].x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.UP))
            snakes[0].y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            snakes[0].y -= 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		//if (snake.x < 0)
		//	snake.x = 0;
		//if (snake.x > 800 - 64)
		//	bucket.x = 800 - 64;

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

}

