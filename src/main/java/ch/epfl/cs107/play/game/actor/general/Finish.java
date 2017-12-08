package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends Trigger {

	private ImageGraphics flagGraphics;
	private float radius;

	public Finish(ActorGame game, boolean fixed, Vector position, float radius) {
		super(game, fixed, position, new Circle(radius));
		
		this.radius = radius;
		
		// Make graphics
		flagGraphics = new ImageGraphics("flag.red.png", 2*radius, 2*radius, new Vector(0.5f, 0.5f));
		flagGraphics.setParent(getEntity());
		
	}
	
	public Finish(ActorGame game, boolean fixed, Vector position) {
		this(game, fixed, position, 1.0f);
	}

	/**
	 * @return if ActorGame's payload has hit Finish
	 */
	@Override
	public boolean isHit() {
		return wasHitBy(getOwner().getPayload());
	}
	
	@Override
	public void destroy() {
		super.destroy();
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
