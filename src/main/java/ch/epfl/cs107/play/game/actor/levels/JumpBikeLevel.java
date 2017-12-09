package ch.epfl.cs107.play.game.actor.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.GravityWell;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class JumpBikeLevel extends Level {

	private Finish finish;
	
	public JumpBikeLevel(ActorGame owner) {
		super(owner);
	}

	@Override
	protected void createActors() {
		
		// Make the launch ramp
		Polyline rampShape = new Polyline(
				-100.0f, 0.0f,
				-100.0f, 100.0f,
				-1.5f, 100.0f,
				-1.5f, 0.0f,
				3.0f, 0.0f,
				5.0f, -1.0f,
				30.0f, -20.0f,
				35.0f, -22.0f,
				40.0f, -23.0f,
				45.0f, -23.5f,
				50.0f, -24.0f,
				55.0f, -24.0f,
				60.0f, -22.0f,
				60.0f, -1000.0f
		);
		Terrain ramp = new Terrain(getOwner(), true, 1.0f, rampShape);
		addActor(ramp);
		
		// Add the gravity well
		float angle = (float)Math.atan(2.0f/5.0f);
		float x = 55f + 2.0f / (float)Math.cos(angle);
		GravityWell well = new GravityWell(getOwner(), new Vector(x, -22f), 5.0f, 2f, angle);
		addActor(well);
		
		// Make the landing platfom
		Polyline platformShape = new Polyline(
				75.0f, -1000.0f,
				75.0f, -24.0f,
				1000.0f, -24.0f
		);
		Terrain platform = new Terrain(getOwner(), true, 1.0f, platformShape);
		addActor(platform);
		
		// Add crates
		for (int i = 0; i < 6; i++) {
			Crate crate = new Crate(getOwner(), false, new Vector(78.0f, -24.0f + i * 1.0f), 1.0f);
			addActor(crate);
		}
		
		// Add finish
		finish = new Finish(getOwner(), true, new Vector(90.0f, -23.0f));
		addActor(finish);
	}

	@Override
	public boolean isFinished() {
		return finish.isHit();
	}
	
	

}
