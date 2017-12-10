package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Bike extends GameEntity implements Actor {

	private static final float MAX_WHEEL_SPEED = 20.0f;
	private static final float WHEEL_RADIUS = 0.5f;
	private boolean lookingLeft = false;
	
	private boolean frozen = false;
	
	private Wheel leftWheel;
	private Wheel rightWheel;
	
	private boolean hit = false;
	
	private Cyclist cyclist;
	
	private Polygon hitbox = new Polygon(
		0.0f, 0.5f,
		0.5f, 1.0f,
		0.0f, 2.0f,
		-0.5f, 1.0f
	);
	
	private ShapeGraphics hitboxGraphics;
	
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		
		buildWheels(game, fixed, Vector.ZERO);
		buildPart();
		setupContactListener();
		
		makeGraphics();
	}

	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		
		buildWheels(game, fixed, position);
		buildPart();
		setupContactListener();
		
		makeGraphics();
	}
	
	private void buildWheels(ActorGame game, boolean fixed, Vector position) {
		leftWheel = new Wheel(game, fixed, position.add(new Vector(-1.0f, 0.0f)), WHEEL_RADIUS);
		rightWheel = new Wheel(game, fixed, position.add(new Vector(1.0f, 0.0f)), WHEEL_RADIUS);
		
		leftWheel.attach(getEntity(), new Vector(-1.0f,  0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getEntity(), new Vector(1.0f,  0.0f), new Vector(0.5f, -1.0f));
	}
	
	private void buildPart() {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		partBuilder.setShape(hitbox);
		partBuilder.setGhost(true);
		partBuilder.build();
	}
	
	private void makeGraphics() {
		cyclist = new Cyclist(getEntity(), (float)Math.PI/2, Color.WHITE);
		hitboxGraphics = new ShapeGraphics(hitbox, Color.RED, null, 0.1f, 0.5f, 0.0f);
		hitboxGraphics.setParent(getEntity());
		
	}	
	
	private void setupContactListener() {
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
					return;
				if (leftWheel.isSameEntity(contact.getOther().getEntity()) 
					|| rightWheel.isSameEntity(contact.getOther().getEntity())) {
					return;
				}
				
				hit = true;
			}
			
			@Override
			public void endContact(Contact contact) {}
		};
		
		getEntity().addContactListener(listener);
	}
	
	@Override
	public void update(float deltaTime) {		
		if (!hit && !frozen) {			
			updateControls();
			cyclist.setAngle(leftWheel.getAngularPositon());
		}
	}
	
	/**
	 * Changes state of bike according to key pressed
	 */
	private void updateControls() {
		
		Keyboard keyboard = getOwner().getKeyboard();
		
		// By default, release wheels
		leftWheel.relax();
		rightWheel.relax();
		
		// If spacebar pressed, invert direction of bike
		if (keyboard.get(KeyEvent.VK_SPACE).isPressed()) {
			lookingLeft = !lookingLeft;
		}
		
		// If down arrow is pressed, turn motors on, immobilize them (speed = 0)
		if (keyboard.get(KeyEvent.VK_DOWN).isDown()) {
			if (lookingLeft) {
				rightWheel.power(0.0f);
			} else {
				leftWheel.power(0.0f);
			}	
		}
		
		// If up arrow is pressed, set driving wheel's speed to the max speed
		if (keyboard.get(KeyEvent.VK_UP).isDown()) {
			
			if (lookingLeft && rightWheel.getSpeed() < MAX_WHEEL_SPEED) {
				rightWheel.power(MAX_WHEEL_SPEED);
			} else if (!lookingLeft && leftWheel.getSpeed() > -MAX_WHEEL_SPEED){
				leftWheel.power(-MAX_WHEEL_SPEED);
			} else {
				leftWheel.relax();
				rightWheel.relax();
			}
		}
		
		// If left arrow is pressed, apply angular force to entity (lean left)
		if (keyboard.get(KeyEvent.VK_LEFT).isDown()) {
			getEntity().applyAngularForce(10.0f);
		}
		
		// If left arrow is pressed, apply angular force to entity (lean left)
		if (keyboard.get(KeyEvent.VK_RIGHT).isDown()) {
			getEntity().applyAngularForce(-10.0f);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		
		
		if (!hit) {
			cyclist.setDirection(lookingLeft);
			cyclist.draw(canvas);
		}
		
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		leftWheel.destroy();
		rightWheel.destroy();
	}
	
	/**
	 * @return a ragdoll version of the cyclist
	 */
	public Ragdoll createRagdoll() {
		return new Ragdoll(getOwner(), cyclist.createModel(), getEntity().getPosition(), getEntity().getVelocity(), 
				getEntity().getAngularPosition(), getEntity().getAngularVelocity());
	}

	/**
	 * If true freezes bike in place until set to false
	 * @param frozen
	 */
	public void setFreeze(boolean frozen) {
		this.frozen = frozen;
	}
	
	public boolean isHit() {
		return hit;
	}
	
	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}
	
}
