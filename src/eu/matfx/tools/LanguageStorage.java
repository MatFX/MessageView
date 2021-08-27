package eu.matfx.tools;

import eu.matfx.message.MESSAGE_TYPE;

/**
 * simple language storage to use a other language
 * @author m.goerlich
 *
 */
public class LanguageStorage 
{
	
	public static final int	BUTTON_TEXT = 0;
		
	public static final int	SECOND_SHORTCUT = 1;
		
	public static final int MESS_NOTIFICATION = 2;
		
	public static final int	MESS_WARNING = 3;
		
	public static final int	MESS_ALERT = 4;
		
	public static final int	MESS_ERROR = 5;
		
	public static final int	MESS_CUSTOM = 6;
	
	private static String[] simpleLanguageStorage = new String[] {
			"close message", 
			"sec.", 
			MESSAGE_TYPE.NOTIFICATION.name(),
			MESSAGE_TYPE.WARNING.name(),
			MESSAGE_TYPE.ALERT.name(),
			MESSAGE_TYPE.ERROR.name(),
			MESSAGE_TYPE.CUSTOM.name(),
			};
	
	
	public static String getLanguage(final int INDEX)
	{
		try
		{
			return simpleLanguageStorage[INDEX];
		}
		catch(Exception e)
		{
			return "n/a";
		}
	}
	
	public static void setLanguageAt(final int INDEX, final String stringToSet) throws NoValidIndexException
	{
		if(INDEX >= BUTTON_TEXT && INDEX <= simpleLanguageStorage.length-1)
		{
			simpleLanguageStorage[INDEX] = stringToSet;
		}
		else
			throw new NoValidIndexException();
	}

	public static String getLanguageFromMessageType(MESSAGE_TYPE messageType) 
	{
		switch(messageType)
		{
			case NOTIFICATION:
				return getLanguage(MESS_NOTIFICATION);
			case WARNING:
				return getLanguage(MESS_WARNING);
			case ALERT:
				return getLanguage(MESS_ALERT);
			case ERROR:
				return getLanguage(MESS_ERROR);
			case CUSTOM:
				return getLanguage(MESS_CUSTOM);
		}
		return getLanguage(4711);
	}

}
