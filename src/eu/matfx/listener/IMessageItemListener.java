package eu.matfx.listener;

import eu.matfx.message.MessageItem;

/**
 * Interface to give a MessageItem from outside to the MessageView.
 * <br>At the MessageView the logic of the method is in Platform.RunLater embedded.
 * @author m.goerlich
 *
 */
public interface IMessageItemListener 
{
	public void setMessageItem(MessageItem aMessageItem);
	
	

}
