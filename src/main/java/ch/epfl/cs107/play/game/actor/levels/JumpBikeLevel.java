package ch.epfl.cs107.play.game.actor.levels;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.GravityWell;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class JumpBikeLevel extends Level {

	private static final Color TERRAIN_FILL_COLOR = new Color(0xa0a0a0);
	private static final Color TERRAIN_OUTLINE_COLOR = new Color(0x777777);
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
		Terrain ramp = new Terrain(getOwner(), 1.0f, rampShape);
		ramp.setFillColor(TERRAIN_FILL_COLOR);
		ramp.setOutlineColor(TERRAIN_OUTLINE_COLOR);
		addActor(ramp);
		
		// Add the gravity well
		GravityWell well = new GravityWell(getOwner(), new Vector(52.5f, -23.0f), 5.0f, 2f, 0.0f);
		addActor(well);
		
		// Make the landing platfom
		Polyline platformShape = new Polyline(
				75.0f, -1000.0f,
				75.0f, -24.0f,
				1000.0f, -24.0f
		);
		Terrain platform = new Terrain(getOwner(), 1.0f, platformShape);
		platform.setFillColor(TERRAIN_FILL_COLOR);
		platform.setOutlineColor(TERRAIN_OUTLINE_COLOR);
		addActor(platform);
		
		// Add crates
		for (int i = 0; i < 5; i++) {
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
