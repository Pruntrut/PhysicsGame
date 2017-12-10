package ch.epfl.cs107.play.game.actor.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Checkpoint;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.Seesaw;
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
		Terrain terrain = new Terrain(getOwner(), 1.0f);
		addActor(terrain);
		
		// Make finish line
		finish = new Finish(getOwner(), true, new Vector(85f, 1f));
		addActor(finish);
		
		// Make pendulum
		Pendulum pendulum = new Pendulum(getOwner(), new Vector(43.0f, 2.0f), 1.0f, 4.25f);
		pendulum.setVelocity(new Vector(5.0f, 0.0f));
		addActor(pendulum);
		
		// Add seesaw
		Seesaw seesaw = new Seesaw(getOwner(),new Vector(70.0f, 0.0f), true);
		addActor(seesaw);
	}
	
	@Override
	protected void createCheckpoints() {
		Checkpoint checkpoint = new Checkpoint(getOwner(), new Vector(50.0f, -4.0f));
		addCheckpoint(checkpoint);
	}
	
	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
}
