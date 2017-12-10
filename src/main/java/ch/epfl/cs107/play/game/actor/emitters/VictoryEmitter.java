package ch.epfl.cs107.play.game.actor.emitters;

import ch.epfl.cs107.play.game.actor.Emitter;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.particles.VictoryParticle;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class VictoryEmitter extends Emitter {
	
	private static final float HEIGHT = 1.0f;
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
	public VictoryEmitter(Vector position) {
		super(SHAPE, position, PARTICLE_LIMIT);
		
		// Set graphical representation
		setGraphics(buildGraphics());
	}
	
	/**
	 * Builds graphics of emitter
	 * @param color : must be either BLUE, RED, GREEN, or YELLOW
	 * @return the image graphics (w/o parent)
	 */
	private ImageGraphics buildGraphics() {
		String path = "blaster.png";
		
		ImageGraphics graphics = new ImageGraphics(path, WIDTH, HEIGHT, new Vector(HEIGHT/2 + 0.1f, WIDTH/2 + 0.1f), 1.0f, 100.0f);
		graphics.setParent(this);
		graphics.setRelativeTransform(Transform.I.rotated((float)Math.PI/2));
		return graphics;
	}
	
	/**
	 * Creates one victory particle and adds it to the list
	 * @param number : ignored in this case
	 */
	@Override
	protected void createParticles(int number) {
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
