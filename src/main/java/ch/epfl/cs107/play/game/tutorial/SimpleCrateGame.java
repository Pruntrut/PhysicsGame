package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game {
	
	private Window window;
	private World world;
	
	private Entity block;
	private Entity crate;

	private ImageGraphics graphicsBlock;
	private ImageGraphics graphicsCrate;
	
	// Properties of block and crate
	private float polygonWidth = 1.0f;
	private float polygonHeight = 1.0f;
	
	private Vector blockPosition = new Vector(1.0f, 1.5f);
	private Vector cratePosition = new Vector(0.2f, 4.0f);
	
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
					new Vector(0.0f, polygonHeight),
					new Vector(polygonWidth, polygonHeight),
					new Vector(polygonWidth, 0.0f)
				);
		blockBuilder.setFriction(0.5f);
		blockBuilder.setShape(polygon);
		blockBuilder.build();
		
		// Create crate
		entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(false);
		entityBuilder.setPosition(cratePosition);
		crate = entityBuilder.build();
		
		// Set crate geometry (same as for block)
		PartBuilder crateBuilder = crate.createPartBuilder();
		crateBuilder.setShape(polygon);
		crateBuilder.build();
		
		// Graphics for block
		graphicsBlock = new ImageGraphics("stone.broken.4.png", polygonWidth, polygonHeight);
		graphicsBlock.setParent(block);
		
		// Graphics for crate
		graphicsCrate = new ImageGraphics("box.4.png", polygonWidth, polygonHeight);
		graphicsCrate.setParent(crate);
		
		return true;
	}
	
	
	// This event is called at each frame
	@Override
	public void update(float deltaTime) {
		
		// Game logic
		
		// Physics simulation
		world.update(deltaTime);
		
		// Change camera position
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		// Render scene
		graphicsBlock.draw(window);
		graphicsCrate.draw(window);
	}
	
	
	// This event is raised after game ends, to release additional resources
	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}
	
	
}
