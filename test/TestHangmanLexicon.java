import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestHangmanLexicon {
	private HangmanLexicon lexicon;

	@Before
	public void setUp() throws Exception {
		lexicon = new HangmanLexicon("ShorterLexicon.txt");
	}

	@Test
	public void testHangmanLexicon() {
		assertNotNull("Failed to initialize HangmanLexicon", lexicon);
	}

	@Test
	public void testGetWord() {
		String word = lexicon.getWord(0);
		assertEquals("Failed to correctly get first word in the list", "ABSTRACT", word);
		
		word = lexicon.getWord(1);
		assertEquals("Failed to correctly get second word in the list", "AMBASSADOR", word);
		
		word = lexicon.getWord(lexicon.getWordCount() - 1);
		assertEquals("Failed to correct get the last word in the list", "ZIRCON", word);
	}

	@Test
	public void testGetWordCount() {
		int wordCount = lexicon.getWordCount();
		assertEquals("Lexicon word count not equal to expected value", 73, wordCount);
	}

}
