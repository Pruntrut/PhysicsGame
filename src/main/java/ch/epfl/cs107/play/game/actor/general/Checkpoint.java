package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Checkpoint extends Trigger {

	private ImageGraphics checkpointGraphics;
	
	private static final String SPRITE_HIT = "flag.green.png";
	private static final String SPRITE_NORMAL = "flag.blue.png";
		
	/**
	 * Creates a new Checkpoint, inactive after being triggered
	 * @param game
	 * @param fixed
	 * @param position
	 * @param radius : radius of spherical hitbox
	 */
	public Checkpoint(ActorGame game, Vector position, float radius) {
		super(game, position, new Circle(radius), false);
				
		// Make graphics
		checkpointGraphics = new ImageGraphics(SPRITE_NORMAL, 2*radius, 2*radius, new Vector(0.5f, 0.5f));
		checkpointGraphics.setParent(getEntity());
	}

	/**
	 * Creates a new Checkpoint of radius 1.0f
	 * @param game
	 * @param fixed
	 * @param position
	 */
	public Checkpoint(ActorGame game, Vector position) {
		this(game, position, 1.0f);
	}

	/**
	 * @return if payload has hit checkpoint
	 */
	public boolean isHit() {
		if (wasHitBy(getOwner().getPayload())) {
			checkpointGraphics.setName(SPRITE_HIT);
			return true;
		}
		
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		checkpointGraphics.draw(canvas);
	}

}
