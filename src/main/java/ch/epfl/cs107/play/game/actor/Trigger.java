package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Trigger extends GameEntity implements Actor {

	// Contact
	private ContactListener listener;
	private Entity hitEntity;
	private boolean hit = false;
	
	// Timeout
	private boolean permanent;									// If Trigger has a timeout
	private boolean inactive = false;							// If Trigger is currently on timeout
	private float timeoutDuration = Float.POSITIVE_INFINITY;	// Initial duration of timeout
	private float timeoutLeft = Float.POSITIVE_INFINITY;		// Time left on timeout

	/**
	 * Creates a new Trigger with custom
	 * @param game
	 * @param fixed 
	 * @param position
	 * @param hitboxShape : the shape of the trigger hitbox, non null
	 * @param permanent : whether the trigger has a certain timeout
	 */
	public Trigger(ActorGame game, Vector position, Shape hitboxShape, boolean permanent) {
		super(game, true, position);
		
		if (hitboxShape == null) {
			throw new NullPointerException("hitboxShape cannot be null");
		}
		
		// Build part 
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(hitboxShape);
		partBuilder.setGhost(true);
		partBuilder.build();
		
		// Build contact listener
		listener = new ContactListener() {
			 @Override
			 public void beginContact(Contact contact) {
				 hitEntity = contact.getOther().getEntity();
				 hit = true;
			 }
			 
			 @Override
			 public void endContact(Contact contact) {}
		};
		getEntity().addContactListener(listener);
	
		this.permanent = permanent;
	}
	
	/**
	 * Creates a new permanent Trigger
	 * @param game
	 * @param fixed
	 * @param position
	 * @param hitboxShape
	 */
	public Trigger(ActorGame game, Vector position, Shape hitboxShape) {
		this(game, position, hitboxShape, true);
	}
	
	/**
	 * @return true if the contact listener has hit an entity
	 * 		   false if inactive or not hit
	 */
	public boolean isHit() {
		if (!permanent && inactive) {
			return false;
		} 
		
		return hit;
	}
	
	/**
	 * Returns true if entity detected by trigger is the same as entity passed as parameter
	 * 		   false if inactive of not hit by said entity
	 * @param other : the entity to be compared
	 * @return a boolean
	 */
	public boolean wasHitBy(GameEntity other) {
		if (!permanent && inactive) {
			return false;
		}
		
		return other.isSameEntity(hitEntity);
	}
	
	/**
	 * If not permanent, sets the Trigger to inactive for a certain amount of time
	 * @param duration
	 */
	public void setInactive(float duration) {
		if (!permanent) {
			this.timeoutDuration = duration;
			this.timeoutLeft = duration;
			
			inactive = true;
		}
	}
	
	/**
	 * If not permanent, sets the Trigger to inactive until reset
	 */
	public void setInactive() {
		setInactive(Float.POSITIVE_INFINITY);
	}

	/**
	 * @return if the trigger is currently inactive
	 */
	public boolean isInactive() {
		if (permanent) {
			return false;
		} else {
			return inactive;
		}
	}
	
	/**
	 * Resets the trigger to active and not hit
	 */
	protected void reset() {
		hit = false;
		hitEntity = null;
		
		inactive = false;
		timeoutLeft = timeoutDuration;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		reset();
	}

	@Override
	public void update(float deltaTime) {
		
		if (!permanent && inactive) {
			// Decrement time left on timeout
			timeoutLeft -= deltaTime;
			
			// If timeout finished, return to normal (active)
			if (timeoutLeft <= 0) {
				reset();
			}
		}
	}
	
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getPosition() {
		return getEntity().getPosition();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	
	

}
