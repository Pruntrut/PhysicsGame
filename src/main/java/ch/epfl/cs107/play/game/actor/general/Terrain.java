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
	
	private Color fillColor = new Color(0x805500);
	private Color outlineColor = new Color(0x00b300);
	
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
			90.0f, 0.0f,
			6500.0f, -1000.0f
		);
		
	private ShapeGraphics terrainGraphics;
	
	/**
	 * Creates a new terrain
	 * @param game
	 * @param fixed 
	 * @param friction
	 * @param position
	 * @param shape : shape of terrain, non null
	 */
	public Terrain(ActorGame game, float friction, Vector position, Polyline shape) {
		super(game, true, position);
		
		if (shape == null) {
			throw new NullPointerException("Shape cannot be null");
		}
		
		terrainShape = shape;
		
		buildPart(friction);
		makeGraphics();
	}
	
	/**
	 * Creates a new Terrain at origin
	 * @param game
	 * @param friction
	 * @param shape : shape of terrain, non null
	 */
	public Terrain(ActorGame game, float friction, Polyline shape) {
		super(game, true);
		
		if (shape == null) {
			throw new NullPointerException("Shape cannot be null");
		}
		
		terrainShape = shape;
		
		buildPart(friction);
		makeGraphics();
	}
	
	/**
	 * Creates a new Terrain of default shape
	 * @param game
	 * @param friction
	 * @param position
	 */
	public Terrain(ActorGame game, float friction, Vector position) {
		this(game, friction, position,DEFAULT_TERRAIN_SHAPE);
	}
	
	/**
	 * Creates a new Terrain at origin with default terrain shape
	 * @param game
	 * @param friction
	 */
	public Terrain(ActorGame game, float friction) {
		this(game, friction, DEFAULT_TERRAIN_SHAPE);
	}

	/**
	 * Builds the part of the terrain
	 * @param friciton
	 */
	private void buildPart(float friciton) {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setFriction(friciton);
		partBuilder.setShape(terrainShape);
		partBuilder.build();
	}
	
	/**
	 * Sets the fill color
	 * @param color
	 */
	public void setFillColor(Color color) {
		this.fillColor = color;
		terrainGraphics.setFillColor(color);
	}
	
	/**
	 * Sets the outline color
	 * @param color
	 */
	public void setOutlineColor(Color color) {
		this.outlineColor = color;
		terrainGraphics.setOutlineColor(color);
	}
	
	/** 
	 * Creates the graphics
	 */
	private void makeGraphics() {
		terrainGraphics = new ShapeGraphics(terrainShape, fillColor, outlineColor, 0.1f);
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
