package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Cyclist extends Node implements Graphics {
		
	private Color color;
	private float angle;
	private boolean lookingLeft;
	private static final float THICKNESS = 0.15f;
	
	private ShapeGraphics head;
	private ShapeGraphics arm;
	private ShapeGraphics back;
	private ShapeGraphics rightThigh;
	private ShapeGraphics leftThigh;
	private ShapeGraphics leftCalf;
	private ShapeGraphics rightCalf;
	
	/**
	 * Creates a new Cyclist
	 * @param parent
	 * @param angle : the inital angle of the wheel (for the pedaling animation)
	 * @param color : color of cyclist
	 */
	public Cyclist(Positionable parent, float angle, Color color) {
		
		this.angle = angle;
		this.color = color;
		
		// Draw head
		head = new ShapeGraphics(getHeadShape(), color, color, THICKNESS, 1.0f, 100.0f);
		head.setParent(parent);
		// Draw arm
		arm = new ShapeGraphics(getArmShape(), color, color, THICKNESS, 1.0f, 100.0f);
		arm.setParent(parent);
		// Draw back
		back = new ShapeGraphics(getBackShape(), color, color, THICKNESS, 1.0f, 100.0f);
		back.setParent(parent);
		// Draw left thigh
		leftThigh = new ShapeGraphics(getLeftThighShape(), null, color, THICKNESS, 1.0f, 100.0f);
		leftThigh.setParent(parent);
		// Draw left calf
		leftCalf = new ShapeGraphics(getLeftCalfShape(), null, color, THICKNESS, 1.0f, 100.0f);
		leftCalf.setParent(parent);
		// Draw right thigh
		rightThigh = new ShapeGraphics(getRightThighShape(), null, color, THICKNESS, 1.0f, 98.0f);
		rightThigh.setParent(parent);
		// Draw right calf
		rightCalf = new ShapeGraphics(getRightCalfShape(), null, color, THICKNESS, 1.0f, 98.0f);
		rightCalf.setParent(parent);
	}
	
	
	@Override
	public void draw(Canvas canvas) {
		// Update positons
		arm.setShape(getArmShape());
		back.setShape(getBackShape());
		leftThigh.setShape(getLeftThighShape());
		rightThigh.setShape(getRightThighShape());
		leftCalf.setShape(getLeftCalfShape());
		rightCalf.setShape(getRightCalfShape());
		
		head.draw(canvas);
		arm.draw(canvas);
		back.draw(canvas);
		leftThigh.draw(canvas);
		leftCalf.draw(canvas);
		rightThigh.draw(canvas);
		rightCalf.draw(canvas);
	}
	
	/**
	 * Sets the angle for pedaling animation
	 * @param angle : an angle (e.g. the angular position of wheel)
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	/**
	 * Changes direction of cyclist
	 * @param left : true for cyclist to look left
	 */
	public void setDirection(boolean left) {
		this.lookingLeft = left;
	}
	
	/**
	 * @return the model of this cyclist
	 */
	public CyclistModel createModel() {
		return new CyclistModel(color, THICKNESS,getHeadShape(), getArmShape(), getBackShape(), getLeftThighShape(), 
				getRightThighShape(), getLeftCalfShape(), getRightCalfShape(), getHeadLocation(), getShoulderLocation(), 
				getHipLocation(), getKneeLocation(true), getKneeLocation(false));
	}
	
	private Circle getHeadShape() {
		return new Circle(0.17f ,getHeadLocation());
	}
	
	private Polyline getArmShape() {
		return new Polyline(getShoulderLocation(), getHandLocation());
	}
	
	private Polyline getBackShape() {
		return new Polyline(getShoulderLocation(), getHipLocation());
	}
	
	private Polyline getLeftThighShape() {
		return new Polyline(getHipLocation(), getKneeLocation(true));
	}
	
	private Polyline getRightThighShape() {
		return new Polyline(getHipLocation(), getKneeLocation(false));
	}
	
	private Polyline getLeftCalfShape() {
		return new Polyline(getKneeLocation(true), getFootLocation(true));
	}
	
	private Polyline getRightCalfShape() {
		return new Polyline(getKneeLocation(false), getFootLocation(false));
	}

	private Vector getHeadLocation() {
		return new Vector(ajust()*0.05f, 1.7f);
	}
	
	private Vector getShoulderLocation() {
		return new Vector(ajust()*-0.1f, 1.5f);
	}
	
	private Vector getHandLocation() {
		return new Vector(ajust()*0.5f, 1.0f);
	}
	
	private Vector getHipLocation() {
		return new Vector(ajust()*-0.6f, 1f);
	}
	
	private Vector getKneeLocation(boolean left) {
		// Get angle between min and max angle
		float minAngle = (float)(ajust()*-Math.PI/25);
		float maxAngle = (float)(ajust()*Math.PI/25);
		float finalAngle;
		
		if (left) {
			finalAngle = (float) (minAngle + ajust()*(maxAngle - minAngle)*Math.sin(angle));
		}else {
			finalAngle = (float) (minAngle + ajust()*(maxAngle - minAngle)*Math.cos(angle + (float)Math.PI/2));
		}
		
		// Rotate around hip
		Vector initial = new Vector(0.0f, 0.8f).sub(getHipLocation());
		Vector rotated = initial.rotated(finalAngle);
		return rotated.add(getHipLocation());
	}
	
	private Vector getFootLocation(boolean left) {
		// Centre of rotation
		Vector centre = new Vector(ajust()*-0.1f, 0.0f);
		Vector initial = new Vector(ajust()*0.1f, 0.0f).sub(centre);
		Vector rotated;
		if (left) {
			rotated = initial.rotated(angle);
		} else {
			rotated = initial.rotated((float)Math.PI + angle);
		}

		return rotated.add(centre);
	}
	/**
	 * @return 1 or -1 depending on looking left (to allow cyclist to rotate
	 */
	private float ajust() {
		if (lookingLeft) {
			return -1;
		} 
		return 1;
	}
	
}
