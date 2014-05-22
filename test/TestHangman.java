import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import acm.io.IOConsole;

public class TestHangman {
	private Hangman hangman;
	private static final String INPUT_FILE = "TestInput.txt";
	private PrintWriter outputStream;
	private String wordToGuess;
	
	private void runAutomatically() throws FileNotFoundException {
		BufferedReader inputStream = new BufferedReader(new FileReader(new File(INPUT_FILE)));
		hangman.getConsole().setInputScript(inputStream);
	}
	
	private char getAnIncorrectGuess() {
		char guess = ' ';
		for (char c = 'A'; c <= 'Z'; c++) {
			if (wordToGuess.indexOf(c) == -1) {
				guess = c;
				break;
			}
		}
		
		return guess;
	}
	
	@Before
	public void setUp() throws FileNotFoundException {
		hangman = new Hangman(true);
		hangman.setConsole(IOConsole.SYSTEM_CONSOLE);
		hangman.init();
		hangman.setUpGame();
		
		wordToGuess = hangman.getWord();
		
		outputStream = new PrintWriter(new PrintStream(new File(INPUT_FILE)), true);
	}

	@Test
	public void testHangman() {
		assertNotNull(hangman);
		
		assertTrue("Game should not make user try to guess an empty string", !(wordToGuess.equalsIgnoreCase("")));
		
		int guessCounter = hangman.getGuessCounter();
		String hiddenWord = hangman.getHiddenWord();
		String incorrectLetters = hangman.getIncorrectLetters();
		
		assertEquals("Guess counter should start at the max", Hangman.MAX_GUESSES, guessCounter);
		
		for (int i = 0; i < hiddenWord.length(); i++) {
			assertEquals("Each character of the word should be hidden at the start - failed for letter at index " + i, 
					'-', hiddenWord.charAt(i));
		}
		
		assertTrue("Incorrect letters should be blank at the start", incorrectLetters.equalsIgnoreCase(""));
	}
	
	@Test
	public void testGetCharGuess() throws FileNotFoundException {
		char[] tests = new char[3];
		tests[0] = 'A';
		tests[1] = 'B';
		tests[2] = 'Z';
		
		for (int i = 0; i < tests.length; i++) {
			outputStream.println(tests[i]);
		}
		
		outputStream.close();
		
		runAutomatically();
		
		for (int i = 0; i < 3; i++) {
			hangman.step();
			assertEquals("Most recent guess should reflect what the user just typed", tests[i], hangman.getCh());
		}
	}

	@Test
	public void testIncorrectGuess() throws FileNotFoundException {
		char guess = getAnIncorrectGuess();
		
		assertTrue("There should be at least one character from A to Z that isn't in the word", guess != ' ');
		outputStream.println(guess);
		outputStream.close();
		
		int guessesBefore = hangman.getGuessCounter();
		String hiddenWordBefore = hangman.getHiddenWord();
		String incorrectLettersBefore = hangman.getIncorrectLetters();
		
		runAutomatically();
		hangman.step();
		
		int guessesAfter = hangman.getGuessCounter();
		String hiddenWordAfter = hangman.getHiddenWord();
		String incorrectLettersAfter = hangman.getIncorrectLetters();
		
		assertEquals("Guessing incorrectly should decrease the remaining number of guesses", 
				guessesBefore - 1, guessesAfter);
		assertTrue("Guessing inccorectly should not reveal any unknown characters", 
				hiddenWordAfter.equalsIgnoreCase(hiddenWordBefore));
		assertTrue("Guessing incorrectly should add the the guess to the list of guesses", 
				incorrectLettersAfter.equalsIgnoreCase(incorrectLettersBefore + guess));
	}
	
	@Test
	public void testCorrectGuess() throws FileNotFoundException {
		char guess = wordToGuess.charAt(0);
		outputStream.println(guess);
		outputStream.close();
		
		int guessesBefore = hangman.getGuessCounter();
		String hiddenWordBefore = hangman.getHiddenWord();
		String incorrectLettersBefore = hangman.getIncorrectLetters();
		
		runAutomatically();
		hangman.step();
		
		int guessesAfter = hangman.getGuessCounter();
		String hiddenWordAfter = hangman.getHiddenWord();
		String incorrectLettersAfter = hangman.getIncorrectLetters();
		
		assertEquals("Guessing correctly should not change the remaining number of guesses", 
				guessesBefore, guessesAfter);
		assertTrue("Guessing correctly should reveal the guessed character in the hidden word", 
				!(hiddenWordAfter.equalsIgnoreCase(hiddenWordBefore)));
		assertTrue("Guessing correctly should not add the the guess to the list of guesses", 
				incorrectLettersAfter.equalsIgnoreCase(incorrectLettersBefore));
	}
	
	@Test
	public void testLosing() throws FileNotFoundException {
		char guess = getAnIncorrectGuess();
		
		for(int i = 0; i < hangman.MAX_GUESSES; i++) {
			outputStream.println(guess);
		}
		
		outputStream.close();
		runAutomatically();
		
		for(int i = 0; i < hangman.MAX_GUESSES; i++) {
			hangman.step();
		}
		
		assertEquals("Should have 0 guesses remaining after losing", 0, hangman.getGuessCounter());
		assertTrue("Hidden word should not be revelaed after losing", 
				!(hangman.getHiddenWord().equalsIgnoreCase(wordToGuess)));
	}
	
	@Test
	public void testWinning() throws FileNotFoundException {
		for(int i = 0; i < wordToGuess.length(); i++) {
			outputStream.println(wordToGuess.charAt(i));
		}
		
		outputStream.close();
		runAutomatically();
		
		for(int i = 0; i < wordToGuess.length(); i++) {
			hangman.step();
		}
		
		assertTrue("Should have more than 0 guesses remaining after winning", hangman.getGuessCounter() > 0);
		assertTrue("Hidden word should be completely revealed after winning", 
				wordToGuess.equalsIgnoreCase(hangman.getHiddenWord()));
	}
}
