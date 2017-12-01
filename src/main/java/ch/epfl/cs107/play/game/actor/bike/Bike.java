package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {

	private static final float MAX_WHEEL_SPEED = 20.0f;
	private static final float WHEEL_RADIUS = 0.5f;
	private boolean lookingLeft = false;
	
	private Wheel leftWheel;
	private Wheel rightWheel;
	
	private ShapeGraphics[] bodyGraphics;
	
	private Polygon hitbox = new Polygon(
		0.0f, 0.5f,
		0.5f, 1.0f,
		0.0f, 2.0f,
		-0.5f, 1.0f
	);
	
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		
		buildWheels(game, fixed, Vector.ZERO);
		buildPart();
		
		makeGraphics();
	}

	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		buildWheels(game, fixed, position);
		buildPart();
		
		makeGraphics();
	}
	
	private void buildWheels(ActorGame game, boolean fixed, Vector position) {
		leftWheel = new Wheel(game, fixed, position.add(new Vector(-1.0f, 0.0f)), WHEEL_RADIUS);
		rightWheel = new Wheel(game, fixed, position.add(new Vector(1.0f, 0.0f)), WHEEL_RADIUS);
		
		leftWheel.attach(getEntity(), new Vector(-1.0f,  0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getEntity(), new Vector(1.0f,  0.0f), new Vector(0.5f, -1.0f));
		
		//leftWheel.power(MAX_WHEEL_SPEED);
	}
	
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();
	}
	
	private void makeGraphics() {
		
		Color color = Color.WHITE;
		float thickness = 0.2f;
		bodyGraphics = new ShapeGraphics[5];
		
		// Draw head
		Circle head = new Circle(0.2f, getHeadLocation());
		bodyGraphics[0] = new ShapeGraphics(head, color, color, thickness);
		
		// Draw arm
		Polyline arm = new Polyline(
			getShoulderLocation(), 
			getHandLocation()
		);
		bodyGraphics[1] = new ShapeGraphics(arm, color, color, thickness);
		
		// Draw back
		Polyline back = new Polyline(
			getShoulderLocation(), 
			getHipLocation(),
			getKneeLocation()
		);
		bodyGraphics[2] = new ShapeGraphics(back, null, color, thickness);
		
		// Draw first leg
		Polyline firstLeg = new Polyline(
			getKneeLocation(),
			getFirstLegLocation()
		);
		bodyGraphics[3] = new ShapeGraphics(firstLeg, color, color, thickness);
		
		// Draw second leg
		Polyline secondLeg = new Polyline(
			getKneeLocation(),
			getSecondLegLocation()
		);
		bodyGraphics[4] = new ShapeGraphics(secondLeg, color, color, thickness);
		
		// Add parent entity to every body part
		for (ShapeGraphics bodyPart : bodyGraphics) {
			bodyPart.setParent(getEntity());
		}
	}
	
	private float getAlign() {
		if (lookingLeft) {
			return -1.0f;
		}
		
		return 1.0f;
	}
	
	private Vector getHeadLocation() {
		return new Vector(0.0f, 2.0f);
	}
	
	private Vector getShoulderLocation() {
		return new Vector(getAlign() * -0.1f, 1.7f);
	}
	
	private Vector getHandLocation() {
		return new Vector(getAlign() * 0.5f, 1.0f);
	}
	
	private Vector getHipLocation() {
		return new Vector(getAlign() * -0.5f, 1.0f);
	}
	
	private Vector getKneeLocation() {
		return new Vector(getAlign() * 0.1f, 0.6f);
	}
	
	private Vector getFirstLegLocation() {
		return new Vector(0.3f, -0.1f);
	}
	
	private Vector getSecondLegLocation() {
		return new Vector(-0.3f, -0.1f);
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
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		
		// Draw body parts
		for (ShapeGraphics bodyPart : bodyGraphics) {
			bodyPart.draw(canvas);
		}
	}

}
