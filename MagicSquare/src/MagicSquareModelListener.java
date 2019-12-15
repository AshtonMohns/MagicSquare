/**
 * 
 * @author ashtonmohns
 *
 */
public interface MagicSquareModelListener {
	public abstract void HandleMagicSquareEvent(MagicSquareEvent e);
	public abstract void HandleGameComplete(boolean won);
	public abstract void HandleReset();
}
