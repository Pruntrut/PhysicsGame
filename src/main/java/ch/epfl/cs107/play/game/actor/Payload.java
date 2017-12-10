package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Vector;

/**
 *	Wrapper class for GameEntity which forces the payload returned by the 
 *	ActorGame to have a isAlive method (used for checkpoint,...)
 */
public abstract class Payload extends GameEntity {

	/**
	 * Creates a new payload
	 * @param game
	 * @param fixed
	 * @param position
	 */
	public Payload(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
	}

	/**
	 * Creates a new payload at origin
	 * @param game
	 * @param fixed
	 */
	public Payload(ActorGame game, boolean fixed) {
		super(game, fixed);
	}
	
	public abstract boolean isAlive();

}
