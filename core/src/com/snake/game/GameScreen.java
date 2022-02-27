package com.snake.game;

import java.util.*;
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
    int lastMove;

    final int PELLET_SIZE = 8;
    final int SNAKE_SIZE = 8;
    final int WINDOW_LENGTH = 800;
    final int WINDOW_HEIGHT = 480;
    final int SNAKE_SPEED = 200;
    final int MOVE_LEFT = 0;
    final int MOVE_RIGHT = 1;
    final int MOVE_UP = 2;
    final int MOVE_DOWN = 3;

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
		camera.setToOrtho(false, WINDOW_LENGTH, WINDOW_HEIGHT);

		// create a Rectangle to logically represent the pellet 
		pellet = new Rectangle();
		pellet.x = MathUtils.random(0,WINDOW_LENGTH - PELLET_SIZE); // center the bucket horizontally
		pellet.y = MathUtils.random(0,WINDOW_HEIGHT - PELLET_SIZE); // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		pellet.width = PELLET_SIZE;
		pellet.height = PELLET_SIZE;

		// Create first snake pixel
		//ArrayList<Rectangle> snakes = new ArrayList<>();
        Rectangle snake = new Rectangle();
        snake.x = WINDOW_LENGTH / 2;
        snake.y = WINDOW_HEIGHT / 2;
        snake.width = SNAKE_SIZE;
        snake.height = SNAKE_SIZE;
        snakes.add(snake);
        snakeLength = 1;

	}

    private void moveSnake(int move) {
        // add new snake position
        Rectangle snakePosition = (Rectangle) snakes.get(0);
        Rectangle snake = new Rectangle();
        switch (move) {
            case MOVE_RIGHT:
                snake.x = snakePosition.x + SNAKE_SIZE;
                snake.y = snakePosition.y;
                break;
            case MOVE_LEFT:
                snake.x = snakePosition.x - SNAKE_SIZE;
                snake.y = snakePosition.y;
                break;
            case MOVE_UP:
                snake.x = snakePosition.x;
                snake.y = snakePosition.y + SNAKE_SIZE;
                break;
            case MOVE_DOWN:
                snake.x = snakePosition.x;
                snake.y = snakePosition.y - SNAKE_SIZE;
                break;

        }
        snake.width = SNAKE_SIZE;
        snake.height = SNAKE_SIZE;
        snakes.add(0, snake);
        snakes.remove(snakeLength);
    }

    //private void checkOverlap(int move) {
        // check if snake overlaps pellet
        // if overlap, add pixel to snake and generate new pellet
        
    //}

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

		// render snake and pellet 
		game.batch.begin();
		game.font.draw(game.batch, "Pellets Collected: " + pelletsGathered, 0, WINDOW_HEIGHT);
		game.batch.draw(pelletImage, pellet.x, pellet.y);
		for (Rectangle snake : snakes) {
			game.batch.draw(snakeImage, snake.x, snake.y);
		}
		game.batch.end();

		// process user input
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            lastMove = MOVE_LEFT;
            moveSnake(MOVE_LEFT);
        }
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            lastMove = MOVE_RIGHT;
            moveSnake(MOVE_RIGHT);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            lastMove = MOVE_UP;
            moveSnake(MOVE_UP);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            lastMove = MOVE_DOWN;
            moveSnake(MOVE_DOWN);
        }

        // moveSnake outside of ifs to keep snake always in motion

        // check if snake overlaps pellet

		// make sure the snake stays within the screen bounds
		//if (snake.x < 0)
		//	snake.x = 0;

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
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
		snakeImage.dispose();
		pelletImage.dispose();
		//dropSound.dispose();
		//rainMusic.dispose();
	}

}

