package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {

	private float radius;
	
	private WheelConstraint constraint;		// The constraint attaching the wheel to its vehicle
	private Entity vehicle;					// The vehicle it is attached to 
	
	private static final String WHEEL_SPRITE = "explosive.11.png";
	private ImageGraphics wheelGraphics;
	
	public Wheel(ActorGame game, boolean fixed, Vector position, float radius) {
		super(game, fixed, position);
		this.radius = radius;
		
		buildPart();
		makeGraphics();
	}

	public Wheel(ActorGame game, boolean fixed, float radius) {
		super(game, fixed);
		this.radius = radius;
		
		buildPart();
		makeGraphics();
	}
	
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(radius);
		partBuilder.setShape(circle);
		partBuilder.build();
	}
	
	private void makeGraphics() {
		wheelGraphics = new ImageGraphics(WHEEL_SPRITE, radius * 2.0f, radius * 2.0f, new Vector(radius, radius));
		wheelGraphics.setParent(getEntity());
	}
	
	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		this.vehicle = vehicle;
		
		WheelConstraintBuilder constraintBuilder = getOwner().getWheelContraintBuilder();
		constraintBuilder.setFirstEntity(vehicle);			// First entity (block)
		constraintBuilder.setFirstAnchor(anchor);			// First anchor point (on block)
		constraintBuilder.setSecondEntity(getEntity());		// Second entity (this wheel)
		constraintBuilder.setSecondAnchor(Vector.ZERO);		// Second anchor point (center of wheel)
		constraintBuilder.setAxis(axis);					// Axis along which wheel can move
		constraintBuilder.setFrequency(3.0f);				// Spring frequency 
		constraintBuilder.setDamping(0.5f);					// Spring damping 
		constraintBuilder.setMotorMaxTorque(10.0f);			// Max torque applicable to wheel
		constraint = constraintBuilder.build();
	}
	
	public void power(float speed) {
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(speed);
	}
	
	public void relax() {
		constraint.setMotorEnabled(false);
	}
	
	public void detach() {
		constraint.destroy();
	}
	
	/**
	 * @return relative rotation speed, in radians per second
	 */
	public float getSpeed() {
		if (vehicle != null) {
			return getEntity().getAngularVelocity() - vehicle.getAngularVelocity();
		} else {
			return getEntity().getAngularVelocity();
		}
	}
	
	@Override
	public void destroy() {
		detach();
		super.destroy();
	}
	
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}

	@Override
	public void draw(Canvas canvas) {
		wheelGraphics.draw(canvas);
	}

}
