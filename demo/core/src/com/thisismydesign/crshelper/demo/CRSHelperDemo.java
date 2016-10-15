package com.thisismydesign.crshelper.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thisismydesign.crshelper.iterator.Precision;
import com.thisismydesign.crshelper.screen.Frame;
import com.thisismydesign.crshelper.shape.Line;
import com.thisismydesign.crshelper.shape.spline.Spline;
import com.thisismydesign.crshelper.shape.spline.SplineAnimator;

import java.util.List;

public class CRSHelperDemo extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	Precision precision;

	DemoXFlatSpline lowerSpline;
	SplineAnimator lowerSplineAnimator;

	DemoSpline upperSpline;
	SplineAnimator upperSplineAnimator;

	Line upperMiddleLine;
	Line lowerMiddleLine;
	Line verticalMiddleLine;
	Line diagonalMiddleLine;

	final int sampleCount = 100;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		int screenHeight = Gdx.graphics.getHeight();
		int screenWidth = Gdx.graphics.getWidth();

		precision = new Precision(1f);
		final int maxPointRangeInPercent = 100;

		System.out.println(precision.getAllowedErrorInPixels());

		Vector2 upperFrameStartPoint = new Vector2(0f, 0f);
		Vector2 lowerFrameStartPoint = new Vector2(0f, screenHeight/2);

		Vector2[] upperCPs1 = new Frame(10, screenWidth, screenHeight/2, upperFrameStartPoint).getRandomPoints(maxPointRangeInPercent);
		Vector2[] upperCPs2 = new Frame(10, screenWidth, screenHeight/2, upperFrameStartPoint).getRandomPoints(maxPointRangeInPercent);
		lowerSpline = new DemoXFlatSpline(upperCPs1, precision);
		lowerSplineAnimator = new SplineAnimator(lowerSpline, upperCPs2);

		upperSpline = new DemoSpline(lowerSpline);
		upperSpline.move(lowerFrameStartPoint);
		upperSplineAnimator = new SplineAnimator(upperSpline, new Spline(upperCPs2, precision).move(lowerFrameStartPoint).getControlPoints());

		upperMiddleLine = new Line(new Vector2(screenWidth/2, screenHeight/2), new Vector2(screenWidth/2, screenHeight), 2);
		lowerMiddleLine = new Line(new Vector2(screenWidth/2, screenHeight/2), new Vector2(screenWidth/2, 0), 2);
		verticalMiddleLine = new Line(new Vector2(screenWidth /2, screenHeight /2), new Vector2(screenWidth /2, screenHeight /2), 2);
		diagonalMiddleLine = new Line(new Vector2(0f,0f), new Vector2(screenWidth, screenHeight), 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		lowerSpline.render(shapeRenderer, Color.CYAN);
		lowerSpline.renderDebug(shapeRenderer);
		lowerSplineAnimator.animate(0.001f);

		upperSpline.dumbRender(shapeRenderer);
		upperSplineAnimator.animate(0.001f);

		diagonalMiddleLine.render(shapeRenderer, Color.MAGENTA);

		List<Vector2> intersections = lowerSpline.mathIntersect(diagonalMiddleLine);
		for (Vector2 intersection : intersections) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.circle(intersection.x, intersection.y, 5);
			shapeRenderer.end();
		}

		intersections = upperSpline.intersect(diagonalMiddleLine);
		for (Vector2 intersection : intersections) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.circle(intersection.x, intersection.y, 5);
			shapeRenderer.end();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private double approxRollingAverage (double avg, double newSample) {
		avg -= avg / sampleCount;
		avg += newSample / sampleCount;

		return avg;
	}

	public void draw(String str, Vector2 position) {
		SpriteBatch spriteBatch = new SpriteBatch();
		BitmapFont font = new BitmapFont();

		spriteBatch.begin();
		font.draw(spriteBatch, str, position.x, position.y);
		spriteBatch.end();
	}
}
