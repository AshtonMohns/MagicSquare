import javax.swing.JButton;

/**
 * A button with an additional x and y value corresponding to its position.
 * @author ashtonmohns
 *
 */
public class MagicSquareButton extends JButton{
	private int column, row;
	
	/**
	 * Create a new MagicSquareButton
	 * @param x the x position of the button.
	 * @param y the y position of the button.
	 */
	public MagicSquareButton(int x, int y) {
		super();
		this.column = x;
		this.row = y;
	}

	/**
	 * 
	 * @return the x position of the button
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * 
	 * @return the y position of the button.
	 */
	public int getRow() {
		return row;
	}	
}
