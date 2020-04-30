package othellogui.pixelfont;

/**
 * CharacterNotFoundException is thrown when a character to be drawn is not available
 * 
 * @author omega237
 */
public class CharacterNotFoundException extends Exception
{

	/**
	 * Constructs the Exception with no specified error message
	 */
	public CharacterNotFoundException()
	{
		super();
	}

	/**
	 * Constructs the Exception with the specified detail message
	 * 
	 * @param message error message of this exception
	 */
	public CharacterNotFoundException(String message)
	{
		super(message);
	}

}
