package ch.epfl.cs107.play.game.actor.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Checkpoint;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class CheckpointLevel extends Level {

	private Finish finish;
	
	public CheckpointLevel(ActorGame owner) {
		super(owner);
	}
	
	@Override
	protected void createActors() {
		
		// Make the terrain
		Terrain terrain = new Terrain(getOwner(), 1.0f, new Polyline(-1000f, -1000f, -1000.0f, 0.0f, 1000f, 0.0f, 1000f, -1000f));
		addActor(terrain);
		
		// Make finish line
		finish = new Finish(getOwner(), true, new Vector(105f, 1.0f));
		addActor(finish);
		
	}
	
	@Override
	protected void createCheckpoints() {
		for (int i = 1; i < 50; i++) {
			Checkpoint checkpoint = new Checkpoint(getOwner(), new Vector(2f * i, 1.0f));
			addCheckpoint(checkpoint);
		}
	}
	
	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
}
