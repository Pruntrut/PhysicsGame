package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {
	private Entity entity;
	private ActorGame game;
	
	/**
	 * Creates a new game entity
	 * @param game
	 * @param fixed
	 * @param position
	 */
	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (game == null) {
			throw new NullPointerException("ActorGame cannot be null");
		}
		
		this.game = game;
		entity = game.createEntity(fixed, position);
		
	}
	
	/**
	 * Creates a new game entity at origin
	 * @param game
	 * @param fixed
	 */
	public GameEntity(ActorGame game, boolean fixed) {
		this(game, fixed, Vector.ZERO);
	}
	
	/**
	 * Destroys the entity
	 */
	public void destroy() {
		entity.destroy();
	}
	
	/**
	 * @return this game entity's physical entity
	 */
	protected Entity getEntity() {
		return entity;
	}
	
	/**
	 * @return the actor game this entity is in
	 */
	protected ActorGame getOwner() {
		return game;
	}
	
	/**
	 * Compares this GameEntity's physical Entity with another
	 * @param other
	 * @return true if other is the same entity
	 */
	public boolean isSameEntity(Entity other) {
		return entity.equals(other);
	}
	
}
