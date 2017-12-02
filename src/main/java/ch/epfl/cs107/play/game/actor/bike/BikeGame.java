package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.general.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {
	
	private Bike bike;
	private Finish finish;
	
	private TextGraphics message;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		initialize();
		
		return true;
	}
	
	private void initialize() {
		// Add terrain to list of actors
		Terrain terrain = new Terrain(this, true, 0.85f);
		addActor(terrain);
		
		// Create bike
		bike = new Bike(this, false, new Vector(-0.5f, 0.75f));
		addActor(bike);
		
		// Add finish line
		finish = new Finish(this, true, new Vector(-10.0f, 1.0f));
		addActor(finish);
		
		// Set message graphics
		message = new TextGraphics("", 0.2f, Color.GREEN, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		
		// Make camera follow bike
		setViewCandidate(bike);
		// Set bike as main entity
		setPayload(bike);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if (bike.isHit()) {
			reset();
		}
		
		if (finish.isHit()) {
			message.setText("LEVEL COMPLETE");
			removeActor(bike);
		}
		
		message.draw(getCanvas());
	}
	
	private void reset() {
		removeActor(bike);
		removeActor(finish);
		
		initialize();
	}
	
	
}
