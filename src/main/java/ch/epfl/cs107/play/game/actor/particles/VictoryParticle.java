package ch.epfl.cs107.play.game.actor.particles;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Particle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class VictoryParticle extends Particle {

	private ImageGraphics graphics;
	
	/**
	 * Creates a new VictoryParticle (star shaped) with a random color
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 */
	public VictoryParticle(Vector position, Vector velocity, Vector acceleration, float angularPosition,
			float angularVelocity, float angularAcceleration) {
		super(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration);
		
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
	
	private void buildGraphics() {
		// Select random star sprite
		String path = "star.";
		
		int index = (int) Math.floor(Math.random() / 4) * 4;
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
		graphics = new ImageGraphics(path, 0.05f, 0.05f, Vector.ZERO, 1.0f, 100.0f);
		graphics.setParent(this);
		
	}

	@Override
	public void draw(Canvas canvas) {
		graphics.draw(canvas);
	}

	@Override
	public Particle copy() {
		return new VictoryParticle(this);
	}

}
