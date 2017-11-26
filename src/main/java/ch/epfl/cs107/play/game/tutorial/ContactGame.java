package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game {

	private Window window;
	private World world;
	
	private BasicContactListener contactListener;
	
	private Entity block;
	private Entity ball;
	
	private float friction = 0.5f;

	// Properties of block
	private Vector blockPosition = new Vector(-5.0f, -1.0f);
	private float blockWidth = 10.0f;
	private float blockHeight = 1.0f;
	private String blockSprite = "stone.broken.4.png";
	private ImageGraphics blockGraphics;
	
	// Properties of ball
	private Vector ballPosition = new Vector(0.0f, 2.0f);
	private float ballRadius = 0.5f;
	private ShapeGraphics ballGraphics;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		// Create entities
		block = buildEntity(true, blockPosition);
		ball = buildEntity(false, ballPosition);
		
		// Set geometries
		buildPartRectangle(block, friction, blockHeight, blockWidth);
		buildPartCircle(ball, friction, ballRadius);
		
		// Make graphics
		blockGraphics = new ImageGraphics(blockSprite, blockWidth, blockHeight);
		Circle circle = new Circle(ballRadius);
		ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.BLUE, 0.1f, 1, 0);
		blockGraphics.setParent(block);
		ballGraphics.setParent(ball);
		
		// Setup contact listener
		contactListener = new BasicContactListener();
		ball.addContactListener(contactListener);
		
		return true;
	}

	@Override
	public void update(float deltaTime) {
		// Game logic
		
		// Physics simulation
		world.update(deltaTime);
		
		// Change camera position
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		// Render scene
		
		// contactListener is associated to ball
		// contactListener.getEntities() returns a list of entites in collision with ball
		int numberOfCollisions = contactListener.getEntities().size();
		
		if (numberOfCollisions > 0) {
			ballGraphics.setFillColor(Color.RED);
		}
		
		blockGraphics.draw(window);
		ballGraphics.draw(window);

	}

	@Override
	public void end() {
		// Does nothing at the moment

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
