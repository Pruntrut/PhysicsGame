package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Level extends Node implements Actor {

	private ActorGame owner;
	
	private List<Actor> actors;
		
	public Level(ActorGame owner) {
		
		if (owner == null) {
			throw new NullPointerException("Owner cannot be null");
		}
		
		this.owner = owner;
		actors = new ArrayList<>();
	}
	
	/**
	 * Initializes all actors from specific level (e.g. crates, terrain, ...)=
	 */
	public abstract void createAllActors();
	
	/**
	 * Updates every actor in level
	 * @param deltaTime : the time difference in milliseconds
	 */
	@Override
	public void update(float deltaTime) {
		for (Actor actor : actors) {
			actor.update(deltaTime);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		for (Actor actor : actors) {
			actor.draw(canvas);
		}
	}

	/** 
	 * Destroys every actor in level
	 */
	@Override
	public void destroy() {
		// Destroy every actor
		for (Actor actor : actors) {
			actor.destroy();
		}
		
		// Reset list
		actors = new ArrayList<>();
	}
	
	/**
	 * Return true if level has finished
	 * @return a boolean
	 */
	public abstract boolean isFinished();
	
	/**
	 * Return this level's owner (the ActorGame in which it takes place)
	 * @return an ActorGame
	 */
	protected ActorGame getOwner() {
		return owner;
	}
	
	/**
	 * Adds an actor to list of actors in level
	 * @param actor
	 */
	protected void addActor(Actor actor) {
		actors.add(actor);
	}
}
