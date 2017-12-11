package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Image;

public class ImageParticle extends Particle {
	
	private String name;
	private float width;
	private float height;
	private float alpha;
	private float depth;

	
	/**
	 * Creates a new ImageParticle
	 * @param position : initial position
	 * @param velocity : initial velocity
	 * @param acceleration : initial acceleration
	 * @param angularPosition : initial angular position
	 * @param angularVelocity : initial angular velocity
	 * @param angularAcceleration : initial angular acceleration
	 * @param permanent
	 * @param name : image name, non null
	 * @param width : actual image width, before transformation
     * @param height : actual image height, before transformation
	 * @param alpha : transparency between 0 (invisible) and 1 (opaque)
	 * @param depth : render priority, lower-values drawn first
	 */
	public ImageParticle(Vector position, Vector velocity, Vector acceleration, float angularPosition, float angularVelocity,
			float angularAcceleration, boolean permanent, String name, float width, float height, float alpha, float depth) {
		super(position, velocity, acceleration, angularPosition, angularVelocity, angularAcceleration, permanent);
		
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		} 
		
		if (width <= 0.0f || height <= 0.0f) {
			throw new IllegalArgumentException("Width and Height must be positive");
		}
		
		if (alpha < 0.0f || alpha > 1.0f) {
			throw new IllegalArgumentException("Alpha must be between 0 and 1");
		}
		
		this.name = name;
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		this.depth = depth;
	}
	
	/**
	 * Copies an ImageParticle
	 * @param other
	 */
	public ImageParticle(ImageParticle other) {
		super(other);
		
		this.name = other.name;
		this.width = other.width;
		this.height = other.height;
		this.alpha = other.alpha;
		this.depth = other.depth;
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (!isExpired()) {
			float alpha = this.alpha;
			
			// If time left is last 25%, start to fade out
			if (getTimeLeft() < 0.25f * getDuration()) {
				alpha *= getTimeLeft() / (0.25f * getDuration()); 
			} else {
				alpha *= 1.0f;
			}
			
			Image image = canvas.getImage(name);
			Transform transform = Transform.I.scaled(width, height).transformed(getTransform());
			canvas.drawImage(image, transform, alpha, depth);
		}
	}

	@Override
	public ImageParticle copy() {
		return new ImageParticle(this);
	}

}
