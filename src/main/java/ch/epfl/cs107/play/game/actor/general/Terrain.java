package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {
	
	private static final Color BROWN = new Color(0x805500);
	private static final Color GREEN = new Color(0x00b300);
	
	private Polyline terrainShape;
	private static final Polyline DEFAULT_TERRAIN_SHAPE = new Polyline(
			-1000.0f, -1000.0f,
			-1000.0f, 0.0f,
			2.0f, 0.0f,
			4.0f, 1.0f,
			8.0f, 1.0f,
			15.0f, 3.0f,
			16.0f, 3.0f,
			25.0f, 0.0f,
			35.0f, -5.0f,
			50.0f, -5.0f,
			55.0f, -4.0f,
			65.0f, 0.0f,
			6500.0f, -1000.0f
		);
		
	private ShapeGraphics terrainGraphics;
	
	public Terrain(ActorGame game, boolean fixed, float friction, Vector position, Polyline shape) {
		super(game, fixed, position);
		
		if (shape == null) {
			throw new NullPointerException("Shape cannot be null");
		}
		
		terrainShape = shape;
		
		buildPart(friction);
		makeGraphics();
	}
	
	public Terrain(ActorGame game, boolean fixed, float friction, Polyline shape) {
		super(game, fixed);
		
		if (shape == null) {
			throw new NullPointerException("Shape cannot be null");
		}
		
		terrainShape = shape;
		
		buildPart(friction);
		makeGraphics();
	}
	
	public Terrain(ActorGame game, boolean fixed, float friction, Vector position) {
		this(game, fixed, friction, position,DEFAULT_TERRAIN_SHAPE);
	}
	
	public Terrain(ActorGame game, boolean fixed, float friction) {
		this(game, fixed, friction, DEFAULT_TERRAIN_SHAPE);
	}

	
	private void buildPart(float friciton) {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setFriction(friciton);
		partBuilder.setShape(terrainShape);
		partBuilder.build();
	}
	
	private void makeGraphics() {
		terrainGraphics = new ShapeGraphics(terrainShape, BROWN, GREEN, 0.1f);
		terrainGraphics.setParent(getEntity());
	}
	
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}

	@Override
	public void draw(Canvas canvas) {
		terrainGraphics.draw(canvas);
	}

	
}
