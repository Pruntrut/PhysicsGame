package ch.epfl.cs107.play.game.tutorial;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game {

	private Window window;
	private World world;
	
	private Entity block;
	private Entity plank;
	private Entity ball;
	
	private float friction = 0.5f;
	
	// Properties of block
	private Vector blockPosition = new Vector(-5.0f, -1.0f);
	private float blockWidth = 10.0f;
	private float blockHeight = 1.0f;
	private String blockSprite = "stone.broken.4.png";
	private ImageGraphics blockGraphics;
	
	
	// Properties of plank
	private Vector plankPosition = new Vector(-2.5f, 0.8f);
	private float plankWidth = 5.0f;
	private float plankHeight = 0.2f;
	private String plankSprite = "wood.3.png";
	private ImageGraphics plankGraphics;
	
	// Properties of ball
	private Vector ballPosition = new Vector(0.5f, 4.0f);
	private float ballRadius = 0.5f;
	private String ballSprite = "explosive.11.png";
	private ImageGraphics ballGraphics;
	
	// This event is raised when game has just started
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		// Create entities
		block = buildEntity(true, blockPosition);
		plank = buildEntity(false, plankPosition);
		ball = buildEntity(false, ballPosition);
		
		// Set geometries
		buildPartRectangle(block, friction, blockHeight, blockWidth);
		buildPartRectangle(plank, friction, plankHeight, plankWidth);
		buildPartCircle(ball, friction, ballRadius);
		
		
		
		// Make graphics
		blockGraphics = new ImageGraphics(blockSprite, blockWidth, blockHeight);
		plankGraphics = new ImageGraphics(plankSprite, plankWidth, plankHeight);
		ballGraphics = new ImageGraphics(ballSprite, ballRadius*2, ballRadius*2, new Vector(0.5f, 0.5f));
		blockGraphics.setParent(block);
		plankGraphics.setParent(plank);
		ballGraphics.setParent(ball);
		
		// Add constraints
		RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
		revoluteConstraintBuilder.setFirstEntity(block);
		revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, (blockHeight*7)/4));
		revoluteConstraintBuilder.setSecondEntity(plank);
		revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2, plankHeight/2));
		revoluteConstraintBuilder.setInternalCollision(true);
		revoluteConstraintBuilder.build();
		
		return true;
	}

	@Override
	public void update(float deltaTime) {
		// Game logic
		if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			ball.applyAngularForce(10.0f);
		} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			ball.applyAngularForce(-10.0f);
		}
		
		// Physics simulation
		world.update(deltaTime);
		
		// Change camera position
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		// Render scene
		blockGraphics.draw(window);
		plankGraphics.draw(window);
		ballGraphics.draw(window);

	}

	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}
	
	/**
	 * Build entity in world according to passed parameters
	 * @param fixed : sets entity to be fixed in world or not
	 * @param position : the position vector 
	 */
	private Entity buildEntity(boolean fixed, Vector position) {
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(position);
		return entityBuilder.build();
	}
	
	
	/**
	 * Takes an entity and builds a rectangular geometric model (Part) around it according to parameters
	 * @param entity : the entity to be built
	 * @param friction: the friction coefficient
	 * @param height : a nonnegative float
	 * @param width : a nonnegative float
	 */
	private static void buildPartRectangle(Entity entity, float friction, float height, float width) {
		Polygon polygon = new Polygon(
				new Vector(0.0f, 0.0f),
				new Vector(0.0f, height),
				new Vector(width, height),
				new Vector(width, 0.0f)
			);
		
		buildPart(entity, polygon, friction);
	}
	
	/**
	 * Takes an entity and builds a circular geometric model (Part) around it according to parameters
	 * @param entity : the entity to be built
	 * @param friction: the friction coefficient
	 * @param radius : the radius of the circle
	 * @param position : the position of the model
	 */
	private static void buildPartCircle(Entity entity, float friction, float radius) {
		Circle circle = new Circle(radius);
		buildPart(entity, circle, friction);
	}
	
	/**
	 * Takes an entity and builds a geometric model (Part) around it according to parameters
	 * @param entity : the entity to be built
	 * @param shape : the entity's geometry
	 * @param friction: the friction coefficient
	 */
	private static void buildPart(Entity entity, Shape shape, float friction) {
		PartBuilder partBuilder = entity.createPartBuilder();
		partBuilder.setShape(shape);
		partBuilder.setFriction(friction);
		partBuilder.build();
	}	
	
}
