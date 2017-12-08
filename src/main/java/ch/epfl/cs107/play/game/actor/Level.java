package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.actor.general.Checkpoint;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Level extends Node implements Actor {

	private ActorGame owner;
	
	private List<Actor> actors;
	private List<Checkpoint> checkpoints;		// List of all checkpoints
	private List<Checkpoint> usedCheckpoints;	// List of already activated checkpoints
	private Checkpoint currentCheckpoint;		// Most recent checkpoint (one being used)
	
	public Level(ActorGame owner) {
		
		if (owner == null) {
			throw new NullPointerException("Owner cannot be null");
		}
		
		this.owner = owner;
		actors = new ArrayList<>();
		checkpoints = new ArrayList<>();
		usedCheckpoints = new ArrayList<>();
	}
	
	/**
	 * Initializes all actors from specific level (e.g. crates, terrain, ...)
	 * including checkpoints
	 */
	public final void createAllActors() {
		// Method is final to avoid child class re-implementing, 
		// circumventing the createCheckpoints and createActors methods
		
		createActors();
		createCheckpoints();
	}
	
	/**
	 * Creates all the actors in level EXCEPT Checkpoints
	 * @see createCheckpoints()
	 */
	protected abstract void createActors();
	
	/**
	 * Creates all the checkpoints in level 
	 * Does nothing by default, to be implemented by child class if it wants to add
	 * checkpoint functionality
	 * @see createCheckpoints();
	 */
	protected void createCheckpoints() {
		// Does nothing by default, if want to add 
	};
	
	/**
	 * Updates every actor in level
	 * @param deltaTime : the time difference in milliseconds
	 */
	@Override
	public void update(float deltaTime) {
		for (Actor actor : actors) {
			actor.update(deltaTime);
		}

		for (Checkpoint checkpoint : checkpoints) {
			// If new checkpoint is hit by bike, set it to current checkpoint
			if (checkpoint.isHit() && !usedCheckpoints.contains(checkpoint)) {
				currentCheckpoint = checkpoint;
				usedCheckpoints.add(checkpoint);
			}
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		for (Actor actor : actors) {
			actor.draw(canvas);
		}
	}

	/** 
	 * Destroys every actor in level (including checkpoints)
	 */
	@Override
	public void destroy() {
		// Destroy every actor
		for (Actor actor : actors) {
			actor.destroy();
		}
		
		// Reset list
		actors = new ArrayList<>();
		checkpoints = new ArrayList<>();
		usedCheckpoints = new ArrayList<>();
		currentCheckpoint = null;
	}
	
	/**
	 * Resets the level to its initial state, but keeps the checkpoints and their state intact
	 */
	public void reset() {
		// Reset actors and keep checkpoints
		deleteActorsExceptCheckpoints();
		// Create actors
		createActors();
	}
	
	/**
	 * Deletes all actors in level except for checkpoints
	 * Checkpoint lists and current checkpoint are kept
	 */
	private void deleteActorsExceptCheckpoints() {
		List<Checkpoint> checkpointTempList = new ArrayList<>();
		List<Checkpoint> usedCheckpointTempList = new ArrayList<>();
		Checkpoint currentCheckpointTemp = currentCheckpoint;
		
		// Remove checkpoints from actor list (avoid destroying them)
		for (Checkpoint checkpoint : checkpoints) {
			actors.remove(checkpoint);				// remove from actors (so that their entity is not destroyed)
			checkpointTempList.add(checkpoint);		// add them to the temporary list
		}
		
		for (Checkpoint usedCheckpoint : usedCheckpoints) {
			usedCheckpointTempList.add(usedCheckpoint);
		}
		
		destroy();									// Destroys all other entities and empties checkpoint lists
		
		// Add checkpoints back in
		for (Checkpoint checkpoint : checkpointTempList) {
			addCheckpoint(checkpoint);				// Add checkpoints back in
		}
		
		for (Checkpoint usedCheckpoint : usedCheckpointTempList) {
			usedCheckpoints.add(usedCheckpoint);
		}
		
		currentCheckpoint = currentCheckpointTemp;
	}
	
	/**
	 * Adds a checkpoint to level's list of checkpoints
	 */
	protected void addCheckpoint(Checkpoint checkpoint) {
		actors.add(checkpoint);
		checkpoints.add(checkpoint);
	}
	
	/**
	 * Returns the position at which the bike should be placed
	 * @return the bike position, Vector.ZERO if no checkpoint
	 */
	public Vector getBikeInitialPosition() {
		if (currentCheckpoint != null) {
			return currentCheckpoint.getPosition();
		}
		
		return Vector.ZERO;
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
