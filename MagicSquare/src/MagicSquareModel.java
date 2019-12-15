import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

/**
 * The background model for the MagicSquare game.
 * @author ashtonmohns
 *
 */
public class MagicSquareModel {
	private final int GAMESIZE;
	//Decided against making it final because two games can play with two different sizes.
	private int[][] values;
	private int movesMade;
	private ArrayList<MagicSquareModelListener> listeners;
	private HashSet<Integer> numbersUsed;
	
	/**
	 * Create a new MagicSquareModel.
	 * @param size the size of the model (must be greater than or equal to 3.
	 */
	public MagicSquareModel(int size) {
		while(size < 3) {
			size = Integer.parseInt(JOptionPane.showInputDialog("Please choose a game size that is 3 or greater."));
		}
		
		GAMESIZE = size;
		values = new int [size][size];
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				values[i][j] = -1;
			}
		}
		movesMade = 0;
		listeners = new ArrayList<>();
		numbersUsed = new HashSet<>();
	}
	
	/**
	 * 
	 * @return the size of the game.
	 */
	public int getSize() {
		return this.GAMESIZE;
	}
	
	/**
	 * Add a listener who will be updated whenever a change occurs in the game.
	 * @param m the listener.
	 */
	public void addListener(MagicSquareModelListener m) {
		listeners.add(m);
	}
	
	/**
	 * Checks if the given square has a value already assigned.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasValue(int x, int y) {
		if(x > GAMESIZE || x < 0 || y < 0 || y > GAMESIZE) return true;
		if(values[x][y] != -1) return true;
		return false;
	}
	
	/**
	 * Set the value of a button.
	 * @param x the position
	 * @param y the position
	 * @param value the value being assigned.
	 */
	public void setValue(int x, int y, int value) {
		if(x > GAMESIZE || x < 0 || y < 0 || y > GAMESIZE || value < 1 || value > (GAMESIZE * GAMESIZE)) {
			updateListeners(new MagicSquareEvent(-1, -1, -1));
		}
		else if(numbersUsed.contains(value)) {
			updateListeners(new MagicSquareEvent(-2, -2, -2));
		}
		else if(hasValue(x, y)) {
			updateListeners(new MagicSquareEvent(-3, -3, -3));
		}
		else {
			values[x][y] = value;
			movesMade += 1;
			updateListeners(new MagicSquareEvent(x, y, value));
			numbersUsed.add(value);
			if(movesMade == GAMESIZE * GAMESIZE) checkIfSolved();
		}
	}
	
	/**
	 * checks if the game has been solved.
	 */
	private void checkIfSolved() {
		if(movesMade < (GAMESIZE * GAMESIZE)) return;
		
		boolean solved = true;
		int firstSum = 0;
		
		for(int i = 0; i < GAMESIZE && solved; i++) {
			int sum = 0;
			for(int j = 0; j < GAMESIZE; j++) {
				sum += values[i][j];
			}
			if(firstSum == 0) firstSum = sum;
			else if(firstSum != sum) solved = false;
		}
		
		for(int j = 0; j < GAMESIZE && solved; j++) {
			int sum = 0;
			for(int i = 0; i < GAMESIZE; i++) {
				sum += values[i][j];
			}
			if(firstSum != sum) solved = false;
		}
		
		updateListenersGameComplete(solved);
	}
	
	public void reset() {
		for(int i = 0; i < GAMESIZE; i++) {
			for(int j = 0; j < GAMESIZE; j++) {
				values[i][j] = -1;
			}
		}
		movesMade = 0;
		numbersUsed = new HashSet<>();
		updateListenersReset();
	}
	
	/**
	 * Tells listeners the game has been reset.
	 */
	private void updateListenersReset() {
		for(MagicSquareModelListener listener : listeners) {
			listener.HandleReset();
		}
	}

	/**
	 * Updates all listeners
	 * @param event the event that has just occurred.
	 */
	private void updateListeners(MagicSquareEvent event) {
		for(MagicSquareModelListener listener : listeners) {
			listener.HandleMagicSquareEvent(event);
		}
	}
	
	/**
	 * Tell listeners if the game is won or lost.
	 * @param won
	 */
	private void updateListenersGameComplete(boolean won) {
		for(MagicSquareModelListener listener : listeners) {
			listener.HandleGameComplete(won);
		}
	}
}
