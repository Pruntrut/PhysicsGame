package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		// Add terrain to list of actors
		Terrain terrain = new Terrain(this, true, 0.85f);
		addActor(terrain);
		
		// Create bike
		Bike bike = new Bike(this, false, new Vector(-1.5f, 0.0f));
		addActor(bike);
		
		
		
		return true;
	}
	
}
