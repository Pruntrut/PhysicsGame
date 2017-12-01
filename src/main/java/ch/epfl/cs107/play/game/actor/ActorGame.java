package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game {
	
	// List of actors in game
	private List<Actor> actors = new ArrayList<>();
	
	// Physical attributes (physics engine and its properties)
	private World world;			
	private Vector gravity = new Vector(0.0f, -9.81f);
	
	// External properties
	private Window window;
	private FileSystem fileSystem;
	
	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 10.0f;

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		
		this.window = window;
		this.fileSystem = fileSystem;
		
		// Initialize world
		world = new World();
		world.setGravity(gravity);

		// Set camera position to initial values
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		
		return true;
	}
	
	@Override
	public void end() {
		// Does nothing at the moment
	}
	
	@Override
	public void update(float deltaTime) {
		
		// 1. Physics simulation
		world.update(deltaTime);
		
		// 2. Game logic (update every actor)
		for (Actor actor : actors) {
			actor.update(deltaTime);
		}
		
		// 3. Calculate camera position
		// 		Update expected viewport center
		if (viewCandidate != null) {
			viewTarget = viewCandidate.getPosition()
					.add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		}
		
		// 		Interpolate with previous location
		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter.mixed(viewTarget, ratio);
		
		// 		Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);
		
		// 4. Draw all actors
		for (Actor actor : actors) {
			actor.draw(window);
		}
	}
	
	/**
	 * Adds given actor to list of actors in game
	 * @param actor
	 */
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	/**
	 * Removes given actor from list of actors in game
	 * @param actor
	 * @return a boolean indicating if given actor was present and removed corretly
	 */
	public boolean removeActor(Actor actor) {
		return actors.remove(actor);
	}
	
	/**
	 * Returns the keyboard object of window
	 * @return a Keyboard
	 */
	public Keyboard getKeyboard() {
		return window.getKeyboard();
	}
	
	/** 
	 * Return a canvas element of window for rendering
	 * @return a Canvas
	 */
	public Canvas getCanvas() {
		return window;
	}
	
	/**
	 * Sets the window focus to given positionable
	 * @param viewCandidate, a Position 
	 */
	public void setViewCandidate(Positionable viewCandidate) {
		this.viewCandidate = viewCandidate;
	}
	
	/**
	 * Creates an entity in world then returns it
	 * @param fixed : determines if entity is fixed or not
	 * @param position : the initial position of the entity
	 * @return the built entity
	 */
	public Entity createEntity(boolean fixed, Vector position) {
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(position);
		return entityBuilder.build();
	}
	
	public WheelConstraintBuilder getWheelContraintBuilder() {
		return world.createWheelConstraintBuilder();
	}
	
	
	

}
