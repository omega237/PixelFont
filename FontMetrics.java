package othellogui.pixelfont;

public interface FontMetrics
{

	/**
	 * Calculates the width of a string.
	 * 
	 * @param s	string whose width is to be returned
	 * @return	the width of s
	 */
	public abstract int getWidth(String s);

	/**
	 * Returns a char's width
	 * 
	 * @return width of a char
	 */
	public abstract int getWidth();

	/**
	 * Returns a char's height
	 * 
	 * @return height of a char
	 */
	public abstract int getHeight();

	/**
	 * Returns the charspacing
	 * 
	 * @return  charspacing between chars
	 */
	public abstract int getCharspacing();

	/**
	 * Sets the charspacing
	 * 
	 * @param charspacing the charspacing to set
	 */
	public abstract void setCharspacing(int charspacing);

}