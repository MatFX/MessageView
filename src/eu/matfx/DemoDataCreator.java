package eu.matfx;

import java.util.concurrent.TimeUnit;

import eu.matfx.listener.IMessageItemListener;
import eu.matfx.message.MessageItem;
import eu.matfx.message.ExpirationMessageItem;
import eu.matfx.message.MESSAGE_TYPE;
import javafx.application.Platform;

public class DemoDataCreator extends Thread
{
	private boolean runner = false;
	
	private int messageCounter = 0;
	
	private IMessageItemListener iAMessageListener;
	
	
	public DemoDataCreator(IMessageItemListener iAMessageListener)
	{
		this.iAMessageListener = iAMessageListener;
		this.runner = true;
		this.start();
	}
	
	public void run()
	{
		while(runner)
		{
			
			try 
			{
				messageCounter++;
				
				int indexMessageType = (int) (Math.random() * MESSAGE_TYPE.values().length);
				MessageItem customMessage = null;
				//notification and warning with expiration message
				if(MESSAGE_TYPE.values()[indexMessageType] == MESSAGE_TYPE.NOTIFICATION
						|| MESSAGE_TYPE.values()[indexMessageType] == MESSAGE_TYPE.WARNING)
				{
					long delayInMS = (long) ((Math.random() * 15000) + 5000);
					customMessage = new ExpirationMessageItem("Notification number: " + messageCounter, MESSAGE_TYPE.values()[indexMessageType], delayInMS);
				}
				else
				{
					//normal way
					customMessage = new MessageItem("Notification number: " + messageCounter, MESSAGE_TYPE.values()[indexMessageType]);
				}
		
				iAMessageListener.setMessageItem(customMessage);
				
								
				int randomValueSleep =  (int)(Math.random() * 3000 + 1000);
				
				if(runner)
					TimeUnit.MILLISECONDS.sleep(randomValueSleep);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
	}

	public boolean isRunning() {
		return runner;
	}

	public void stopThread() {
		runner = false;
		
	}
	

}
