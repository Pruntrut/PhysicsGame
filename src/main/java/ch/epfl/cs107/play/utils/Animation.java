package ch.epfl.cs107.play.utils;

/**
 * This class was mainly created to help the animation
 * of the checkpoint, it contains utilities such as easing
 * functions, etc...
 */
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
	
	/**
	 * If value goes over/under max/min, returns max/min 
	 * Returns value otherwise
	 * @param value
	 * @param max
	 * @param min
	 * @return a float between max and min
	 */
	public static final float limit(float value, float max, float min) {
		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		} else {
			return value;
		}
	}
}
