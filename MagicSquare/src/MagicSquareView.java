import java.awt.GridLayout;

import javax.swing.*;

/**
 * Create a new MagicSquare GUI.
 * @author ashtonmohns
 *
 */
public class MagicSquareView extends JFrame implements MagicSquareModelListener{
	
	private MagicSquareModel model;
	private MagicSquareButton buttons[][];
	
	/**
	 * Create the View, instantiate all buttons.
	 * @param size the size of the game.
	 */
	public MagicSquareView(int size) {
		super("Magic Square");
		this.model = new MagicSquareModel(size);
		
		this.setSize(100 * model.getSize(), 100 * model.getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.model.addListener(this);
		
		JMenuItem resetButton = new JMenuItem("Reset");
		resetButton.addActionListener(e -> {
			model.reset();
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(resetButton);
		
		this.setJMenuBar(menuBar);
		
		JPanel panel = new JPanel(new GridLayout(model.getSize(), model.getSize()));
		
		buttons = new MagicSquareButton[model.getSize()][model.getSize()];
		for(int x = 0; x < model.getSize(); x++) {
			for(int y = 0; y < model.getSize(); y++) {
				MagicSquareButton b = new MagicSquareButton(x, y);
				//Create the controller for the game.
				b.addActionListener(e -> {
					MagicSquareButton buttonPressed = (MagicSquareButton) e.getSource();
					if(!model.hasValue(buttonPressed.getColumn(), buttonPressed.getRow())) {
						try {
							int value = Integer.parseInt(JOptionPane.showInputDialog("Enter a value you'd like to place here."));
							model.setValue(buttonPressed.getColumn(), buttonPressed.getRow(), value);
						}
						catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Invalid input value.");
						}
					}
				});
				buttons[x][y] = b;
				panel.add(b);
			}
		}
		this.add(panel);
		this.setVisible(true);
	}
	
	/**
	 * Update the view when a value is placed.
	 */
	@Override
	public void HandleMagicSquareEvent(MagicSquareEvent e) {
		//I could have added more methods to the interface, but did not have time, 
		//so I have used negative values as the errors instead of implementing more methods.
		//If I had more time I would have fixed this, this is a temporary solution.
		if(e.getValue() == -1) JOptionPane.showMessageDialog(null, "Invalid input value.");
		else if(e.getValue() == -2) JOptionPane.showMessageDialog(null, "Already used this number.");
		else if(e.getValue() == -2) JOptionPane.showMessageDialog(null, "Value already placed in this position.");
		else buttons[e.getX()][e.getY()].setText(Integer.toString(e.getValue()));
	}
	
	/**
	 * Update the view when the game is complete.
	 */
	@Override
	public void HandleGameComplete(boolean won) {
		for(int x = 0; x < model.getSize(); x++) {
			for(int y = 0; y < model.getSize(); y++) {
				buttons[x][y].setEnabled(false);
			}
		}
		if(won) JOptionPane.showMessageDialog(null, "Congratulations, you have won.");
		else JOptionPane.showMessageDialog(null, "You have lost.");
	}
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		MagicSquareView m = new MagicSquareView(3);
	}

	@Override
	public void HandleReset() {
		for(int x = 0; x < model.getSize(); x++) {
			for(int y = 0; y < model.getSize(); y++) {
				buttons[x][y].setEnabled(true);
				buttons[x][y].setText("");
			}
		}
	}

}
