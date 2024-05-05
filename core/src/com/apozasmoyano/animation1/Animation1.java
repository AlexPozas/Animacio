package com.apozasmoyano.animation1;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Animation1 implements ApplicationListener {

	private static final int FRAME_COLSW = 8, FRAME_COLSI = 13, FRAME_ROWS = 1;


	Animation<TextureRegion> walkAnimation, idleAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet, idleSheet, background;
	TextureRegion bgRegion;
	SpriteBatch spriteBatch;
	private OrthographicCamera camera;


	float stateTime;
	boolean isWalking = false;
	int dir = 1, dirv = 1;
	float posx, posy;
	Rectangle up, down, left, right;
	final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		spriteBatch = new SpriteBatch();

		//Background
		background = new Texture(Gdx.files.internal("background.jpg"));
		background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		bgRegion = new TextureRegion(background);
		posx = 0;
		posy = 0;

		// facilities per calcular el "touch"
		up = new Rectangle(0, camera.viewportHeight*2/3, camera.viewportWidth, camera.viewportHeight/3);
		down = new Rectangle(0, 0, camera.viewportWidth, camera.viewportHeight/3);
		left = new Rectangle(0, 0, camera.viewportWidth/3, camera.viewportHeight);
		right = new Rectangle(camera.viewportWidth*2/3, 0, camera.viewportWidth/3, camera.viewportHeight);

		//Idle
		idleSheet = new Texture(Gdx.files.internal("idle.png"));

		TextureRegion[][] tmpI = TextureRegion.split(idleSheet,
				idleSheet.getWidth()  / FRAME_COLSI,
				idleSheet.getHeight() / FRAME_ROWS);
		TextureRegion[] idleFrames = new TextureRegion[FRAME_COLSI * FRAME_ROWS];

		int indexI = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLSI; j++) {
				idleFrames[indexI++] = tmpI[i][j];
			}
		}

		idleAnimation = new Animation<TextureRegion>(0.12f, idleFrames);

		// Walk
		walkSheet = new Texture(Gdx.files.internal("walk.png"));

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLSW,
				walkSheet.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLSW * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLSW; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation<TextureRegion>(0.12f, walkFrames);



		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time


		//Background
		bgRegion.setRegion(posx,posy,camera.viewportWidth,camera.viewportHeight+posy);

		//Animations
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		TextureRegion currentFrameIdle = idleAnimation.getKeyFrame(stateTime, true);

		int sprW = 25;
		int sprH = 50;
		spriteBatch.begin();
		spriteBatch.draw(bgRegion,0,0);

		background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		int touch = virtual_joystick_control();

		//Moviment
		if (touch != IDLE) {
			isWalking = true;
			if (touch == UP) {
				dirv = 1;
				posy -= 0.001;
			} else if(touch == LEFT){
				dir = -1;
				posx -= 0.001;
			} else if (touch == RIGHT) {
				dir = 1;
				posx += 0.001;
			} else if (touch == DOWN) {
				dirv = -1;
				posy += 0.001;
			}

		} else {
			isWalking = false;
		}

		if (isWalking) {
			spriteBatch.draw(currentFrame, camera.viewportWidth / 2 - 25*dir, camera.viewportHeight / 2 - 44, (sprW + 50)*dir, sprH + 50); // Draw current frame at (50, 50)
		} else {
			spriteBatch.draw(currentFrameIdle, camera.viewportWidth / 2 - 25*dir, camera.viewportHeight / 2 - 44, (sprW + 50)*dir, sprH + 50); // Draw current frame at (50, 50)
		}
		spriteBatch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
		idleSheet.dispose();
	}

	protected int virtual_joystick_control() {

		for(int i=0;i<10;i++)
			if (Gdx.input.isTouched(i)) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);

				camera.unproject(touchPos);
				if (up.contains(touchPos.x, touchPos.y)) {
					return UP;
				} else if (down.contains(touchPos.x, touchPos.y)) {
					return DOWN;
				} else if (left.contains(touchPos.x, touchPos.y)) {
					return LEFT;
				} else if (right.contains(touchPos.x, touchPos.y)) {
					return RIGHT;
				}
			}
		return IDLE;
	}
}