/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private HangmanLexicon hangmanWords;
	
	private HangmanCanvas canvas;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public static final int MAX_GUESSES = 8;

	// Tracks the number of guesses the player has
	private int guessCounter = MAX_GUESSES;
	
	private boolean automated = false;
	
    @Override
	public void run() {
    	if (!automated) {
        	setUpGame();
    		playGame();
    	}
	}
    
    //This is the word being guessed
	private String hiddenWord;
    
	//This is the secret word
	private String word = pickWord();
	
	//This is the latest character entered by the user
	private char ch;
	
	//This string keeps track of all the incorrect guessed letters
	private String incorrectLetters = "";
	
    /*Set up the game by displaying the welcome message, 
     *how many letters there are in the word, 
     *and how many guesses the user has
     */
    public void setUpGame() {
    	canvas.reset();
    	hiddenWord = showNumberOfLetters();
    	canvas.displayWord(hiddenWord);
    	println("Welcome to Hangman!");
    	println("The word now looks like this: " + hiddenWord);
    	println("You have " + guessCounter + " guesses left.");
    }
	
    //Generates a random word selected from the HangmanLexicon
    private String pickWord() {
    	hangmanWords = new HangmanLexicon("HangmanLexicon.txt");
    	int randomWord = rgen.nextInt(0, (hangmanWords.getWordCount())); 
    	String pickedWord = hangmanWords.getWord(randomWord);
    	return pickedWord;
    }
	
    //Shows how many letters there are in the word as part of game setup
	private String showNumberOfLetters() {
		String result = "";
		for(int i = 0; i< word.length(); i++) {
			result = result + "-";
		}
		return result;
		}
	
	private void getCharGuess() {
		String getChar = readLine("Your guess: ");
		while (true) {
			if(getChar.length() > 1) {
				getChar = readLine("You can only guess one letter at a time. Try again: ");
			}
			if(getChar.length() == 1) break;
		}
		ch = Character.toUpperCase(getChar.charAt(0));
	}
	
	private void checkForLoss() {
		if (guessCounter == 0) {
			println("You're completely hung.");
			println("The word was:" + word);
			println("You lose.");
		}
	}
	
	private void playGame() {
		while(guessCounter > 0) {
			getCharGuess();
			letterCheck();
			if (hiddenWord.equals(word)) {
				println("You guessed the word: " + word);
				println("You win");
				break;
			}
			println("The word now looks like this: " + hiddenWord);
			println("You have " + guessCounter + " guesses left.");
		}
		
		checkForLoss();
	}
	
	public void step() {
		if (!automated) {
			return;
		}
		
		if(guessCounter > 0) {
			getCharGuess();
			letterCheck();
			if (hiddenWord.equals(word)) {
				println("You guessed the word: " + word);
				println("You win");
				return;
			}
			println("The word now looks like this: " + hiddenWord);
			println("You have " + guessCounter + " guesses left.");
		}
		
		checkForLoss();
	}
	
	//updates the hiddenWord if the character entered is correct 
	private void letterCheck() {
		//checks to see if the guessed letter is in the word
		if(word.indexOf(ch) == -1) {
			println("There are no " + ch + "'s in the word");
			guessCounter--;
			incorrectLetters = incorrectLetters + ch;
			canvas.noteIncorrectGuess(incorrectLetters);
		}
		if(word.indexOf(ch) != -1) {
			println("The guess is correct.");
		}
		//goes through each of the letters in the word and checks if it matches the guessed letter, 
		//if it's a match, updates the hidden word to reveal the position of the guessed letter
		for(int i = 0; i < word.length(); i++) {
			if (ch == word.charAt(i)) {
				if (i > 0) {
					hiddenWord = hiddenWord.substring(0, i) + ch + hiddenWord.substring(i + 1);
				}
				if(i == 0) {
					hiddenWord = ch + hiddenWord.substring(1);
				}
				canvas.displayWord(hiddenWord);
			}
		}
	}
  
	@Override
	public void init() {
		this.resize(800, 600);
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public Hangman(boolean automated) {
		this.automated = automated;
	}

	//we need to have a default constructor for the Applet to properly load
	//since we have the overloaded constructor for automation
	public Hangman() {
		super();
	}
/*
 * These are a bunch of accessor methods to get values of private variables.
 * These are useful to have for testing.
 */
	public int getGuessCounter() {
		return guessCounter;
	}
	
	public String getHiddenWord() {
		return hiddenWord;
	}
	
	public String getWord() {
		return word;
	}
	
	public char getCh() {
		return ch;
	}
	
	public String getIncorrectLetters() {
		return incorrectLetters;
	}
}
