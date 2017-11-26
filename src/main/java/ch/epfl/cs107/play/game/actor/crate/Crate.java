package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {
	
	private float length;
	private static final String CRATE_SPRITE = "box.4.png";
	private ImageGraphics crateGraphics;

	public Crate(ActorGame game, boolean fixed, Vector position, float length) {
		super(game, fixed, position);
		this.length = length;
		
		buildPart();
		makeGraphics();
	}

	public Crate(ActorGame game, boolean fixed) {
		super(game, fixed);

		this.length = 1.0f;		
		buildPart();
		makeGraphics();
	}
	
	/**
	 * Builds the crate as a square physical Part of length "length"
	 */
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(
				new Vector(0.0f, 0.0f),
				new Vector(0.0f, length),
				new Vector(length, length),
				new Vector(length, 0.0f)
		);
		partBuilder.setShape(polygon);
		partBuilder.build();
	}
	
	/**
	 * Sets the graphical representation of the crate to CRATE_SPRITE
	 */
	private void makeGraphics() {
		crateGraphics = new ImageGraphics(CRATE_SPRITE, length, length);
		crateGraphics.setParent(getEntity());
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
		crateGraphics.draw(canvas);
	}
}
