package ch.epfl.cs107.play.game.actor.emitters;

import ch.epfl.cs107.play.game.actor.Emitter;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;

public class CheckpointEmitter extends Emitter {

	public CheckpointEmitter(Shape shape, Vector position, int particleLimit) {
		super(shape, position, particleLimit);
	}

	@Override
	protected void createParticles(int number) {
		
	}

}
