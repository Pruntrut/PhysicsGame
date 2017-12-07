package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameWithLevels;
import ch.epfl.cs107.play.game.actor.Level;
import ch.epfl.cs107.play.game.actor.Message;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.levels.BasicBikeLevel;
import ch.epfl.cs107.play.game.actor.levels.CrateBikeLevel;
import ch.epfl.cs107.play.game.actor.levels.VictoryBikeLevel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGameWithLevels extends ActorGame implements GameWithLevels {

	// Main entity of game 
	private Bike bike;
	
	// List of level and current level
	private List<Level> levels;
	private Level currentLevel;
	private int currentLevelIndex = -1;
	
	// Message
	private Message message;
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
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		// If the message was not shown and is being shown
		if (!initialMessageShown && !message.wasShown()) {
			message.drawFade();
			setFreeze(true);
		// If message has finished displaying but game still frozen, unfreeze game
		// This makes sure game that was frozen in nextLevel() is not unfrozen
		} else if (!initialMessageShown && message.wasShown()) {
			initialMessageShown = true;
			setFreeze(false);
		}
		
		if (currentLevel.isFinished()) {
			nextLevel();
		} else if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			resetLevel();
		} else if (bike.isHit()) {
			resetLevel();
		}
		
	}

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
		
		buildBike();
		
		// Initialize level
		currentLevel.setParent(getCanvas());
		currentLevel.createAllActors();
		addActor(currentLevel);
		
		// Check if final level
		if (currentLevelIndex == levels.size() - 1) {
			// Freeze game
			setFreeze(true);
		} else {
			// Draw next level message as normal
			message.prepareDrawFade("Level " + (currentLevelIndex + 1), 2000);
			initialMessageShown = false;
		}
		
		
	}

	/**
	 * Resets the level to its original state when nextLevel was called
	 */
	@Override
	public void resetLevel() {
		removeActor(bike);
		buildBike();
		
		currentLevel.destroy();
		currentLevel.createAllActors();
	}
	
	/**
	 * Builds the bike entity, sets it as payload, actor, etc...
	 */
	private void buildBike() {
		// Build bike
		bike = new Bike(this, false, new Vector(0.0f, 0.5f));
		addActor(bike);
		setViewCandidate(bike);
		setPayload(bike);
	}
	
	protected List<Level> createLevelList() {
		return Arrays.asList(
				new VictoryBikeLevel(this),
				new BasicBikeLevel(this),
				new CrateBikeLevel(this)
		);
	}

}
