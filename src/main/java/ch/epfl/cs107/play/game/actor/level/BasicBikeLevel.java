package ch.epfl.cs107.play.game.actor.level;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

public class BasicBikeLevel extends Level {
	
	private Finish finish;
	
	private TextGraphics message;
	private long messageCreationTime;
	private static final long INITIAL_MESSAGE_DURATION = 2000;
	private boolean initialMessageShown = false;
	
	public BasicBikeLevel(ActorGame owner) {
		super(owner);
	}
	
	@Override
	public void createAllActors() {
		
		// Make the terrain
		Terrain terrain = new Terrain(getOwner(), true, 1.0f);
		addActor(terrain);
		
		// Make finish line
		finish = new Finish(getOwner(), true, new Vector(65.0f, 0.5f));
		addActor(finish);
		
		// Make message
		message = new TextGraphics("", 0.2f, Color.BLUE, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(getOwner().getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		setMessageText("Next Level");
	}
	
	/**
	 * Sets the message text and sets creation time to current time
	 * @param text
	 */
	private void setMessageText(String text) {
		message.setText(text);
		messageCreationTime = System.currentTimeMillis();
	}
	
	/**
	 * If elapsed time since message creation is less than half of duration, shows message at full alpha,
	 * Fades out from half duration to full duration
	 * @param duration : total duration (half at 100%, half fading out)
	 */
	private void drawMessage(long duration) {
		long elapsed = System.currentTimeMillis() - messageCreationTime;
		
		if (elapsed < duration / 2) {
			message.draw(getOwner().getCanvas());
		} else if (elapsed < duration) {
			float alpha = 1.0f - (float)(elapsed - duration/2) / (float)(duration/2);
			message.setAlpha(alpha);
			message.draw(getOwner().getCanvas());
		}
	}
	
	private void drawInitialMessage() {
		long elapsed = System.currentTimeMillis() - messageCreationTime;
		if (elapsed < INITIAL_MESSAGE_DURATION) {
			drawMessage(INITIAL_MESSAGE_DURATION);
		} else if (elapsed > INITIAL_MESSAGE_DURATION){
			initialMessageShown = true;
		}
	}
	
	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
	
	@Override
	public void update(float deltaTime) {
		if (!initialMessageShown) {
			drawInitialMessage();
		}
		
		super.update(deltaTime);
	}
	
	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return Vector.ZERO;
	}
}
