package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends Trigger {

	private ImageGraphics flagGraphics;

	/**
	 * Creates a new Finish
	 * @param game
	 * @param fixed
	 * @param position
	 * @param radius : radius of spherical hitbox
	 */
	public Finish(ActorGame game, Vector position, float radius) {
		super(game, position, new Circle(radius));
				
		// Make graphics
		flagGraphics = new ImageGraphics("flag.red.png", 2*radius, 2*radius, new Vector(0.5f, 0.5f));
		flagGraphics.setParent(getEntity());
		
	}
	
	/**
	 * Creates a new Finish with radius 1.0f
	 * @param game
	 * @param fixed
	 * @param position
	 */
	public Finish(ActorGame game, boolean fixed, Vector position) {
		this(game, position, 1.0f);
	}

	/**
	 * @return if ActorGame's payload has hit Finish
	 */
	@Override
	public boolean isHit() {
		return wasHitBy(getOwner().getPayload()) && getOwner().getPayload().isAlive();
	}

	@Override
	public void draw(Canvas canvas) {
		flagGraphics.draw(canvas);
	}

}
