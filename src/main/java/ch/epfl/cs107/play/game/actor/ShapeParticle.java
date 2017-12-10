/**
 * 
 */
package ch.epfl.cs107.play.game.actor;

import java.awt.Color;

import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ShapeParticle extends Particle {

	private Shape shape;
	private Color fillColor;
	private Color outlineColor;
	private float thickness;
	private float alpha;
	private float depth;
	
	/**
	 * Creates a new ShapeParticle
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 * 
	 * @param shape
     * @param fillColor : fill color, may be null
     * @param outlineColor : outline color, may be null
     * @param thickness : outline thickness
     * @param alpha : transparency, between 0 (invisible) and 1 (opaque)
     * @param depth : render priority, lower-values drawn first
	 */
	public ShapeParticle(Vector position, Vector velocity, Vector acceleration, float angularPosition,
			float angularVelocity, float angularAcceleration, Shape shape, Color fillColor, Color outlineColor,
			float thickness, float alpha, float depth) {
		super(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration);
		
		if (shape == null) {
			throw new NullPointerException("Shape cannot be null");
		} 
		
		if (thickness < 0.0f) {
			throw new IllegalArgumentException("Thickness must be positive");
		}
		
		if (alpha < 0.0f || alpha > 1.0f) {
			throw new IllegalArgumentException("Alpha must be between 0 and 1");
		}
		
		this.shape = shape;
		this.fillColor = fillColor;
		this.outlineColor = outlineColor;
		this.thickness = thickness;
		this.alpha = alpha;
		this.depth = depth;
		
	}

	/**
	 * Copies another ShapeParticle
	 * @param other
	 */
	public ShapeParticle(ShapeParticle other) {
		super(other);
		
		this.shape = other.shape;
		this.fillColor = other.fillColor;
		this.outlineColor = other.outlineColor;
		this.thickness = other.thickness;
		this.alpha = other.alpha;
		this.depth = other.depth;	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawShape(shape, getTransform(), fillColor, outlineColor, thickness, alpha, depth);
	}

	@Override
	public Particle copy() {
		return new ShapeParticle(this);
	}

}
