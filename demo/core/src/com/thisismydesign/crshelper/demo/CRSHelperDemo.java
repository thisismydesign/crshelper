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
import com.thisismydesign.crshelper.shape.spline.SplineAnimator;

public class CRSHelperDemo extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	Spline spline;
	SplineAnimator splineAnimator;
	
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		int screenHeight = Gdx.graphics.getHeight();
		int screenWidth = Gdx.graphics.getWidth();
		Vector2[] cps1 = new Frame(10, screenWidth, screenHeight).getRandomPoints();
		Vector2[] cps2 = new Frame(10, screenWidth, screenHeight).getRandomPoints();

		spline = new Spline(Color.CYAN, cps1);
		splineAnimator = new SplineAnimator(spline, cps2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spline.render(shapeRenderer);
		splineAnimator.animate(0.01f);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
