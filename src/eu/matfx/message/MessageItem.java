package eu.matfx.message;

import eu.matfx.tools.LanguageStorage;

/**
 * base container to show the message on the view
 * @author m.goerlich
 *
 */
public class MessageItem 
{
	/**
	 * Headline will be shown in the listView; its filled with individuell description or the name of the MESSAGE_TYPE
	 */
	private String headline;
	/**
	 * complete message 
	 */
	private String content;
	
	/**
	 * which message type e.g. ERROR, WARNING ....
	 */
	private MESSAGE_TYPE messageType;
	
	public MessageItem(String content, MESSAGE_TYPE messageType)
	{
		this.content = content;
		this.messageType = messageType;
		//default use the name of the message types
		this.headline = LanguageStorage.getLanguageFromMessageType(messageType);
	}
	
	public MessageItem(String headline, String content, MESSAGE_TYPE messageType)
	{
		this(content, messageType);
		this.headline = headline;
	}
	
	public String getContent() {
		return content;
	}

	public MESSAGE_TYPE getMessageType() {
		return messageType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMessageType(MESSAGE_TYPE messageType) {
		this.messageType = messageType;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	

}
