/*
 * File: Hangman.java
 * ----------------------
 * Class plays a game of hangman.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {

    private static final int MAX_NUM_GUESSES = 8;

    public void run() {
        boolean playAgain = false;
        
        do {
        	println("");
        	println("Welcome to Hangman!");
        	setupGame();
        	playGame();
        	
        	playAgain = playAgain();
        } while (playAgain == true);
    }

    private void setupGame() {
        gameWon = false;
        numGuesses = MAX_NUM_GUESSES;
        wordToGuess = lexicon.getWord(rgen.nextInt(lexicon.getWordCount()));
        guessedLetters = "";
        updateState();
    }

    private void playGame() {
        while (numGuesses > 0 && gameWon == false) {
            printState();
            printGuessesLeft();
            
            char guess = (getUserGuess());
            // Is guess correct?
            checkGuess(guess);

            addToGuessedLetters(guess);
            updateState();
            gameWon = checkGameWon();
        }

        if (gameWon == true) {
            printWinMsg();
        } else {
            printLoseMsg();
        }
    }
    
    private boolean playAgain() {
    	boolean playAgain = false;
    	
    	String input = readLine("Play again? (y/n): ");
    	input = input.toLowerCase();

    	while (input.length() != 1 || (input.charAt(0) != 'y' && input.charAt(0) != 'n')) {
    		println("Illegal input.");
    		input = readLine("Play again? (y/n): ");
    		input = input.toLowerCase();
    	}
    
    	if (input.charAt(0) == 'y') {
    		playAgain = true;
    	} else if (input.charAt(0) == 'n') {
    		playAgain = false;
    	}
    	
    	return playAgain;
    }

    // Updates current guess state
    private void updateState() {
        // Clear state
        state = "";

        // Check if any chars in guessedLetters match chars in wordToGuess
        for (int i = 0; i < wordToGuess.length(); ++i) {
            char charToCheck = wordToGuess.charAt(i);
            boolean foundLetter = false;
            
            for (int j = 0; j < guessedLetters.length(); ++j) {
                if (guessedLetters.charAt(j) == charToCheck) {
                    foundLetter = true;
                }
            }
            
            // Update state to match guessedLetters
            if (foundLetter) {
                state += charToCheck;
            } else {
                state += '-';
            }
        }
    }

    private void printState() {
        println("The word now looks like: " + state);    
    }

    // Prints number of guesses left
    private void printGuessesLeft() {
        println("You have " + numGuesses + " left.");
    }

    private char getUserGuess() {      
        String userGuess = readLine("Your guess: ");
        
        // Convert to upper case
        userGuess = userGuess.toUpperCase();
        
        // If user does not guess a valid letter or inputs more than one letter
        while (userGuess.length() != 1 || userGuess.charAt(0) < 'A' || userGuess.charAt(0) > 'Z') {
           println("Illegal guess, please try again.");
           
           userGuess = readLine("Your guess: ");
           userGuess = userGuess.toUpperCase();
        }
        
        return userGuess.charAt(0);
    }

	/* Function checks if guessed character is in word to be guessed
	 * and prints a message indicating to the user whether or not
	 * the guess is correct. If the guess is not correct, the number of
	 * total guesses is decreased by one.
	 */
    private void checkGuess(char userGuess) {
        boolean foundGuess = false;
        
        for (int i = 0; i < wordToGuess.length(); ++i) {
            if (wordToGuess.charAt(i) == userGuess) {
                foundGuess = true;
            }
        }
        
        if (foundGuess == false) {
            --numGuesses;
            println("There are no " + userGuess + "'s in the word.");
        } else {
            if (guessedBefore(userGuess) == false) {
                println("That guess is correct.");
            }
        }        
    }

    private void addToGuessedLetters(char userGuess) {
        if (guessedBefore(userGuess) == false) {
            guessedLetters += userGuess;
        }
    }

    private boolean guessedBefore(char userGuess) {
        boolean guessedBefore = false;
        
        for (int i = 0; i < guessedLetters.length(); ++i) {
            if (guessedLetters.charAt(i) == userGuess) {
                guessedBefore = true;
            }
        }
        
        return guessedBefore;
    }

    private boolean checkGameWon() {
        boolean won = false;

        if (state.equals(wordToGuess)) {
            won = true;
        }
    
        return won;
    }

    private void printWinMsg() {
        println("You guessed the word: " + wordToGuess);
        println("You win.");
    }
    
    private void printLoseMsg() {
        println("You're completely hung.");
        println("The word was: " + wordToGuess);
        println("You lose.");
    }
        

    boolean     gameWon;
    int         numGuesses;

    String      wordToGuess;
    String      state = "";
    String      guessedLetters = "";

    HangmanLexicon      lexicon = new HangmanLexicon();
    RandomGenerator     rgen = RandomGenerator.getInstance();
    
}