package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.particles.GravityWellEmitter;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityWell extends GameEntity implements Actor {

	private GravityWellEmitter emitter;
	
	private Entity targetEntity;
	private float angle;
	private float forceStrength = 30.0f;
	
	private ShapeGraphics wellGraphics;
	private static final String BACKGROUND_COLOR = "0xb1b9ba";
	private static final String BORDER_COLOR = "0x717677";
	private static Polyline border;
	private float height;
	private float width;
	
	/**
	 * Creates a new gravity well
	 * @param game
	 * @param position
	 * @param width : width of boost rectangle, non-negative
	 * @param height : length of boost rectangle, non-negative
	 * @param angle : orientation of gravity well (direction of force)
	 */
	public GravityWell(ActorGame game, Vector position, float width, float height, float angle) {
		super(game, true, position);
		
		if (height < 0 || width < 0) {
			throw new IllegalArgumentException("Height and Width are non-negative");
		}

		this.height = height;
		this.width = width;
		
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
		wellGraphics = new ShapeGraphics(shape, Color.decode(BACKGROUND_COLOR), null, 0.0f, 0.3f, 1000f);
		wellGraphics.setParent(getEntity());
		
		// Create border
		border = new Polyline(0.0f, 0.0f, width, 0.0f);
		
		// Make the emitter (at the "start" of the gravity well)
		emitter = new GravityWellEmitter(getTransform().getOrigin().add(new Vector(-width/2, -height/2).rotated(angle)), forceStrength, angle, width, height);
		
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
		
		this.forceStrength = force;
		emitter.setForce(force);
	}
	
	@Override
	public void update(float deltaTime) {
		if (targetEntity != null) {
			targetEntity.applyForce(new Vector(forceStrength, 0.0f).rotated(angle), targetEntity.getPosition());
		}
		
		emitter.update(deltaTime);
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
		emitter.draw(canvas);
		
		Color color = Color.decode(BORDER_COLOR);
		
		canvas.drawShape(border, getTransform().translated(new Vector(-width/2, -height/2).rotated(angle)), color, color, 0.1f, 1.0f, 1000f);
		canvas.drawShape(border, getTransform().translated(new Vector(-width/2, height/2).rotated(angle)), color, color, 0.1f, 1.0f, 1000f);
	}

}
