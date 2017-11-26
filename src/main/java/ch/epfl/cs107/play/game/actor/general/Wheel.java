package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {

	private float radius;
	
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
