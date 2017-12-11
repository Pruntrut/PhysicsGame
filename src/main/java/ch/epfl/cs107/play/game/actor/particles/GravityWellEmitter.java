package ch.epfl.cs107.play.game.actor.particles;

import ch.epfl.cs107.play.game.actor.Emitter;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Vector;

public class GravityWellEmitter extends Emitter {

	private static final int PARTICLE_LIMIT = 25;
		
	
	private float force;
	private float angle;
	private float length;
	
	/**
	 * Creates a new GravityWellEmitter
	 * @param position
	 * @param force : force of emitter
	 * @param angle : angle at which 
	 * @param length : distance particles have to travel (usually length of gravity well)
	 * @param height : height of emitter
	 */
	public GravityWellEmitter(Vector position, float force, float angle, float length, float height) {
		super(createShape(angle, length, height), position, PARTICLE_LIMIT);
		
		this.angle = angle;
		this.force = force;
		this.length = length;
	}

	/**
	 * Creates a polygon with 0.1*length and 1*height, rotated by angle
	 * @param angle
	 * @param length
	 * @param height
	 * @return the shape
	 */
	private static Polygon createShape(float angle, float length, float height) {
		Polygon shape = new Polygon(
				new Vector(0.0f, 0.0f),
				new Vector(0.0f, height).rotated(angle),
				new Vector(0.5f*length, height).rotated(angle),
				new Vector(0.5f*length, 0.0f).rotated(angle)
		);
		
		return shape;
	}
	
	/**
	 * Sets the force of the emitter
	 * @param force
	 */
	public void setForce(float force) {
		this.force = force;
	}
	
	/**
	 * Creates one particle and adds it to the list
	 * @param number : upper bound of particles created
	 */
	@Override
	protected void createParticles(int number) {
		for (int i = 0; i < number; i++) {
			Vector postionRelToEmitter = getShape().sample();
			Vector position = getTransform().onPoint(postionRelToEmitter);
			Vector side = new Vector(length, 0.0f);
			Vector absPostionRelToEmitter = new Vector(Math.abs(postionRelToEmitter.x), Math.abs(postionRelToEmitter.y));
			
			float distanceLeft = length - side.dot(absPostionRelToEmitter)/length;
			
			GravityWellParticle gwParticle = new GravityWellParticle(position, force, angle, distanceLeft);
			addParticle(gwParticle);
		}

	}

}
