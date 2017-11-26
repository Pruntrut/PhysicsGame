package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame {

	private Vector[] positions = {
			new Vector(0.0f, 5.0f),
			new Vector(0.2f, 7.0f),
			new Vector(2.0f, 6.0f)
		};
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		// Add crates to actors list
		for (Vector pos : positions) {
			addActor(new Crate(this, false, pos, 1.0f));
		}		
		
		return true;
	}
	
}
