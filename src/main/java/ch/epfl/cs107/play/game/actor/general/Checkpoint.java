package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.Trigger;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.utils.Animation;
import ch.epfl.cs107.play.window.Canvas;

public class Checkpoint extends Trigger {

	private ImageGraphics roundleGraphics;
	private ImageGraphics poleGraphics;
	private ShapeGraphics signGraphics;
	
	private static final String ROUNDLE_SPRITE = "metal.hollow.11.png";
	private static final String POLE_SPRITE = "metal.7.png";
	
	private boolean animating;
	private static final float DURATION = 0.75f;
	private float timeLeft = DURATION;
	
	/**
	 * Creates a new Checkpoint, inactive after being triggered
	 * @param game
	 * @param position
	 * @param radius : radius of spherical hitbox
	 */
	public Checkpoint(ActorGame game, Vector position, float radius) {
		super(game, position, new Circle(radius), false);
				
		// Make graphics
		roundleGraphics = new ImageGraphics(ROUNDLE_SPRITE, radius*1.2f, radius*1.2f, new Vector(0.5f, 0.3f), 1.0f, -100.0f);
		roundleGraphics.setParent(getEntity());
		
		poleGraphics = new ImageGraphics(POLE_SPRITE, 0.32f * radius, radius, new Vector(0.15f, 4.0f), 1.0f, -101.0f);
		poleGraphics.setParent(getEntity());
		
		signGraphics = new ShapeGraphics(new Circle(radius/2, new Vector(0.0f, 0.25f)), Color.decode("0x2b9daf"), Color.LIGHT_GRAY, 0.1f, 1.0f, -102.0f);
		signGraphics.setParent(getEntity());
	}

	/**
	 * Creates a new Checkpoint of radius 1.0f
	 * @param game
	 * @param position
	 */
	public Checkpoint(ActorGame game, Vector position) {
		this(game, position, 1.0f);
	}

	/**
	 * If payload hits the checkpoint returns true and set checkpoint to inactive
	 * @return a boolean 
	 */
	public boolean isHit() {
		if (wasHitBy(getOwner().getPayload())) {
			setInactive();
			animating = true;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Animates checkpoint according to time elapsed
	 * @param deltaTime
	 */
	private void animate(float deltaTime) {
		timeLeft -= deltaTime;
		
		float percentComplete =  (DURATION - timeLeft) / DURATION;
		float moveUpTime = 0.2f;
		float maxHeight = 1.2f;
		
		if (percentComplete < moveUpTime) {
			// Move sign up using quadratic easing function
			float height = maxHeight * Animation.easeIn(percentComplete/moveUpTime);
			height = Animation.limit(height, maxHeight, 0.0f);
			
			signGraphics.setRelativeTransform(Transform.I.translated(0.0f, height));
		} else if (percentComplete >= 0.2f && percentComplete < 0.8f) {
			// 3D rotate sign
			float width = (float) (float)Math.cos(Math.PI * 3 * Animation.easeInOut((percentComplete - moveUpTime)/(1 - 2*moveUpTime)));
			
			if (width < 0.0f) {
				signGraphics.setFillColor(Color.decode("0x3ac808"));
			} else {
				signGraphics.setFillColor(Color.decode("0x2b9daf"));
			}
			
			signGraphics.setRelativeTransform(Transform.I.scaled(width, 1.0f).translated(0.0f, maxHeight));
		} else if (percentComplete >= 1-moveUpTime){
			// Move sign down
			float height = maxHeight - maxHeight * Animation.easeIn((percentComplete - (1-moveUpTime)) / moveUpTime);
			height = Animation.limit(height, maxHeight, 0.0f);
			signGraphics.setRelativeTransform(Transform.I.translated(0.0f, height));
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if (animating && timeLeft > 0 ) {
			animate(deltaTime);
		} else if (timeLeft <= 0) {
			animating = false;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		roundleGraphics.draw(canvas);
		poleGraphics.draw(canvas);
		signGraphics.draw(canvas);
	}

}
