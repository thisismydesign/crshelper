package com.thisismydesign.crshelper.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.screen.Frame;
import com.thisismydesign.crshelper.shape.spline.Spline;

public class CRSHelperDemo extends ApplicationAdapter {
	SpriteBatch batch;

	Spline spline;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();

		int screenHeight = Gdx.graphics.getHeight();
		int screenWidth = Gdx.graphics.getWidth();
		Vector2[] cps = new Frame(10, screenWidth, screenHeight).getRandomPoints();

		spline = new Spline(Color.CYAN, cps, cps);

		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spline.render(shapeRenderer);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
