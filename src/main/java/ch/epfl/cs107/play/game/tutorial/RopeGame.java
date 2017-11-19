package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;
import sun.awt.X11.InfoWindow.Balloon;

public class RopeGame implements Game {
	
	private Window window;
	private World world;
	
	private Entity block;
	private Entity ball;

	private ImageGraphics blockGraphics;
	private ShapeGraphics ballGraphics;
	
	// Properties of block
	private Vector blockPosition = new Vector(1.0f, 1.5f);
	private float blockWidth = 1.0f;
	private float blockHeight = 1.0f;
	
	// Properties of ball
	private Vector ballPosition = new Vector(0.2f, 4.0f);
	private Vector ballVelocity = new Vector(-5.0f, 0.0f);
	private float ballRadius = 0.6f;
	
	// This event is raised when game has just started
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		
		world = new World();
		world.setGravity(new Vector(0f, -9.81f));
		
		// Create fixed block
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(true);
		entityBuilder.setPosition(blockPosition);
		block = entityBuilder.build();
		
		// Set block geometry
		PartBuilder blockBuilder = block.createPartBuilder();
		Polygon polygon = new Polygon(
					new Vector(0.0f, 0.0f),
					new Vector(0.0f, blockHeight),
					new Vector(blockWidth, blockHeight),
					new Vector(blockWidth, 0.0f)
				);
		blockBuilder.setShape(polygon);
		blockBuilder.build();
		
		
		// Create ball
		entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(ballPosition);
		entityBuilder.setVelocity(ballVelocity);
		ball = entityBuilder.build();
		
		// Set ball geometry
		PartBuilder ballBuilder = ball.createPartBuilder();
		Circle circle = new Circle(ballRadius, ballPosition);
		ballBuilder.setShape(circle);
		ballBuilder.build();
		
		
		// Graphics for block
		blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockWidth);
		blockGraphics.setParent(block);
		
		// Graphics for crate
		ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.RED, 0, ballRadius, 0);
		ballGraphics.setParent(ball);
		
		
		// Add constraints
		RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
		ropeConstraintBuilder.setFirstEntity(block);
		ropeConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, blockHeight/2));
		ropeConstraintBuilder.setSecondEntity(ball);
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
		ropeConstraintBuilder.setMaxLength(3.0f);
		ropeConstraintBuilder.setInternalCollision(true);
		ropeConstraintBuilder.build();
		
		return true;
	}

	// This event is called at each frame
	@Override
	public void update(float deltaTime) {
		// Game logic
		
		// Physics simulation
		world.update(deltaTime);;
		
		// Change camera position
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		// Render scene
		blockGraphics.draw(window);
		ballGraphics.draw(window);
	}

	// This event is raised after game ends, to release additional resources
	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}
	
}
