package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;

/**
 * This is a collection of Shapes that form the cyclist
 * Used to pass Model of cyclist from Cyclist to Ragdoll
 */
public class CyclistModel {
	public final Color color;
	public final float thickness;
	
	public final Vector headLocation;
	public final Vector shoulderLocation;
	public final Vector hipLocation;
	public final Vector leftKneeLocation;
	public final Vector rightKneeLocation;
	 
	public final Circle head;
	public final Polygon arm;
	public final Polygon back;
	public final Polygon leftThigh;
	public final Polygon rightThigh;
	public final Polygon leftCalf;
	public final Polygon rightCalf;
	
	/**
	 * Creates a new Cyclist
	 * @param color
	 * @param thickness
	 * @param head
	 * @param arm
	 * @param back
	 * @param leftThigh
	 * @param rightThigh
	 * @param leftCalf
	 * @param rightCalf
	 * @param shoulderLocation
	 * @param hipLocation
	 * @param leftKneeLocation
	 * @param rightKneeLocation
	 */
	public CyclistModel(Color color, float thickness,Circle head, Polyline arm, Polyline back, Polyline leftThigh, Polyline rightThigh,
			Polyline leftCalf, Polyline rightCalf, Vector headLocation,Vector shoulderLocation, Vector hipLocation, Vector leftKneeLocation, Vector rightKneeLocation) {
		// General properties
		this.color = color;
		this.thickness = thickness;
		
		// Position of joints
		this.headLocation = headLocation;
		this.shoulderLocation = shoulderLocation;
		this.hipLocation = hipLocation;
		this.leftKneeLocation = leftKneeLocation;
		this.rightKneeLocation = rightKneeLocation;
		
		// Shapes
		this.head = head;
		this.arm = convertPolylineToPolygon(arm);
		this.back = convertPolylineToPolygon(back);
		this.leftThigh = convertPolylineToPolygon(leftThigh);
		this.rightThigh = convertPolylineToPolygon(rightThigh);
		this.leftCalf = convertPolylineToPolygon(leftCalf);
		this.rightCalf = convertPolylineToPolygon(rightCalf);
	}
	
	/**
	 * Converts a polyline from the cyclist model to a proper polygon so that it can be used by an entity
	 * @param polyline
	 * @return the converted polygon
	 */
	private Polygon convertPolylineToPolygon(Polyline polyline) {
		if (polyline.getPoints().size() != 2) {
			throw new IllegalArgumentException("This method only takes polylines made of two points");
		}
		
		Vector firstPoint = polyline.getPoints().get(0);
		Vector secondPoint = polyline.getPoints().get(1);
		float angle = secondPoint.sub(firstPoint).getAngle();
		
		Vector upperFirstPoint = firstPoint.rotated(-angle).add(new Vector(0.0f, thickness/2)).rotated(angle);
		Vector lowerFirstPoint = firstPoint.rotated(-angle).sub(new Vector(0.0f, thickness/2)).rotated(angle);
		Vector upperSecondPoint = secondPoint.rotated(-angle).add(new Vector(0.0f, thickness/2)).rotated(angle);
		Vector lowerSecondPoint = secondPoint.rotated(-angle).sub(new Vector(0.0f, thickness/2)).rotated(angle);
		
		return new Polygon(lowerFirstPoint,upperFirstPoint,upperSecondPoint,lowerSecondPoint);
	}
	
	/**
	 * @return a list of all the shapes in this model
	 */
	public List<Shape> getListShapes() {
		return Arrays.asList(head, arm, back, leftThigh, rightThigh, leftCalf, rightCalf);
	}
}
