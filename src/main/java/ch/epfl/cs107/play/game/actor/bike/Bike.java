package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {

	private static final float MAX_WHEEL_SPEED = 20.0f;
	private boolean lookingLeft = true;
	
	private Wheel leftWheel;
	private Wheel rightWheel;
	
	private Polygon hitbox = new Polygon(
			0.0f, 0.5f,
			0.5f, 1.0f,
			0.0f, 2.0f,
			-0.5f, 1.0f
		);
	
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		
		buildPart();
	}

	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		buildPart();
	}
	
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();
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
		
	}

}
