/**
 * 
 */
package othellogui.pixelfont;

/**
 * BoundingRectTooSmallException is thrown when the bounding rect is too small for the text to fit in
 * 
 * @author omega237
 */
public class BoundingRectTooSmallException extends Exception
{

	/**
	 * Constructs the Exception with no specified error message
	 */
	public BoundingRectTooSmallException()
	{
		super();
	}

	/**
	 * Constructs the Exception with the specified detail message
	 * 
	 * @param message error message of this exception
	 */
	public BoundingRectTooSmallException(String message)
	{
		super(message);
	}

}
