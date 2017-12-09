package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityWell extends GameEntity implements Actor {

	private Entity targetEntity;
	private float angle;
	private float forceStrength = 30.0f;
	
	private ShapeGraphics wellGraphics;
	private static final String BACKGROUND_COLOR = "0xb1b9ba";
	
	/**
	 * Creates a new gravity well
	 * @param game
	 * @param position
	 * @param width : width of boost rectangle
	 * @param height : length of boost rectangle
	 * @param angle : orientation of gravity well (direction of force)
	 */
	public GravityWell(ActorGame game, Vector position, float width, float height, float angle) {
		super(game, true, position);
		
		// Rotate the gravity well
		this.angle = angle;
		getEntity().setAngularPosition(angle);;
		
		// Create part
		Polygon shape = new Polygon(
				-width/2, -height/2,
				-width/2,  height/2,
				 width/2,  height/2,
				 width/2, -height/2);
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(shape);
		partBuilder.setGhost(true);
		partBuilder.build();
		
		// Create graphics
		wellGraphics = new ShapeGraphics(shape, Color.decode(BACKGROUND_COLOR), null, 0.0f, 0.5f, 1000f);
		wellGraphics.setParent(getEntity());
		
		// Create contact listener
		ContactListener listener = new ContactListener() {
			
			@Override
			public void beginContact(Contact contact) {
				targetEntity = contact.getOther().getEntity();
			}
			
			@Override
			public void endContact(Contact contact) {
				targetEntity = null;
			}
			
		};
		getEntity().addContactListener(listener);
		
	}
	
	/**
	 * Sets the force of the gravity well
	 * @param force : a non-negative float
	 */
	public void setForce(float force) {
		if (force < 0.0f) {
			throw new IllegalArgumentException("Force must be positive or zero");
		}
	}
	
	@Override
	public void update(float deltaTime) {
		if (targetEntity != null) {
			targetEntity.applyForce(new Vector(forceStrength, 0.0f).rotated(angle), targetEntity.getPosition());
		}
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getPosition();
	}

	@Override
	public void draw(Canvas canvas) {
		wellGraphics.draw(canvas);
	}

}
