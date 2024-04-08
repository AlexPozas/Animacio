package com.apozasmoyano.animation1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class Animation1 implements ApplicationListener {
	// Constant rows and columns of the sprite sheet


	// Objects used
	// Must declare frame type (TextureRegion)
	Texture walkSheet;
	Texture background;
	SpriteBatch spriteBatch;
	Animation<TextureRegion> walk;
	TextureRegion bgRegion;
	int posx;
	int posy;
	// A variable for tracking elapsed time for the animation
	float stateTime;

	@Override
	public void create() {
		background = new Texture(Gdx.files.internal("background.jpg"));
		background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		 bgRegion = new TextureRegion(background);
		posx = 0;
		posy = 0;

		// Load the sprite sheet as a Texture
		walkSheet = new Texture(Gdx.files.internal("walk.jpg"));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.



		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[27];

		walkFrames[0] = new TextureRegion(walkSheet,42,215,79,140);
		walkFrames[1] = new TextureRegion(walkSheet,152,215,79,140);
		walkFrames[2] = new TextureRegion(walkSheet,256,215,79,140);
		walkFrames[3] = new TextureRegion(walkSheet,354,215,79,140);
		walkFrames[4] = new TextureRegion(walkSheet,459,215,79,140);
		walkFrames[5] = new TextureRegion(walkSheet,565,215,79,140);
		walkFrames[6] = new TextureRegion(walkSheet,661,215,79,140);
		walkFrames[7] = new TextureRegion(walkSheet,772,215,79,140);
		walkFrames[8] = new TextureRegion(walkSheet,881,215,79,140);

		walkFrames[9] = new TextureRegion(walkSheet,42,395,79,140);
		walkFrames[10] = new TextureRegion(walkSheet,152,395,79,140);
		walkFrames[11] = new TextureRegion(walkSheet,256,395,79,140);
		walkFrames[12] = new TextureRegion(walkSheet,354,395,79,140);
		walkFrames[13] = new TextureRegion(walkSheet,459,395,79,140);
		walkFrames[14] = new TextureRegion(walkSheet,565,395,79,140);
		walkFrames[15] = new TextureRegion(walkSheet,661,395,79,140);
		walkFrames[16] = new TextureRegion(walkSheet,772,395,79,140);
		walkFrames[17] = new TextureRegion(walkSheet,881,395,79,140);

		walkFrames[18] = new TextureRegion(walkSheet,42,570,79,140);
		walkFrames[19] = new TextureRegion(walkSheet,152,570,79,140);
		walkFrames[20] = new TextureRegion(walkSheet,256,570,79,140);
		walkFrames[21] = new TextureRegion(walkSheet,354,570,79,140);
		walkFrames[22] = new TextureRegion(walkSheet,459,570,79,140);
		walkFrames[23] = new TextureRegion(walkSheet,565,570,79,140);
		walkFrames[24] = new TextureRegion(walkSheet,661,570,79,140);
		walkFrames[25] = new TextureRegion(walkSheet,772,566,79,140);
		walkFrames[26] = new TextureRegion(walkSheet,881,566,79,140);







		// Initialize the Animation with the frame interval and array of frames
		walk = new Animation<TextureRegion>(0.025f, walkFrames);

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walk.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 550, 100,0,0,currentFrame.getRegionWidth(),currentFrame.getRegionHeight(),4,4,0); // Draw current frame at (50, 50)
		spriteBatch.end();
		background.setWrap( Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);


	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		walkSheet.dispose();
	}
}
