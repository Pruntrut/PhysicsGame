package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {

	private float radius;
	private static final float FRICTION = 1f; 
	
	private WheelConstraint constraint;		// The constraint attaching the wheel to its vehicle
	private Entity vehicle;					// The vehicle it is attached to 
	
	private ShapeGraphics tire;
	private List<ShapeGraphics> spokes = new ArrayList<>();
	
	/**
	 * Creates a new Wheel
	 * @param game
	 * @param fixed
	 * @param position
	 * @param radius : non-negative
	 */
	public Wheel(ActorGame game, boolean fixed, Vector position, float radius) {
		super(game, fixed, position);
		
		if (radius <= 0.0f) {
			throw new IllegalArgumentException("Radius must be positive");
		}
		
		this.radius = radius;
		
		buildPart();
		makeGraphics();
	}

	/**
	 * Creates a new Wheel at origin
	 * @param game
	 * @param fixed
	 * @param radius : non-negative
	 */
	public Wheel(ActorGame game, boolean fixed, float radius) {
		super(game, fixed);
		
		if (radius <= 0.0f) {
			throw new IllegalArgumentException("Radius must be positive");
		}
		
		this.radius = radius;
		
		buildPart();
		makeGraphics();
	}
	
	/**
	 * Creates physical part
	 */
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Circle circle = new Circle(radius);
		partBuilder.setShape(circle);
		partBuilder.setFriction(FRICTION);
		partBuilder.build();
	}
	
	/**
	 * Creates the wheel graphics
	 */
	private void makeGraphics() {
		tire = new ShapeGraphics(new Circle(radius), null, new Color(0x0f0d0e), 0.1f);
		tire.setParent(getEntity());
		
		int numberOfSpokes = 10;
		
		for (int i = 0; i < numberOfSpokes; i++) {
			float angle = (float) (2*Math.PI/numberOfSpokes * i);
			
			Polyline spoke = new Polyline(new Vector(-radius, 0.0f).rotated(angle), new Vector(radius, 0.0f).rotated(angle));
			ShapeGraphics spokeGraphics = new ShapeGraphics(spoke, null, Color.BLACK, 0.025f);
			spokeGraphics.setParent(getEntity());
			spokes.add(spokeGraphics);
		}
	}
	
	/**
	 * Attaches wheel to other entity
	 * @param vehicle
	 * @param anchor
	 * @param axis : axis along which wheel can move
	 */
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
	
	/**
	 * Sets motor speed of wheel
	 * @param speed
	 */
	public void power(float speed) {
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(speed);
	}
	
	/**
	 * Stop motor and rolls freely
	 */
	public void relax() {
		constraint.setMotorEnabled(false);
	}
	
	/**
	 * Destroys wheel constraint
	 */
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
	
	/**
	 * @return the angular position of the wheel
	 */
	public float getAngularPositon() {
		return getEntity().getAngularPosition();
	}

	@Override
	public void draw(Canvas canvas) {
		tire.draw(canvas);
		
		for (ShapeGraphics spoke : spokes) {
			spoke.draw(canvas);
		}
	}

}
