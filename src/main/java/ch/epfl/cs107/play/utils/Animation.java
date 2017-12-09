package ch.epfl.cs107.play.utils;

public final class Animation {
	/**
	 * Goes from 0 to 0.5 at midpoint then to 1
	 * Accelerates until halfway then decelerates 
	 * (using two quadratic functions)
	 * @param t : a float between 0 and 1
	 * @return a float between 0 and 1
	 */
	public static final float easeInOut(float t) {
		if (t < 0.5f) {
			// Ease in
			return 2*t*t;
		} else {
			// Ease out
			return -1 + (4-2*t)*t;
		}
	}
	
	/**
	 * Goes from 0 to 1 with quadratic function
	 * Accelerates from 0 velocity
	 * @param t : a float between 0 and 1
	 * @return a float between 0 and 1
	 */
	public static final float easeIn(float t) {
		return t*t;
	}
}
