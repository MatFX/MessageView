package eu.matfx.view.listcell;

import eu.matfx.message.ExpirationMessageItem;
import eu.matfx.message.MessageItem;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class DefaultMessageItemListCell extends ListCell<MessageItem>
{
	
	private final Color CUSTOM = Color.web("#6699ff");
	
	private final Color WARNING = Color.web("#e6e600");
	
	private final Color NOTIFICATION = Color.web("#33cc33");
	
	private final Color ALERT = Color.web("#ff8080");
	
	private final Color ERROR = Color.web("#ff0000");
	
	@Override
	protected void updateItem(MessageItem item, boolean empty)
	{
		super.updateItem(item, empty);
		
        
        if(item!=null)
       	{
        	
        	Circle sample = new Circle(0, 0, 4);
        	
        	switch(item.getMessageType())
        	{
        		case NOTIFICATION:
        			sample.setFill(NOTIFICATION);
        			break;
        		case WARNING:
        			sample.setFill(WARNING);
        			break;
        		case CUSTOM:
        			sample.setFill(CUSTOM);
        			break;
        		case ALERT:
        			sample.setFill(ALERT);
        			break;
        		case ERROR:
        			sample.setFill(ERROR);
        			break;
        	}
        	
        	setGraphic(sample);
        	
        	//creepy way to change the text
        	if(item instanceof ExpirationMessageItem)
        	{
        		((ExpirationMessageItem)item).setTextProperty(this.textProperty());
        	}
        	//normal way to change text
        	else
        		setText(item.getHeadline());
        	
       	}
        else
        {
        	setGraphic(null);
        	setText(null);
     	}
       
	}


}
