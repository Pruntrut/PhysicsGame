package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    // Graphical representation of the body
    private ImageGraphics graphicsCrate;
    private ImageGraphics graphicsBow;
    

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // Create new entity builder
        EntityBuilder entityBuilder = world.createEntityBuilder();
        // Ensure it remains fixed
        entityBuilder.setFixed(true);
        // Set initial position
        entityBuilder.setPosition(new Vector(1.0f, 1.5f));
        // Build body
        body = entityBuilder.build();
        
        // Set graphical representation of body 
        graphicsCrate = new ImageGraphics("stone.broken.4.png", 1, 1);
        graphicsCrate.setAlpha(0.5f);
        graphicsCrate.setDepth(0.0f);
        graphicsCrate.setParent(body);
        
        // Set second graphical representation
        graphicsBow = new ImageGraphics("bow.png", 1, 1);
        graphicsBow.setAlpha(1.0f);
        graphicsBow.setDepth(1.0f);
        graphicsBow.setRelativeTransform(Transform.I.rotated((float)Math.PI, new Vector(0.5f, 0.5f)));
        graphicsBow.setParent(body);
        
        // Successfully initiated
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
    	
    	// Game logic here
    	// Nothing here yet
    	
    	// Simulate physics
    	world.update(deltaTime);
    	
    	// Change camera position
    	window.setRelativeTransform(Transform.I.scaled(10.0f));
    	// Render scene
    	graphicsCrate.draw(window);
    	graphicsBow.draw(window);
    	
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
