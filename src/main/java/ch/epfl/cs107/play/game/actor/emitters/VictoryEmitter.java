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
	
	private static final int PARTICLE_LIMIT = 100;
	
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
		
		ImageGraphics graphics = new ImageGraphics(path, WIDTH, HEIGHT, Vector.ZERO, 1.0f, 100.0f);
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
			Vector position = getTransform().getOrigin();
			Vector velocity = new Vector(0.0f, 10.0f);
			Vector acceleration = new Vector(0.0f, -3.0f);
			
			float angularVelocity = 1.0f;
			
			VictoryParticle particle = new VictoryParticle(position, velocity, acceleration, 0.0f, angularVelocity, 0.0f);
			addParticle(particle);
		}
	}
}
