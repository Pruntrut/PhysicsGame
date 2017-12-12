package ch.epfl.cs107.play.game.actor.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.general.Checkpoint;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.GravityWell;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.Seesaw;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class ChallengeLevel extends Level {

	private Finish finish;
	
	public ChallengeLevel(ActorGame owner) {
		super(owner);
	}

	@Override
	protected void createActors() {
		
		// Create terrain
		Polyline terrainShape = new Polyline(
				-1000f, -1000f,
				-1000f, 0.0f,
				10.0f, 0.0f,
				10.0f, 5.0f,
				25.0f, 5.0f,
				25.0f, 0.0f,
				
				27.5f, 0.0f,
				27.5f, 4.0f,
				30.0f, 4.0f,
				30.0f, 0.0f,
				32.5f, 0.0f,
				32.5f, 4.0f,
				35.0f, 4.0f,
				35.0f, 0.0f,
				37.7f, 0.0f,
				37.7f, 4.0f,
				40.5f, 4.0f,

				40.5f, 5.0f,
				62.0f, 5.0f,
				66.0f, 7.0f,
				66.0f, 5.0f,
				
				1000f, 5.0f,
				1000f, -1000f
		);
		Terrain terrain = new Terrain(getOwner(), 1.0f, terrainShape);
		addActor(terrain);
		// Create gravity well
		GravityWell gravityWell = new GravityWell(getOwner(), new Vector(8.0f, 2.5f), 5.0f, 4.0f, (float)Math.PI/2);
		gravityWell.setForce(150f);
		addActor(gravityWell);
		// Add seesaws
		addActor(new Seesaw(getOwner(), new Vector(27.5f, 4.0f), true));
		addActor(new Seesaw(getOwner(), new Vector(32.5f, 4.0f), true));
		addActor(new Seesaw(getOwner(), new Vector(37.7f, 4.0f), true));
		// Add pendulum
		Pendulum pendulum = new Pendulum(getOwner(), new Vector(70.0f, 12.5f), 1.25f, 5.0f);
		pendulum.setVelocity(new Vector(6.0f, 0.0f));
		addActor(pendulum);
		// Add finish line
		finish = new Finish(getOwner(), true, new Vector(85.0f, 6.0f));
		addActor(finish);
	}
	
	

	@Override
	protected void createCheckpoints() {
		addCheckpoint(new Checkpoint(getOwner(), new Vector(45.0f, 6.0f)));
	}

	@Override
	public boolean isFinished() {
		return finish.isHit();
	}

}
