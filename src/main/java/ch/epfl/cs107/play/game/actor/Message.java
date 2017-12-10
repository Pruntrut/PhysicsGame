package ch.epfl.cs107.play.game.actor;

import java.awt.Color;

import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Message extends TextGraphics {
	
	private Canvas canvas;
	
	private float duration = 0;
	private float timeLeft = 0;
	
	private boolean shown = false;
	
	/**
	 * Creates a new Message
	 * @param fontSize
	 * @param fillColor
	 * @param outlineColor
	 * @param thickness
	 * @param bold
	 * @param italics
	 * @param anchor
	 * @param alpha
	 * @param depth
	 * @param canvas
	 */
	public Message(float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold,
			boolean italics, Vector anchor, float alpha, float depth, Canvas canvas) {
		super("", fontSize, fillColor, outlineColor, thickness, bold, italics, anchor, alpha, depth);
				
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}

	/**
	 * Creates a new Message
	 * @param fontSize
	 * @param fillColor
	 * @param outlineColor
	 * @param thickness
	 * @param bold
	 * @param italics
	 * @param anchor
	 * @param alpha
	 * @param depth
	 * @param canvas
	 */
	public Message(float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold,
			boolean italics, Vector anchor, Canvas canvas) {
		super("", fontSize, fillColor, outlineColor, thickness, bold, italics, anchor);
		
		if (canvas == null) {
			throw new NullPointerException("Canvas cannot be null");
		}
		
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}

	/**
	 * Creates a new Message
	 * @param fontSize
	 * @param fillColor
	 * @param outlineColor
	 * @param thickness
	 * @param bold
	 * @param italics
	 * @param anchor
	 * @param alpha
	 * @param depth
	 * @param canvas
	 */
	public Message(float fontSize, Color fillColor, Canvas canvas) {
		super("", fontSize, fillColor);
				
		if (canvas == null) {
			throw new NullPointerException("Canvas cannot be null");
		}
		
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));	
	}
	
	/**
	 * Sets the message text and initializes the timer. Duration is relative
	 * to time method was called (by default : time of object creation)
	 * @param text : String to be shown
	 * @param duration : duration of message (starts after method called) in seconds
	 */
	public void prepareDrawFade(String text, float duration) {
		shown = false;
		
		this.duration = duration;
		this.timeLeft = duration;
		this.setText(text);
	}
	
	/**
	 * Sets the message text and initializes the timer. Duration is relative
	 * to time method was called (by default : time of object creation)
	 * Here : duration = 1s
	 * @param text : String to be shown
	 */
	public void prepareDrawFade(String text) {
		prepareDrawFade(text, 1.0f);
	}
	
	/**
	 * If duration has not elapsed, draws message. Starts to face out after 
	 * half of duration has elapsed 
	 */
	public void drawFade(float deltaTime) {
		// Update timeLeft
		if (timeLeft > 0) {
			timeLeft -= deltaTime;
		}
		
		// Show message at full alpha
		if (timeLeft > duration / 2) {
			setAlpha(1.0f);
			super.draw(canvas);
		} else if (timeLeft > 0) {
			float alpha = timeLeft / (duration/2);
			setAlpha(alpha);
			super.draw(canvas);
		} else {
			shown = true;
		}
	}
	
	/**
	 * Clears current text and duration
	 */
	public void clear() {
		setText("");
		duration = 0;
		timeLeft = 0;
		shown = false;
	}
	
	/**
	 * @return true if duration has elapsed since last prepareDrawFade
	 */
	public boolean wasShown() {
		return shown;
	}
	

}
