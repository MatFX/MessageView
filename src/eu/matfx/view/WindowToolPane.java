package eu.matfx.view;


import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * Drei Buttons für minimieren, maximieren und close
 * @author m.goerlich
 *
 */
public class WindowToolPane extends HBox
{
	
	private boolean isMaximized = true;
	
	/**
	 * Da muss beim switch das Icon nocht getauscht werden
	 */
	private Button maximize;
	

	/**
	 * save the last dimension after maximize
	 */
	private Rectangle2D savedWindowBounds = null;
	
	/**
	 * needed to close the stage with action event or to maximize the stage after calculation
	 */
	private Stage stageToExit;
	
	private Button close;
	
	
	public WindowToolPane(Stage stageToExit, boolean isPaddingUse, boolean isDialogMaximized)
	{
		this.stageToExit = stageToExit;
		this.isMaximized = isDialogMaximized;
		
		//TODO?
		if(isPaddingUse)
		{
			this.setPadding(new Insets(5,5,5,5));
		}
		
		
		this.setAlignment(Pos.BASELINE_RIGHT);
		this.setSpacing(5);
		
		this.getChildren().addAll(getMinimizeButton(), getMaximizeButton(), getCloseButton());
	}

	private Node getCloseButton() 
	{
		close = new Button("");
		close.setId("roundButton");
		GlyphsDude.setIcon(close, MaterialDesignIcon.WINDOW_CLOSE);
		close.setFocusTraversable(false);
		close.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				stageToExit.close();
				//remark: use the setClosingEvent(Eventhandler) method to define your own closing event.
			}
			
		});
		
		return close;
	}

	private Node getMaximizeButton() 
	{
		maximize = new Button("");
		maximize.setId("roundButton");
		GlyphsDude.setIcon(maximize, MaterialDesignIcon.WINDOW_MAXIMIZE);
		maximize.setFocusTraversable(false);
		maximize.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0)
			{
				toogleMaximizedForScreen();
			}
			
		});
		return maximize;
	}

	protected void toogleMaximizedForScreen()
	{
		
		double stageX = this.stageToExit.getX();
		if(stageX < 0)
		{
			stageX = 0;
		}
		double stageY = this.stageToExit.getY();
		if(stageY < 0)
		{
			stageY = 0;
		}
		
		final Screen screen = Screen.getScreensForRectangle(stageX, stageY, 1, 1).get(0);
		if(isMaximized)
		{
			isMaximized = false;
			GlyphsDude.setIcon(maximize, MaterialDesignIcon.WINDOW_MAXIMIZE);
			if(savedWindowBounds != null)
			{
				this.stageToExit.setX(savedWindowBounds.getMinX());
				this.stageToExit.setY(savedWindowBounds.getMinY());
				this.stageToExit.setWidth(savedWindowBounds.getWidth());
				this.stageToExit.setHeight(savedWindowBounds.getHeight());
			}
		}
		else
		{
			GlyphsDude.setIcon(maximize, MaterialDesignIcon.CONTENT_COPY);
			//Hier der Bereich für die Maximierung des Screens
			isMaximized = true;
			savedWindowBounds = new Rectangle2D(this.stageToExit.getX(), this.stageToExit.getY(), this.stageToExit.getWidth(), this.stageToExit.getHeight());
			this.stageToExit.setX(screen.getVisualBounds().getMinX());
			this.stageToExit.setY(screen.getVisualBounds().getMinY());
			this.stageToExit.setWidth(screen.getVisualBounds().getWidth());
			this.stageToExit.setHeight(screen.getVisualBounds().getHeight());
		}
	}

	private Node getMinimizeButton() 
	{
		Button minimize = new Button("");
		minimize.setId("roundButton");
		GlyphsDude.setIcon(minimize, MaterialDesignIcon.WINDOW_MINIMIZE);
		minimize.setFocusTraversable(false);
		minimize.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				stageToExit.setIconified(true);
			}
			
		});
		return minimize;
	}
	
	/**
	 * Define your own closing event from the dialog e.g. system.exit
	 * @param closingEvent
	 */
	public void setClosingEvent(EventHandler<ActionEvent> closingEvent)
	{
		close.setOnAction(closingEvent);
	}
	
	
	/**
	 * optional when you want define a key combination to close the dialog.
	 * @return
	 */
	public Button getCloseButtonForKeyListener()
	{
		return close;
	}
	

}
