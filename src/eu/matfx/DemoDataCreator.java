package eu.matfx;

import java.util.concurrent.TimeUnit;

import eu.matfx.listener.IAMessageItemListener;
import eu.matfx.message.AMessageItem;
import eu.matfx.message.MESSAGE_TYPE;
import javafx.application.Platform;

public class DemoDataCreator extends Thread
{
	private boolean runner = false;
	
	private int messageCounter = 0;
	
	private IAMessageItemListener iAMessageListener;
	
	
	public DemoDataCreator(IAMessageItemListener iAMessageListener)
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
				
				AMessageItem customMessage = AMessageItem.createMessageItem("Mitteilung Nummer: " + messageCounter, MESSAGE_TYPE.CUSTOM);
				
				Platform.runLater(new Runnable() 
				{

					@Override
					public void run() {
						iAMessageListener.setAMessageItem(customMessage);
						
					}

				});
				
				
				int randomValueSleep =  (int)(Math.random() * 3000 + 1);
				
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
