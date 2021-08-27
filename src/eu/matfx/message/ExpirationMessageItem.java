package eu.matfx.message;

/**
 * Extended message item with the delay to the expiration time.
 * <br>Expiration time = currentTimeInMillisAfterFirstRepresentation + delayInMs;
 * @author m.goerlich
 *
 */
public class ExpirationMessageItem extends MessageItem
{
	private long delayToExpirationInMS;

	public ExpirationMessageItem(String content, MESSAGE_TYPE messageType, long delayToExpirationInMS) 
	{
		super(content, messageType);
		this.delayToExpirationInMS = delayToExpirationInMS;
		
	}
	
	public ExpirationMessageItem(String headline, String content, MESSAGE_TYPE messageType, long delayToExpirationInMS)
	{
		this(content, messageType, delayToExpirationInMS);
		this.setHeadline(headline);
	}
	
	public long getDelayToExpirationInMS() {
		return delayToExpirationInMS;
	}

	public void setDelayToExpirationInMS(long delayToExpirationInMS) {
		this.delayToExpirationInMS = delayToExpirationInMS;
	}

}
