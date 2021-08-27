package eu.matfx.message;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Extended message item with the delay to the expiration time.
 * <br>Expiration time = currentTimeInMillisAfterFirstRepresentation + delayInMs;
 * @author m.goerlich
 *
 */
public class ExpirationMessageItem extends MessageItem
{
	private long delayToExpirationInMS;
	
	private StringProperty textProperty;
	
	private Timeline timeline;

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

	/**
	 * creepy method but I found no better way
	 * @param textProperty
	 */
	public void setTextProperty(StringProperty textProperty) 
	{
		this.textProperty  = textProperty;
		//creepy
		textProperty.set(getTextToSet(this.getHeadline(), (int) (delayToExpirationInMS / 1000)));
	}
	
	public void startTimeLine()
	{
		timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                  new EventHandler<ActionEvent>() {
                    
					@Override
					public void handle(ActionEvent event) {
						delayToExpirationInMS = delayToExpirationInMS - 1000;
	                       if(textProperty != null)
	                    	   textProperty.set(getTextToSet(getHeadline(), (int) (delayToExpirationInMS / 1000)));
	                    	
						
					}
                }));
        timeline.play();
	}

	public void stopTimeLine() 
	{
		//stop time line from outside wenn the timer task executed
		timeline.stop();
	}
	
	
	/**
	 * Build the headline for the ListView with current second
	 * @param headline
	 * @param second
	 * @return
	 */
	private String getTextToSet(String headline, int second)
	{
		StringBuilder sb = new StringBuilder(headline);
		sb.append(" ");
		sb.append(second);
		sb.append(" sec.");
		return sb.toString();
	}
}
