package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends GameEntity implements Actor {
	
	private float hitboxRadius;
	
	private Circle hitbox;
	private boolean hit = false;
	
	private ImageGraphics flagGraphics;
	

	public Finish(ActorGame game, boolean fixed, Vector position, float radius) {
		super(game, fixed, position);
	
		if (radius <= 0.0f) {
			throw new IllegalArgumentException("Radius must be positive");
		}
		
		hitboxRadius = radius;
		
		// Build part
		PartBuilder partBuilder = getEntity().createPartBuilder();
		hitbox = new Circle(hitboxRadius);
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();
		
		// Make graphics
		flagGraphics = new ImageGraphics("flag.red.png", 2*hitboxRadius, 2*hitboxRadius, new Vector(0.5f, 0.5f));
		flagGraphics.setParent(getEntity());
		
		// Collision listener
		ContactListener listener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				// Get the game's main entity (e.g. bike)
				GameEntity main = getOwner().getPayload();
				
				// If main entity has hit finish zone
				if (main.isSameEntity(contact.getOther().getEntity())) {
					hit = true;
				}
			}

			@Override
			public void endContact(Contact contact) {}

		};
		getEntity().addContactListener(listener);
	}
	
	public Finish(ActorGame game, boolean fixed, Vector position) {
		this(game, fixed, position, 1.0f);
	}

	public boolean isHit() {
		return hit;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		hit = false;
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
		flagGraphics.draw(canvas);
	}

}
