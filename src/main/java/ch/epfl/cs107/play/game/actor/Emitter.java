package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Emitter implements Actor {

	private List<Particle> particles;
	private int particleLimit;
	
	private Vector position;
	private Shape shape;
	private Graphics graphics;
	
	/**
	 * Creates a new Emitter without a graphical representation
	 * @param shape : shape of emitter
	 * @param position : absolute position of emitter
	 * @param particleLimit : max number of particles
	 */
	public Emitter(Shape shape, Vector position, int particleLimit) {
		particles = new ArrayList<>();
		this.shape = shape;
		this.particleLimit = particleLimit;
	}
	
	protected void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void draw(Canvas canvas) {
		// Draw particles
		for (Particle particle : particles) {
			particle.draw(canvas);
		}
		
		// If emitter has a graphical representation, draw it
		if (graphics != null) {
			graphics.draw(canvas);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		// Make new particles
		//createParticles(particleLimit - particles.size());
		if (particles.size() < particleLimit) {
			createParticles(1);
		}
		
		// Update particles
		for (Particle particle : particles) {
			particle.update(deltaTime);
		}
	}
	
	/**
	 * Creates n particles and adds them to the list
	 * @param number
	 */
	protected abstract void createParticles(int number);
	
	/**
	 * Adds particle to list of particles
	 * @param particle
	 */
	protected void addParticle(Particle particle) {
		if (particles.size() < particleLimit) {
			particles.add(particle);
		}
	}
	
	@Override
	public Transform getTransform() {
		return Transform.I.translated(position);
	}

	@Override
	public Vector getVelocity() {
		return Vector.ZERO;
	}
}
