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

	Spline upperSpline;
	SplineAnimator upperSplineAnimator;

	Spline lowerSpline;
	SplineAnimator lowerSplineAnimator;
	
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		int screenHeight = Gdx.graphics.getHeight();
		int screenWidth = Gdx.graphics.getWidth();

		Vector2 upperFrameStartPoint = new Vector2(0f, 0f);
		Vector2 lowerFrameStartPoint = new Vector2(0f, screenHeight/2);

		Vector2[] upperCPs1 = new Frame(20, screenWidth, screenHeight/2, upperFrameStartPoint).getRandomPoints();
		Vector2[] upperCPs2 = new Frame(20, screenWidth, screenHeight/2, upperFrameStartPoint).getRandomPoints();
		upperSpline = new Spline(Color.CYAN, upperCPs1);
		upperSplineAnimator = new SplineAnimator(upperSpline, upperCPs2);

		Vector2[] lowerCPs1 = new Frame(20, screenWidth, screenHeight/2, lowerFrameStartPoint).getRandomPoints();
		Vector2[] lowerCPs2 = new Frame(20, screenWidth, screenHeight/2, lowerFrameStartPoint).getRandomPoints();

		lowerSpline = new Spline(Color.CYAN, lowerCPs1);
		lowerSplineAnimator = new SplineAnimator(lowerSpline, lowerCPs2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		upperSpline.render(shapeRenderer);
		upperSplineAnimator.animate(0.01f);

		lowerSpline.dumbRender(shapeRenderer);
		lowerSplineAnimator.animate(0.01f);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
