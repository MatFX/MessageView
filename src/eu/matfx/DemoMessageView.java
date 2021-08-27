package eu.matfx;
	
import java.io.File;

import eu.matfx.tools.CSSContainer;
import eu.matfx.tools.LanguageStorage;
import eu.matfx.tools.NoValidIndexException;
import eu.matfx.tools.ResourceLoader;
import eu.matfx.view.MessageView;
import eu.matfx.view.listcell.DefaultMessageItemListCell;
import eu.matfx.view.listcell.IconMessageItemListCell;
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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class DemoMessageView extends Application 
{
	private DemoDataCreator demoDataCreator;
	
	private MessageView messageView;
	
	private ToggleButton testButton;
	
	private ComboBox<String> languageChange;
	
	private final String STANDARD = "standard";
	
	private final String CUSTOM = "custom";
	
	private ComboBox<String> listCellComboBox;
	
	private Button openView;

	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			primaryStage.setTitle("DemoMessageView");
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
			{

				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
					
				}
				
			});
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 300, 250);
			
			
			HBox topBox = new HBox();
			topBox.setSpacing(5);
			topBox.setPadding(new Insets(10,10,10,10));
			
			
			openView = new Button("open view");
			
			openView.setOnAction(new EventHandler<ActionEvent>() 
			{

				@Override
				public void handle(ActionEvent arg0) 
				{
					messageView = new MessageView();
					//example to change the rendering of the listView
					if(listCellComboBox.getSelectionModel().getSelectedItem().equals(CUSTOM))
					{
						messageView.getListView().setCellFactory(c -> new IconMessageItemListCell());
					}
					else
					{
						//Standard
						messageView.getListView().setCellFactory(c -> new DefaultMessageItemListCell());
					}
					
				
					setComponentDisable(true);

					testButton.setDisable(false);
					messageView.showAndWait();
					
					setComponentDisable(false);
					
					if(demoDataCreator != null && demoDataCreator.isRunning())
					{
						testButton.fire();
					}
					testButton.setDisable(true);
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
			
			GridPane gridPaneSetting = new GridPane();
			gridPaneSetting.setPadding(new Insets(15,15,15,15));
			gridPaneSetting.setHgap(5);
			
			gridPaneSetting.setVgap(5);
			
			
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
			gridPaneSetting.add(new Label("choice language:"), 0, 0);
			gridPaneSetting.add(languageChange, 1, 0);
			
			listCellComboBox = new ComboBox<String>();
			listCellComboBox.getItems().add(STANDARD);
			listCellComboBox.getItems().add(CUSTOM);
			listCellComboBox.getSelectionModel().select(0);
			
			gridPaneSetting.add(new Label("choice list cell:"), 0, 1);
			gridPaneSetting.add(listCellComboBox, 1, 1);
			
			
			

			
			root.setCenter(gridPaneSetting);
		
			try
			{
				ObservableList<CSSContainer> cssList = ResourceLoader.getGlobalCSSContainerList();
				CSSContainer cssContainer = null;
				if(cssList != null && cssList.size() > 0)
				{
					for(CSSContainer temp : cssList)
					{
						if(temp.getFilename().contains("technical"))
						{
							cssContainer =temp;
							break;
						}
						
					}
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
	
	public void setComponentDisable(boolean isDisable)
	{
		openView.setDisable(isDisable);
		languageChange.setDisable(isDisable);
		listCellComboBox.setDisable(isDisable);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
