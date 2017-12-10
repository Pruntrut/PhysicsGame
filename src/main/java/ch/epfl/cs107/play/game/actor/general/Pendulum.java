package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Pendulum extends GameEntity implements Actor {

	private static final float HEIGHT = 0.6f;
	private static final float WIDTH = 1.0f;
	
	private static final String BASE_SPRITE = "metal.5.png";
	private static final String BALL_SPRITE = "stone.11.png";
	private static final String ROPE_COLOR = "0xbc7641";
	
	private Entity ball;
	
	private ImageGraphics baseGraphics;
	private ImageGraphics ballGraphics;
	
	/**
	 * Creates a new Pendulum
	 * @param game
	 * @param position
	 * @param ballRadius : radius of oscillation ball
	 * @param ropeLength : length of rope
	 * @param frequency : frequency of ocillation
	 */
	public Pendulum(ActorGame game, Vector position, float ballRadius, float ropeLength) {
		super(game, true, position);
		
		if (ballRadius <= 0.0f) {
			throw new IllegalArgumentException("ballRadius must be larger than 0");
		}
		if (ropeLength < 1.0f) {
			throw new IllegalArgumentException("ropeLength must be at least 1");
		}
				
		// Build base part
		PartBuilder baseBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(0.0f,0.0f, 0.0f,HEIGHT, WIDTH,HEIGHT, WIDTH,0.0f);
		baseBuilder.setShape(polygon);
		baseBuilder.build();
		
		// Build ball entity
		ball = getOwner().createEntity(false, getEntity().getPosition().add(new Vector(0.0f, -ropeLength)));
		
		// Build ball part
		PartBuilder ballBuilder = ball.createPartBuilder();
		Circle circle = new Circle(ballRadius);
		ballBuilder.setShape(circle);
		ballBuilder.build();
		
		// Graphics for the base
		baseGraphics = new ImageGraphics(BASE_SPRITE, WIDTH, HEIGHT, Vector.ZERO, 1.0f, 100.0f);
		baseGraphics.setParent(getEntity());
		
		// Graphics for the ball
		ballGraphics = new ImageGraphics(BALL_SPRITE, 2*ballRadius, 2*ballRadius, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		ballGraphics.setParent(ball);
				
		// Rope constraint
		RopeConstraintBuilder ropeConstraintBuilder = getOwner().getRopeConstraintBuilder();
		ropeConstraintBuilder.setFirstEntity(getEntity());
		ropeConstraintBuilder.setFirstAnchor(new Vector(WIDTH/2, HEIGHT/2));
		ropeConstraintBuilder.setSecondEntity(ball);
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
		ropeConstraintBuilder.setMaxLength(ropeLength + HEIGHT/2);
		ropeConstraintBuilder.setInternalCollision(false);
		ropeConstraintBuilder.build();
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return Vector.ZERO;
	}
	
	/**
	 * Sets velocity of ball
	 * @param velocity
	 */
	public void setVelocity(Vector velocity) {
		ball.setVelocity(velocity);
	}

	@Override
	public void draw(Canvas canvas) {
		baseGraphics.draw(canvas);
		ballGraphics.draw(canvas);
		
		// Draw the rope
		Polyline ropeShape = new Polyline(ball.getPosition(), getTransform().onPoint(WIDTH/2, HEIGHT/2));
		canvas.drawShape(ropeShape, Transform.I, null, Color.decode(ROPE_COLOR), 0.1f, 1.0f, 99.0f);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		ball.destroy();
	}

}
