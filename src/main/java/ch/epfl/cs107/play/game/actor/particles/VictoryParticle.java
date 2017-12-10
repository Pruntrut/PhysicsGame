package ch.epfl.cs107.play.game.actor.particles;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Particle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class VictoryParticle extends Particle {

	private static final float LENGTH = 0.3f;
	
	private ImageGraphics graphics;
	
	/**
	 * Creates a new VictoryParticle (star shaped) with a random color and non permanent
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 */
	public VictoryParticle(Vector position, Vector velocity, Vector acceleration, float angularPosition,
			float angularVelocity, float angularAcceleration, float duration) {
		super(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration, false);
		
		setDuration(duration);
		buildGraphics();
	}

	/**
	 * Copies another particle
	 * @param other
	 */
	public VictoryParticle(VictoryParticle other) {
		super(other);
		this.graphics = other.graphics;
		
	}
	
	/** 
	 * Creates the graphics (with a random star colour
	 */
	private void buildGraphics() {
		// Select random star sprite
		String path = "star.";
		int index = (int)(Math.random() * 4);
		switch (index) {
			case 0:
				path += "diamond";
				break;
			case 1: 
				path += "gold";
				break;
			case 2: 
				path += "silver";
				break;
			case 3: 
				path += "bronze";
				break;
		}
		path += ".png";
		
		// Build graphics
		graphics = new ImageGraphics(path, LENGTH, LENGTH, Vector.ZERO, 0.5f, 100.0f);
		graphics.setParent(this);
		
	}

	@Override
	public void draw(Canvas canvas) {
		if (!isExpired()) {
			
			// If time left is last 25%, start to fade out
			if (getTimeLeft() < 0.25f * getDuration()) {
				graphics.setAlpha(getTimeLeft() / (0.25f * getDuration())); 
			} else {
				graphics.setAlpha(1.0f);
			}
			
			graphics.draw(canvas);
		}
	}

	@Override
	public Particle copy() {
		return new VictoryParticle(this);
	}

}
