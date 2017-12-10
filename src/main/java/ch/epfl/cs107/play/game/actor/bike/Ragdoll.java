package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WeldConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

/**
 * A physical model (entity) of the cyclist body
 */
public class Ragdoll extends GameEntity implements Actor {
	
	private CyclistModel model;
	
	private Entity head;
	private Entity arm;
	private Entity back;
	private Entity leftThigh;
	private Entity rightThigh;
	private Entity leftCalf;
	private Entity rightCalf;
	
	/**
	 * Creates a new ragdoll
	 * @param game
	 * @param model : model on which the ragdoll will be based on
	 * @param position
	 * @param velocity
	 * @param angularPosition
	 * @param angularVelocity
	 */
	public Ragdoll(ActorGame game, CyclistModel model, Vector position, Vector velocity, float angularPosition, float angularVelocity) {
		super(game, false, position);
		
		this.model = model;
		
		// Build the entities
		head = buildEntity(model.head, position, velocity, angularPosition, angularVelocity);
		arm = buildEntity(model.arm, position, velocity, angularPosition, angularVelocity);
		back = buildEntity(model.back, position, velocity, angularPosition, angularVelocity);
		leftThigh = buildEntity(model.leftThigh, position, velocity, angularPosition, angularVelocity);
		rightThigh = buildEntity(model.rightThigh, position, velocity, angularPosition, angularVelocity);
		leftCalf = buildEntity(model.leftCalf, position, velocity, angularPosition, angularVelocity);
		rightCalf = buildEntity(model.rightCalf, position, velocity, angularPosition, angularVelocity);
		
		// Attach head to shoulder
		WeldConstraintBuilder weldBuilder = getOwner().getWeldConstraintBuilder();
		weldBuilder.setFirstEntity(head);
		weldBuilder.setFirstAnchor(model.headLocation);
		weldBuilder.setSecondEntity(back);
		weldBuilder.setSecondAnchor(model.shoulderLocation);
		weldBuilder.setInternalCollision(false);
		weldBuilder.build();
		
		// Attach arm to shoulder
		createRevoluteContraint(arm, back, model.shoulderLocation, false);
		// Attach other joints
		createRevoluteContraint(leftThigh, back, model.hipLocation);
		createRevoluteContraint(rightThigh, back, model.hipLocation);
		createRevoluteContraint(leftCalf, leftThigh, model.leftKneeLocation);
		createRevoluteContraint(rightCalf, rightThigh, model.rightKneeLocation);
		
	}
	
	/**
	 * Creates a new revolute contraint around position (with internal collision)
	 * @param first : the first entity
	 * @param second : the second entity
	 * @param positon : the position of rotation
	 */
	private void createRevoluteContraint(Entity first, Entity second, Vector positon) {
		createRevoluteContraint(first, second, positon, true);
	}
	
	/**
	 * Creates a new revolute contraint around position (with internal collision)
	 * @param first : the first entity
	 * @param second : the second entity
	 * @param positon : the position of rotation
	 * @param internalCollision
	 */
	private void createRevoluteContraint(Entity first, Entity second, Vector positon, boolean internalCollision) {
		RevoluteConstraintBuilder builder = getOwner().getRevoluteConstraintBuilder();
		builder.setFirstEntity(first);
		builder.setFirstAnchor(positon);
		builder.setSecondEntity(second);
		builder.setSecondAnchor(positon);
		builder.setInternalCollision(true);
		builder.build();
	}
	
	/**
	 * Creates a new entity
	 * @param shape
	 * @param position
	 * @param velocity
	 * @param angularPosition
	 * @param angularVelocity
	 * @return the built entity
	 */
	private Entity buildEntity(Shape shape, Vector position, Vector velocity, float angularPosition, float angularVelocity) {
		Entity entity = getOwner().createEntity(false, position, velocity, angularPosition, angularVelocity);
		PartBuilder pb = entity.createPartBuilder();
		pb.setShape(shape);
		pb.build();
		
		return entity;
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
		canvas.drawShape(model.head, head.getTransform(), model.color, model.color, model.thickness, 1.0f, 100.0f);
		canvas.drawShape(model.arm, arm.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
		canvas.drawShape(model.back, back.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
		canvas.drawShape(model.leftCalf, leftCalf.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
		canvas.drawShape(model.rightCalf, rightCalf.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
		canvas.drawShape(model.leftThigh, leftThigh.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
		canvas.drawShape(model.rightThigh, rightThigh.getTransform(), model.color, model.color, 0.0f, 1.0f, 100.0f);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		head.destroy();
		arm.destroy();
		back.destroy();
		leftThigh.destroy();
		rightThigh.destroy();
		leftCalf.destroy();
		rightCalf.destroy();
	}

}
