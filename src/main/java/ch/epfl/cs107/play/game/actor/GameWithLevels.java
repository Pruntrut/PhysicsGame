package ch.epfl.cs107.play.game.actor;

public interface GameWithLevels {
	
	/**
	 * Handles transition from one game level to the next
	 */
	void nextLevel();
	
	/**
	 * Resets current level
	 */
	void resetLevel();
	
	// TODO : use this
	/*
	void goToLevel(int level) throws IllegalArgumentException;
	*/
}
