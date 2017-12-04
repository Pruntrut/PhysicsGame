package ch.epfl.cs107.play.game.actor;

import java.awt.Color;

import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Message extends TextGraphics {
	
	private Canvas canvas;
	
	private long duration = 1000;
	private long initialTime;
	
	private boolean shown = false;
	
	public Message(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold,
			boolean italics, Vector anchor, float alpha, float depth, Canvas canvas) {
		super(text, fontSize, fillColor, outlineColor, thickness, bold, italics, anchor, alpha, depth);
				
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		initialTime = System.currentTimeMillis();
	}

	public Message(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold,
			boolean italics, Vector anchor, Canvas canvas) {
		super(text, fontSize, fillColor, outlineColor, thickness, bold, italics, anchor);
		
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		initialTime = System.currentTimeMillis();
	}

	public Message(String text, float fontSize, Color fillColor, Canvas canvas) {
		super(text, fontSize, fillColor);
				
		this.canvas = canvas;
		
		setParent(canvas);
		setRelativeTransform(Transform.I.translated(0.0f, -1.0f));	
		initialTime = System.currentTimeMillis();
	}
	
	/**
	 * Sets the message text and initializes the timer. Duration is relative
	 * to time method was called (by default : time of object creation)
	 * @param text : String to be shown
	 * @param duration : duration of message (starts after method called)
	 */
	public void prepareDraw(String text, long duration) {
		shown = false;
		
		this.duration = duration;
		this.setText(text);
		
		initialTime = System.currentTimeMillis();
	}
	
	/**
	 * Sets the message text and initializes the timer. Duration is relative
	 * to time method was called (by default : time of object creation)
	 * Here : duration = 1000ms
	 * @param text : String to be shown
	 */
	public void prepareDraw(String text) {
		this.duration = 1000;
		this.setText(text);
		
		initialTime = System.currentTimeMillis();
	}
	
	/**
	 * If duration has not elapsed, draws message. Starts to face out after 
	 * half of duration has elapsed 
	 */
	public void drawFade() {
		long elapsed = System.currentTimeMillis() - initialTime;
		
		if (elapsed < duration / 2) {
			setAlpha(1.0f);
			super.draw(canvas);
		} else if (elapsed < duration) {
			float alpha = 1.0f - (float)(elapsed - duration/2) / (float)(duration/2);
			setAlpha(alpha);
			super.draw(canvas);
		} else {
			shown = true;
		}
	}
	
	public boolean wasShown() {
		return shown;
	}
	

}
