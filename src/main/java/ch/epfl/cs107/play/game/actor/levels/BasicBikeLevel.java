package ch.epfl.cs107.play.game.actor.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Checkpoint;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Vector;

public class BasicBikeLevel extends Level {
	
	private Finish finish;
	
	public BasicBikeLevel(ActorGame owner) {
		super(owner);
	}
	
	@Override
	protected void createActors() {
		
		// Make the terrain
		Terrain terrain = new Terrain(getOwner(), true, 1.0f);
		addActor(terrain);
		
		// Make finish line
		finish = new Finish(getOwner(), true, new Vector(65.8f, 1f));
		addActor(finish);
		
	}
	
	@Override
	protected void createCheckpoints() {
		Checkpoint checkpoint = new Checkpoint(getOwner(), new Vector(45.0f, -4.0f));
		addCheckpoint(checkpoint);
	}
	
	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
}
