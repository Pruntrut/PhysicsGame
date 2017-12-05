package ch.epfl.cs107.play.game.actor.levels;

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
		
		// TODO : Use message
		
		// Make message
		message = new TextGraphics("", 0.2f, Color.BLUE, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(getOwner().getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
	
	@Override
	public void update(float deltaTime) {
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
