package othellogui.pixelfont;

/**
 * PxfMalformedException is thrown if the pxf file loaded by PixelFont 
 * is malformed.
 * 
 * @author omega237
 *
 */
public class PxfMalformedException extends Exception
{
	/**
	 * Constructs the Exception with no specified error message
	 */
	public PxfMalformedException()
	{
		super();
	}

	/**
	 * Constructs the Exception with the specified detail message
	 * 
	 * @param message error message of this exception
	 */
	public PxfMalformedException(String message)
	{
		super(message);
	}
}
