import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;

public class TestHangmanCanvas {
	private HangmanCanvas canvas;
	
	@Before
	public void setUp() {
		canvas = new HangmanCanvas();
	}
	
	@Test
	public void testHangmanCanvas() {
		assertNotNull(canvas);
		
		int numElements = canvas.getElementCount();
		assertEquals("New canvas should not have any elements", 0, numElements);
	}
	
	@Test
	public void testReset() {
		canvas.reset();
		
		int numElements = canvas.getElementCount();
		assertEquals("Resetting the canvas should result in 3 elements for the scaffold", 3, numElements);
		
		Iterator<GObject> iter = canvas.iterator();
		while(iter.hasNext()) {
			GObject element = iter.next();
			assertEquals("Scaffolding should consist entirely of lines", GLine.class, element.getClass());
		}
	}
	
	@Test
	public void testDisplayWord() {
		int numElementsBefore = canvas.getElementCount();
		canvas.displayWord("TEST");
		int numElementsAfter = canvas.getElementCount();
		assertEquals("Displaying a word the first time should increase element count by 1", numElementsBefore + 1, numElementsAfter);
		
		GObject element = canvas.getElement(canvas.getElementCount() - 1);
		assertEquals("Displaying a word should add a label to the top of the canvas", GLabel.class, element.getClass());
		
		canvas.displayWord("ANOTHER_TEST");
		assertEquals("Displaying a word after the first shouldn't change the element count", numElementsAfter + 1, canvas.getElementCount());

		element = canvas.getElement(canvas.getElementCount() - 1);
		assertEquals("Displaying a word after the first should add a label to the top of the canvas", GLabel.class, element.getClass());
	}
	
	@Test
	public void testNoteIncorrectGuess() {
		int numElementsBefore = canvas.getElementCount();
		canvas.noteIncorrectGuess("INCORRECT_GUESS");
		int numElementsAfter = canvas.getElementCount();
		assertEquals("Guessing incorrectly should add 1 element to the canvas", numElementsBefore + 2, numElementsAfter);
	}
}
