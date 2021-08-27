package eu.matfx.view.listcell;

import eu.matfx.message.ExpirationMessageItem;
import eu.matfx.message.MessageItem;
import eu.matfx.tools.ResourceLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconMessageItemListCell extends ListCell<MessageItem>
{
	private final Image CUSTOM = ResourceLoader.getImageFromIconFolder("custom_0x99ccccff");
	
	private final Image WARNING = ResourceLoader.getImageFromIconFolder("warning_0xe6e64dff");
	
	private final Image NOTIFICATION =  ResourceLoader.getImageFromIconFolder("information_0x0b539aff");
	
	private final Image ALERT = ResourceLoader.getImageFromIconFolder("alert_0xff0000ff");
	
	private final Image ERROR = ResourceLoader.getImageFromIconFolder("warning_0xe64d4dff");
	
	@Override
	protected void updateItem(MessageItem item, boolean empty)
	{
		super.updateItem(item, empty);
		
        
        if(item!=null)
       	{
        	
        	ImageView imageView = new ImageView();
        	switch(item.getMessageType())
        	{
        		case NOTIFICATION:
        			imageView.setImage(NOTIFICATION);
        			break;
        		case WARNING:
        			imageView.setImage(WARNING);
        			break;
        		case CUSTOM:
        			imageView.setImage(CUSTOM);
        			break;
        		case ALERT:
        			imageView.setImage(ALERT);
        			break;
        		case ERROR:
        			imageView.setImage(ERROR);
        			break;
        	}
        	imageView.setFitHeight(16);
        	imageView.setFitWidth(16);
        	
        	setGraphic(imageView);
        	
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
