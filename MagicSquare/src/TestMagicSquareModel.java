import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for 3110 Exam.
 * Implements Listener such that it can know when specific methods would be called.
 * @author ashtonmohns
 *
 */
public class TestMagicSquareModel implements MagicSquareModelListener{
	private MagicSquareModel model;
	private int[] values;
	private boolean gameWon;
	private boolean reset;
	private int countMoves;
	
	@Before
	public void setUp() throws Exception {
		model = new MagicSquareModel(3);
		values = new int[] {2, 7, 6, 9, 5, 1, 4, 3, 8};
		gameWon = false;
		reset = false;
		countMoves = 0;
		model.addListener(this);
	}

	@Test
	public void testAddingSequentially() {
		for(int i = 0; i < model.getSize(); i++) {
			for(int j = 0; j < model.getSize(); j++) {
				model.setValue(j, i, values[3 * i + j]);
			}
		}
		assertTrue(gameWon);
	}
	
	@Test
	public void testInitiallyEmpty() {
		for(int i = 0; i < model.getSize(); i++) {
			for(int j = 0; j < model.getSize(); j++) {
				assertFalse(model.hasValue(i, j));
			}
		}
	}
	
	@Test
	public void testAddingIncorrectValue() {
		model.setValue(0, 0, 10);
		assertTrue(!model.hasValue(0, 0));
		assertTrue(countMoves == 0);
	}

	@Test
	public void testReset() {
		model.setValue(1, 1, 1);
		model.reset();
		assertTrue(reset);
		assertTrue(!model.hasValue(1, 1));
	}
	
	@Override
	public void HandleMagicSquareEvent(MagicSquareEvent e) {
		if(e.getValue() > 0) countMoves++;
		//This will mean that it has not sent an error message.
		//I could have added more methods to the interface, but did not have time, 
		//so I have used negative values as the errors.
	}

	@Override
	public void HandleGameComplete(boolean won) {
		gameWon = won;
	}

	@Override
	public void HandleReset() {
		reset = true;
	}

}
