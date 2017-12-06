package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Particle implements Graphics, Positionable {

	// Translational attributes
	private Vector position;
	private Vector velocity;
	private Vector acceleration;
	
	// Angular attributes
	private float angularPosition;
	private float angularVelocity;
	private float angularAcceleration;
	
	/**
	 * Creates a new Particle
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 */
	public Particle(Vector position, Vector velocity, Vector acceleration, float angularPosition, float angularVelocity,
			float angularAcceleration) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.angularPosition = angularPosition;
		this.angularVelocity = angularVelocity;
		this.angularAcceleration = angularAcceleration;
	}
	
	public Particle(Particle other) {
		this.position = other.position;
		this.velocity = other.velocity;
		this.acceleration = other.acceleration;
		this.angularPosition = other.angularPosition;
		this.angularVelocity = other.angularVelocity;
		this.angularAcceleration = other.angularAcceleration;
	}

	/**
	 * Draw particle on canvas
	 * @param canvas
	 */
	@Override
	public abstract void draw(Canvas canvas);
	
	/**
	 * @return cloned particle
	 */
	public abstract Particle copy();
	
	/**
	 * Updates position of particle
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		velocity = velocity.add(acceleration.mul(deltaTime));
		position = position.add(velocity.mul(deltaTime));
		angularVelocity += angularAcceleration * deltaTime;
		angularPosition += angularVelocity * deltaTime;
	}

	/**
	 * @return transformation
	 */
	@Override
	public Transform getTransform() {
		return Transform.I.rotated(angularPosition).translated(position);
	}

	/**
	 * @return velocity of particle
	 */
	@Override
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * @return position of particle in absolute coordinates
	 */
	@Override
	public Vector getPosition() {
		return position;
	}

}
