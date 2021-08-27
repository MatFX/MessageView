package eu.matfx.message;


/**
 * base container to show the message on the view
 * @author m.goerlich
 *
 */
public class MessageItem 
{
	/**
	 * Headline will be shown in the listView
	 */
	private String headline;
	
	private String content;
	
	private MESSAGE_TYPE messageType;
	
	public MessageItem(String content, MESSAGE_TYPE messageType)
	{
		this.content = content;
		this.messageType = messageType;
		this.headline = messageType.name();
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
