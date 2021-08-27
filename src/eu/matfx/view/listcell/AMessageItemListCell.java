package eu.matfx.view.listcell;

import eu.matfx.message.AMessageItem;
import eu.matfx.message.DefaultMessageItem;
import javafx.scene.control.ListCell;

public abstract class AMessageItemListCell<T extends AMessageItem> extends ListCell<T> 
{
	
	
	@Override
	protected void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
	}


}
