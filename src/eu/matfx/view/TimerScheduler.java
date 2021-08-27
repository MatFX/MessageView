package eu.matfx.view;

import java.util.Timer;

import eu.matfx.listener.IMessageItemListener;
import eu.matfx.message.ExpirationMessageItem;

public class TimerScheduler
{
	private static TimerScheduler instance = new TimerScheduler();
	
	private Timer timer;
	
	private IMessageItemListener iMessageListener;
	
	private TimerScheduler()
	{
		timer = new Timer();
	}
	
	public static void addMessageItemListener(IMessageItemListener iMessageListener)
	{
		instance.iMessageListener = iMessageListener;
	}
	
	public static void addTimerTask(ExpirationMessageItem expirationMessageItem)
	{
		instance.timer.schedule(new ExpirationTimerTask(instance.iMessageListener, expirationMessageItem), expirationMessageItem.getDelayToExpirationInMS());
	}

}
