package ch.epfl.cs107.play.game.actor.levels;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.Message;
import ch.epfl.cs107.play.game.actor.emitters.VictoryEmitter;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class VictoryBikeLevel extends Level {

	private Message message;
	
	public VictoryBikeLevel(ActorGame owner) {
		super(owner);
	}

	@Override
	protected void createActors() {
		
		// Add terrain
		Polyline shape = new Polyline(
				-1000.0f, -1000.0f,
				-1000.0f, -1.0f,
				-3.0f, -1.0f,
				-2.0f, 0.0f,
				2.0f, 0.0f,
				3.0f, -1.0f,
				1000.0f, -1.0f,
				1000.0f, -1000.0f
		);
		Terrain terrain = new Terrain(getOwner(), 1.0f, shape);
		addActor(terrain);
		
		// Add victory message
		message = new Message(0.2f, Color.GREEN, Color.WHITE, 0.02f, true, false, 
				new Vector(0.5f, 2.0f), 1.0f, 100.0f, getOwner().getCanvas()); 
		message.setText("VICTORY!");
		
		// Add Emitters
		VictoryEmitter leftEmitter = new VictoryEmitter(new Vector(-4.0f, -1.0f));
		addActor(leftEmitter);
		VictoryEmitter rightEmitter = new VictoryEmitter(new Vector(4.0f, -1.0f));
		addActor(rightEmitter);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		message.draw(canvas);
	}
	
	/**
	 * Since this level is the victory level, it never finishes
	 * @return false
	 */
	@Override
	public boolean isFinished() {
		return false;
	}

}
