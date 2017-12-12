package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameWithLevels;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.Message;
import ch.epfl.cs107.play.game.actor.levels.BasicBikeLevel;
import ch.epfl.cs107.play.game.actor.levels.ChallengeLevel;
import ch.epfl.cs107.play.game.actor.levels.CheckpointLevel;
import ch.epfl.cs107.play.game.actor.levels.JumpBikeLevel;
import ch.epfl.cs107.play.game.actor.levels.VictoryBikeLevel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGameWithLevels extends ActorGame implements GameWithLevels {

	// Main entity of game 
	private Bike bike;
	private Ragdoll ragdoll;
	
	// List of level and current level
	private List<Level> levels;
	private Level currentLevel;
	private int currentLevelIndex = -1;
	
	// Message
	private Message message;
	private static final float INITIAL_MESSAGE_DURATION = 2.0f;
	private boolean initialMessageShown = false;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		// Initialize message
		message = new Message(0.2f, Color.BLUE, Color.WHITE, 0.02f, true, false, 
				new Vector(0.5f, 0.5f), 1.0f, 100.0f, getCanvas());
		// Build levels
		levels = createLevelList();
		// Launch level
		nextLevel();
		
		return true;
	}
	
	/**
	 * Switches to next level
	 */
	@Override
	public void nextLevel() {
		// Remove previous level
		if (currentLevel != null) {
			removeActor(currentLevel);
		}
		
		// Remove bike
		if (bike != null) {
			removeActor(bike);
		}
				
		// Set new level
		if (currentLevelIndex < levels.size() - 1) {
			currentLevelIndex++;
		}
		currentLevel = levels.get(currentLevelIndex);
		
		buildBike(currentLevel.getBikeInitialPosition());
		
		// Initialize level
		currentLevel.setParent(getCanvas());
		currentLevel.createAllActors();
		addActor(currentLevel);
		
		// Check if final level
		if (currentLevelIndex == levels.size() - 1) {
			// Freeze bike
			bike.setFreeze(true);
		} else {
			// Draw next level message as normal
			message.setFillColor(Color.BLUE);
			message.prepareDrawFade("Level " + (currentLevelIndex + 1), INITIAL_MESSAGE_DURATION);
			initialMessageShown = false;
		}
		
		
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		// If the message was not shown and is being shown
		if (!initialMessageShown && !message.wasShown()) {
			message.drawFade(deltaTime);
			setFreeze(true);
		// If message has finished displaying but game still frozen, unfreeze game
		// This makes sure game that was frozen manually in nextLevel() is not unfrozen by the message
		} else if (!initialMessageShown && message.wasShown()) {
			initialMessageShown = true;
			setFreeze(false);
		}
		
		if (currentLevel.isFinished()) {
			nextLevel();
		} else if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			resetLevel();
		} else if (bike.isHit() && ragdoll == null) {
			ragdoll = bike.createRagdoll();
			addActor(ragdoll);
			setViewCandidate(null);
			
			message.prepareDrawFade("Press R to restart", Float.MAX_VALUE);
			message.setFillColor(Color.RED);
			//message.setAnchor(new Vector(0.505f, -2.1f));
		}
		
		message.drawFade(deltaTime);
		
	}

	

	/**
	 * Resets the level to its original state when nextLevel was called
	 */
	@Override
	public void resetLevel() {
		message.clear();
		
		removeActor(bike);
		
		if (ragdoll != null) {
			removeActor(ragdoll);
		}
		
		ragdoll = null;
		
		buildBike(currentLevel.getBikeInitialPosition());
		currentLevel.reset();
	}
	
	/**
	 * Builds the bike entity, sets it as payload, actor, etc...
	 * @param position : initial position of bike
	 */
	private void buildBike(Vector position) {
		bike = new Bike(this, false, position.add(0.0f, 0.5f));
		addActor(bike);
		setViewCandidate(bike);
		setPayload(bike);
	}
	
	/**
	 * @return the list of levels for this bike game
	 */
	protected List<Level> createLevelList() {
		return Arrays.asList(
				new JumpBikeLevel(this),
				new BasicBikeLevel(this),
				new CheckpointLevel(this),
				new ChallengeLevel(this),
				new VictoryBikeLevel(this)
		);
	}

}
