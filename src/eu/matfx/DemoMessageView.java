package eu.matfx;
	
import java.io.File;

import eu.matfx.tools.CSSContainer;
import eu.matfx.tools.LanguageStorage;
import eu.matfx.tools.NoValidIndexException;
import eu.matfx.tools.ResourceLoader;
import eu.matfx.view.MessageView;
import eu.matfx.view.listcell.DefaultMessageItemListCell;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class DemoMessageView extends Application 
{
	private DemoDataCreator demoDataCreator;
	
	private MessageView messageView;
	
	private ToggleButton testButton;
	
	private ComboBox<String> languageChange;
	
	private final String STANDARD = "standard";
	
	private final String CUSTOM = "custom";

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
					//example to change the rendering of the listView
					messageView.getListView().setCellFactory(c -> new DefaultMessageItemListCell());
					openView.setDisable(true);
					languageChange.setDisable(true);
					testButton.setDisable(false);
					messageView.showAndWait();
					openView.setDisable(false);
					languageChange.setDisable(false);
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
			
			//example for language change at the view
			languageChange = new ComboBox<String>();
			languageChange.getItems().add(STANDARD);
			languageChange.getItems().add(CUSTOM);
			languageChange.getSelectionModel().select(0);
			languageChange.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
			{

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
				{
					if(newValue != null)
					{
						if(newValue.equals(STANDARD))
						{
							LanguageStorage.resetLanguage();
						}
						else
						{
							try 
							{
								LanguageStorage.setLanguageAt(LanguageStorage.BUTTON_TEXT, "Mitteilung verwerfen.");
								LanguageStorage.setLanguageAt(LanguageStorage.SECOND_SHORTCUT, "Sek.");
								LanguageStorage.setLanguageAt(LanguageStorage.MESS_NOTIFICATION, "Benachrichtigung");
								LanguageStorage.setLanguageAt(LanguageStorage.MESS_WARNING, "Warnung");
								LanguageStorage.setLanguageAt(LanguageStorage.MESS_ALERT, "Alarm");
								LanguageStorage.setLanguageAt(LanguageStorage.MESS_ERROR, "Fehler");
								LanguageStorage.setLanguageAt(LanguageStorage.MESS_CUSTOM, "Individuell");
							} 
							catch (NoValidIndexException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					
				}
				
			});

			topBox.getChildren().add(languageChange);
			
			
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
