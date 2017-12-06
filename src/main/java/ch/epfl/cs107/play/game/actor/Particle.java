package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Particle implements Graphics, Positionable {

	private Vector position;
	private Vector velocity;
	private Vector acceleration;
	
	private float angularPosition;
	private float angularVelocity;
	private float angularAcceleration;

	@Override
	public abstract void draw(Canvas canvas);
	
	public void update(float deltaTime) {
		velocity = velocity.add(acceleration.mul(deltaTime));
		position = position.add(velocity.mul(deltaTime));
		angularVelocity += angularAcceleration * deltaTime;
		angularPosition += angularVelocity * deltaTime;
	}

	@Override
	public Transform getTransform() {
		return Transform.I.rotated(angularPosition).translated(position);
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

}
