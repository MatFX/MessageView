package eu.matfx.message;


/**
 * base container to show the message on the view
 * @author m.goerlich
 *
 */
public abstract class AMessageItem 
{
	private String content;
	
	private MESSAGE_TYPE messageType;
	
	protected AMessageItem(String content, MESSAGE_TYPE messageType)
	{
		this.content = content;
		this.messageType = messageType;
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
	
	
	public static AMessageItem createMessageItem(String content, MESSAGE_TYPE messageType)
	{
		switch(messageType)
		{
//			case CUSTOM:
//				return new CustomMessageItem(content);
			default:
				return new DefaultMessageItem(content, messageType);
				
		}
	}
	
}
