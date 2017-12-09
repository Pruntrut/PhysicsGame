package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {
	private Entity entity;
	private ActorGame game;
	
	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (game == null) {
			throw new NullPointerException("ActorGame cannot be null");
		}
		
		this.game = game;
		entity = game.createEntity(fixed, position);
		
	}
	
	public GameEntity(ActorGame game, boolean fixed) {
		this(game, fixed, Vector.ZERO);
	}
	
	public void destroy() {
		entity.destroy();
	}
	
	protected Entity getEntity() {
		return entity;
	}
	
	protected ActorGame getOwner() {
		return game;
	}
	
	public boolean isSameEntity(Entity other) {
		return entity.equals(other);
	}
	
	
}
