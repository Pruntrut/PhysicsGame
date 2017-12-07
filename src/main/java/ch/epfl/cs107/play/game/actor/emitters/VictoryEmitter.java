package ch.epfl.cs107.play.game.actor.emitters;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Emitter;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.particles.VictoryParticle;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;

public class VictoryEmitter extends Emitter {
	
	private static final float HEIGHT = 0.67f;
	private static final float WIDTH = 1.0f;
	private static final Polygon SHAPE = new Polygon(
			0.0f, 0.0f,
			HEIGHT, 0.0f,
			HEIGHT, WIDTH,
			0.0f, WIDTH
	);
	
	private static final int PARTICLE_LIMIT = 35;
	
	/**
	 * Creates a new instance of VictoryEmitter
	 * @param position : absolute position
	 * @param color : must be either BLUE, RED, GREEN, or YELLOW
	 */
	public VictoryEmitter(Vector position, Color color) {
		super(SHAPE, position, PARTICLE_LIMIT);
		
		// Set graphical representation
		setGraphics(buildGraphics(color));
	}
	
	/**
	 * Builds graphics of emitter
	 * @param color : must be either BLUE, RED, GREEN, or YELLOW
	 * @return the image graphics (w/o parent)
	 */
	private ImageGraphics buildGraphics(Color color) {
		String path = "button.";
		
		if (color == Color.BLUE) {
			path += "blue";
		} else if (color == Color.RED) {
			path += "red";
		} else if (color == Color.GREEN) {
			path += "green";
		} else if (color == Color.YELLOW) {
			path += "yellow";
		}
		
		path += ".png";
		
		ImageGraphics graphics = new ImageGraphics(path, WIDTH, HEIGHT, new Vector(0.5f, 0.0f), 1.0f, 100.0f);
		graphics.setParent(this);
		
		return graphics;
	}
	
	/**
	 * Creates n particles and adds them to the list
	 * @param number
	 */
	@Override
	protected void createParticles(int number) {
		for (int i = 0; i < number; i++) {
			// Angle between -pi/4 and pi/4
			float velocityAngle = (float)(-Math.PI/6 + (Math.random() * (Math.PI/3)));
			
			Vector position = getTransform().getOrigin();
			Vector velocity = new Vector(0.0f, 6.0f).rotated(velocityAngle);
			Vector acceleration = new Vector(0.0f, -3.0f);
			
			// Angular velocity between 1 and -1
			float angularVelocity = -1.0f + (float)(Math.random() * 2.0);
			
			// Duration between 0.5 and 2.0s
			float duration = 0.5f + (float)(Math.random() * 3);
			
			VictoryParticle particle = new VictoryParticle(position, velocity, acceleration, 0.0f, angularVelocity, 0.0f, duration);
			addParticle(particle);
		}
	}
}
