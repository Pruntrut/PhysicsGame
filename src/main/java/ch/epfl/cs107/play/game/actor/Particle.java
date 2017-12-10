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
	
	// Timeout attributes
	private boolean permanent = true;
	private float duration = 1.0f;
	private float timeLeft = 1.0f;
	
	/**
	 * Creates a new Particle
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 * @param permanent : whether particle expires after some time (default: 1s)
	 */
	public Particle(Vector position, Vector velocity, Vector acceleration, float angularPosition, float angularVelocity,
			float angularAcceleration, boolean permanent) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.angularPosition = angularPosition;
		this.angularVelocity = angularVelocity;
		this.angularAcceleration = angularAcceleration;
		this.permanent = permanent;
	}
	
	/**
	 * Creates a new Particle (permanent)
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 */
	public Particle(Vector position, Vector velocity, Vector acceleration, float angularPosition, float angularVelocity,
			float angularAcceleration) {
		this(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration, true);
	}
	
	public Particle(Particle other) {
		this.position = other.position;
		this.velocity = other.velocity;
		this.acceleration = other.acceleration;
		this.angularPosition = other.angularPosition;
		this.angularVelocity = other.angularVelocity;
		this.angularAcceleration = other.angularAcceleration;
		this.permanent = other.permanent;
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
		if (!isExpired()) {
			velocity = velocity.add(acceleration.mul(deltaTime));
			position = position.add(velocity.mul(deltaTime));
			angularVelocity += angularAcceleration * deltaTime;
			angularPosition += angularVelocity * deltaTime;
			
			timeLeft -= deltaTime;
		}
	}
	
	/**
	 * If particle is not permanent, sets its duration (timeout)
	 * @param duration : in seconds
	 */
	public void setDuration(float duration) {
		if (!permanent) {
			this.duration = duration;
			this.timeLeft = duration;
		}
	}
	
	/**
	 * @return the duration of the particle (Float.POSITIVE_INFINITY if permanent)
	 */
	public float getDuration() {
		if (permanent) {
			return Float.POSITIVE_INFINITY;
		} else {
			return duration;
		}
	}
	
	/**
	 * @return the time left (Float.POSITIVE_INFINITY if permanent)
	 */
	public float getTimeLeft() {
		if (permanent) {
			return Float.POSITIVE_INFINITY;
		} else {
			return timeLeft;
		}
	}
	
	/**
	 * @return if the particle has no time left returns true, if permanent or time left -> false
	 */
	public boolean isExpired() {
		return permanent || timeLeft <= 0.0f;
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
