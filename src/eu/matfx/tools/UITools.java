package eu.matfx.tools;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class UITools 
{
	
	public static Node createHorizontalSpacer() 
	{
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}
	
	public static Node createVerticalSpacer()
	{
		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	public static Cursor findCursorPosition(double startX, double startY, MouseEvent event) 
	{
		//Resize wird erstmal hier nicht geben...ist meiner Meinung nach groß genug
		//dannn wird das Pfeilkreuz angezeigt.
		if(event.getEventType() == MouseEvent.MOUSE_PRESSED)
			return Cursor.MOVE;
		
		return Cursor.DEFAULT;
	}
	

}
