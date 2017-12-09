package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Seesaw extends GameEntity implements Actor {
	
	private static final float PLANK_HEIGHT = 0.3f;
	private static final float PLANK_WIDTH = 5.0f;
	private static final float FRICTION = 0.85f;
	
	private static final float BASE_WIDTH = 1.0f;
	private static final float BASE_HEIGHT = 1.0f;
	
	private static final String PLANK_SPRITE = "wood.3.png";
	private static final String BASE_SPRITE = "wood.10.png";
	
	private Entity plank;

	private ImageGraphics baseGraphics;
	private ImageGraphics plankGraphics;
	
	/**
	 * Creates a new Seesaw
	 * @param game
	 * @param position
	 * @param leanLeft : if true, seesaw will start with its plank leaning to the left
	 */
	public Seesaw(ActorGame game, Vector position, boolean leanLeft) {
		super(game, true, position);
		
		// Build base
		PartBuilder baseBuilder = getEntity().createPartBuilder();
		Polygon baseShape = new Polygon(0.0f, 0.0f, BASE_WIDTH/2, BASE_HEIGHT/2, BASE_WIDTH, 0.0f);
		baseBuilder.setShape(baseShape);
		baseBuilder.build();
		
		// Build plank entity
		float angle = (float)Math.asin(2*BASE_HEIGHT/PLANK_WIDTH);
		float plankOriginX = - (BASE_HEIGHT*(float)Math.tan(Math.PI/2 - angle)) + BASE_WIDTH/2;
		float plankOriginY = 0.0f;
		
		if (!leanLeft) {
			angle = -angle;
			plankOriginY = 2*BASE_HEIGHT;
		}
		
		plank = getOwner().createEntity(false, getEntity().getPosition().add(new Vector(plankOriginX, plankOriginY)));
		plank.setAngularPosition(angle);
		
		// Build plank part
		PartBuilder plankBuilder = plank.createPartBuilder();
		Polygon plankShape = new Polygon(0.0f, 0.0f, 0.0f, PLANK_HEIGHT, PLANK_WIDTH, PLANK_HEIGHT, PLANK_WIDTH, 0.0f);
		plankBuilder.setShape(plankShape);
		plankBuilder.setFriction(FRICTION);
		plankBuilder.build();
		
		// Build graphics
		plankGraphics = new ImageGraphics(PLANK_SPRITE, PLANK_WIDTH, PLANK_HEIGHT);
		baseGraphics = new ImageGraphics(BASE_SPRITE, BASE_WIDTH, BASE_HEIGHT);
		plankGraphics.setParent(plank);
		baseGraphics.setParent(getEntity());
		
		// Revolute constraint
		RevoluteConstraintBuilder revoluteConstraintBuilder = getOwner().getRevoluteContraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(getEntity());
		revoluteConstraintBuilder.setFirstAnchor(new Vector(BASE_WIDTH/2, BASE_HEIGHT));
		revoluteConstraintBuilder.setSecondEntity(plank);
		revoluteConstraintBuilder.setSecondAnchor(new Vector(PLANK_WIDTH/2, 0.0f));
		revoluteConstraintBuilder.setInternalCollision(false);
		revoluteConstraintBuilder.build();
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
		plankGraphics.draw(canvas);
		baseGraphics.draw(canvas);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		plank.destroy();
	}

}
