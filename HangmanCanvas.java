/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		drawScaffold();
		bodyPart = 0;
		guessedLetters = "";
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		// Clear last wordLabel
		if (wordLabel != null) {
			remove(wordLabel);
		}
		
		wordLabel = new GLabel(word);
		
		wordLabel.setFont("SansSerifBOLD-20");
		wordLabel.setLocation(getWidth()/6, 17*getHeight()/20 + (wordLabel.getAscent() + wordLabel.getDescent())/2 - wordLabel.getDescent());

		add(wordLabel);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		boolean guessedBefore = false;
			
		displayBodyPart(bodyPart);
		
		for (int i = 0; i < guessedLetters.length(); ++i) {
			if (guessedLetters.charAt(i) == letter) {
				guessedBefore = true;
			}
		}				 
		
		if (!guessedBefore) {
			guessedLetters += letter;
		}
		
		displayGuessedLetters();
		
		bodyPart++;		
	}

	private void drawScaffold() {
		drawPole();
		drawBeam();
		drawRope();
	}

	private void displayBodyPart(int part) {
		switch (part) {
			case 0:
				drawHead();
				break;
			case 1:
				drawBody();
				break;
			case 2:
				drawLeftArm();
				break;
			case 3:
				drawRightArm();
				break;
			case 4:
				drawLeftLeg();
				break;
			case 5:
				drawRightLeg();
				break;
			case 6:
				drawLeftFoot();
				break;
			case 7:
				drawRightFoot();
				break;
			default:
				throw new ErrorException("displayBodyPart: Illegal body part");
		}
	}

	private void displayGuessedLetters() {
		// Clear last label
		if (guessedLettersLabel != null) {
			remove(guessedLettersLabel);
		}
		
		// Display new label
		guessedLettersLabel = new GLabel(guessedLetters);
		
		guessedLettersLabel.setFont("SansSerifBOLD-17");
		guessedLettersLabel.setLocation(getWidth()/6, 17.5*getHeight()/20 + (guessedLettersLabel.getAscent() + guessedLettersLabel.getDescent()/2) - guessedLettersLabel.getDescent());
		
		add(guessedLettersLabel);
	}	

	private void drawPole() {
		add(new GLine(getWidth()/2 - BEAM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET + SCAFFOLD_HEIGHT/2, getWidth()/2 - BEAM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2));
	}

	private void drawBeam() {
		add(new GLine(getWidth()/2 - BEAM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2, getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2));
	}

	private void drawRope() {
		add(new GLine(getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2, getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH));
	}

	private void drawHead() {
		//add(new GOval(getWidth()/2 - HEAD_RADIUS, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH, getWidth()/2 + HEAD_RADIUS, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS));
		GOval head = new GOval(2*HEAD_RADIUS, 2*HEAD_RADIUS);
		
		head.setLocation(getWidth()/2 - HEAD_RADIUS, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH);
		
		add(head);
	}
	
	private void drawBody() {
		add(new GLine(getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS, getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH));
	}

	private void drawLeftArm() {
		add(new GLine(getWidth()/2 - UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD));
		add(new GLine(getWidth()/2 - UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth()/2 - UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH));
	}

	private void drawRightArm() {
		add(new GLine(getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth()/2 + UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD));
		add(new GLine(getWidth()/2 + UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, getWidth()/2 + UPPER_ARM_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH));
	}
	
	private void drawLeftLeg() {
		add(new GLine(getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH)); 
		add(new GLine(getWidth()/2 - HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH));
	} 	

	private void drawRightLeg() {
		add(new GLine(getWidth()/2, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, getWidth()/2 + HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH));
		add(new GLine(getWidth()/2 + HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH, getWidth()/2 + HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH));
	}

	private void drawLeftFoot() {
		add(new GLine(getWidth()/2 - HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH, getWidth()/2 - HIP_WIDTH - FOOT_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH));
	}

	private void drawRightFoot() {
		add(new GLine(getWidth()/2 + HIP_WIDTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH, getWidth()/2 + HIP_WIDTH + FOOT_LENGTH, getHeight()/2 - SCAFFOLD_OFFSET - SCAFFOLD_HEIGHT/2 + ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH));
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int SCAFFOLD_OFFSET = 10;

	private int 	bodyPart = 0;
	private String 	guessedLetters = "";
	
	private GLabel	wordLabel = null;	
	private GLabel	guessedLettersLabel = null;
}
