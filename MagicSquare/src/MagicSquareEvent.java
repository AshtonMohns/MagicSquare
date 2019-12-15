/**
 * Event that occurs when a value is updated in the model.
 * @author ashtonmohns
 *
 */
public class MagicSquareEvent {
	private int x, y, value;
	
	/**
	 * Create a new event
	 * @param x position of the change
	 * @param y position of the change
	 * @param value of the change
	 */
	public MagicSquareEvent(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	/**
	 * Getter method
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter method
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Getter method
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
