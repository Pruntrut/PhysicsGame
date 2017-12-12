package ch.epfl.cs107.play.game.actor.particles;

import ch.epfl.cs107.play.game.actor.ImageParticle;
import ch.epfl.cs107.play.math.Vector;

public class VictoryParticle extends ImageParticle {

	private static final float LENGTH = 0.3f;
	
	
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
		super(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration, false, getGraphicsPath(), 
				LENGTH, LENGTH, 0.8f, 100f);
		
		setDuration(duration);
	}

	/**
	 * Copies another particle
	 * @param other
	 */
	public VictoryParticle(VictoryParticle other) {
		super(other);		
	}
	
	/** 
	 * Creates the graphics (with a random star colour
	 */
	private static String getGraphicsPath() {
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
		
		return path;	
	}

	@Override
	public VictoryParticle copy() {
		return new VictoryParticle(this);
	}

}
