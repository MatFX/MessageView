package eu.matfx;
	
import java.io.File;

import eu.matfx.tools.CSSContainer;
import eu.matfx.tools.ResourceLoader;
import eu.matfx.view.MessageView;
import eu.matfx.view.listcell.DefaultMessageItemListCell;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class DemoMessageView extends Application 
{
	private DemoDataCreator demoDataCreator;
	
	private MessageView messageView;
	
	private ToggleButton testButton;

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			
			HBox topBox = new HBox();
			topBox.setSpacing(5);
			topBox.setPadding(new Insets(10,10,10,10));
			
			
			Button openView = new Button("open view");
			
			openView.setOnAction(new EventHandler<ActionEvent>() 
			{

				@Override
				public void handle(ActionEvent arg0) 
				{
					messageView = new MessageView();
					//messageView.setListCellFactory(new DefaultMessageItemListCell());
					openView.setDisable(true);
					testButton.setDisable(false);
					messageView.showAndWait();
					openView.setDisable(false);
					if(demoDataCreator != null && demoDataCreator.isRunning())
					{
						testButton.fire();
						testButton.setDisable(true);
					}
					
				}
				
			});
			topBox.getChildren().add(openView);
			
			
			testButton = new ToggleButton("start test");
			testButton.setDisable(true);
			testButton.setOnAction(new EventHandler<ActionEvent>() 
			{

				@Override
				public void handle(ActionEvent arg0) 
				{
					
					
					if(testButton.isSelected())
					{
						testButton.setText("stop test");
						if(messageView != null)
						{
							demoDataCreator = new DemoDataCreator(messageView);
							
						}
					}
					else
					{
						testButton.setText("start test");
						if(demoDataCreator != null && demoDataCreator.isRunning())
							demoDataCreator.stopThread();
					}
					
					
				}
				
			});
			
			
			topBox.getChildren().add(testButton);
			root.setTop(topBox);
			try
			{
				ObservableList<CSSContainer> cssList = ResourceLoader.getGlobalCSSContainerList();
				CSSContainer cssContainer = null;
				if(cssList != null && cssList.size() > 0)
				{
					
					
					cssContainer = cssList.get(0);
				}
				
				if(cssContainer != null)
				{
				
					String value = cssContainer.getResourceFolder() + cssContainer.getFilename();
					File file = new File(value);
					scene.getStylesheets().add(file.toURI().toURL().toString());
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
