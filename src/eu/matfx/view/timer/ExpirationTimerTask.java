package eu.matfx.view.timer;

import java.util.TimerTask;

import eu.matfx.listener.IMessageItemListener;
import eu.matfx.message.ExpirationMessageItem;

/**
 * Every ExpirationMessageItem need a TimerTask to remove the element from the ListView after expiration time is reached.
 * @author m.goerlich
 *
 */
public class ExpirationTimerTask extends TimerTask 
{
	
	private IMessageItemListener iMessageListener;

	private ExpirationMessageItem expirationMessageItem;
	
	public ExpirationTimerTask(IMessageItemListener iMessageListener, ExpirationMessageItem expirationMessageItem) 
	{
		this.iMessageListener = iMessageListener;
		this.expirationMessageItem = expirationMessageItem;
		
	}

	@Override
	public void run() 
	{
		synchronized(this.iMessageListener)
		{
			expirationMessageItem.stopTimeLine();
			this.iMessageListener.removeMessageItemListener(expirationMessageItem);
		}
	}

}
