package eu.matfx.view;

import java.util.TimerTask;

import eu.matfx.listener.IMessageItemListener;
import eu.matfx.message.ExpirationMessageItem;

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
			this.iMessageListener.removeMessageItemListener(expirationMessageItem);
		}
	}

}
