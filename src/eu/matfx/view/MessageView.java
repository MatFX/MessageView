package eu.matfx.view;


import java.io.File;
import java.util.Timer;

import eu.matfx.listener.IMessageItemListener;
import eu.matfx.message.ExpirationMessageItem;
import eu.matfx.message.MessageItem;
import eu.matfx.tools.CSSContainer;
import eu.matfx.tools.ResourceLoader;
import eu.matfx.tools.UITools;
import eu.matfx.view.timer.TimerScheduler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * ListView with short information of the incomed message.
 * <br>right side content of the selected ListView element or the last incomed message
 * <br>top of border pane close dialog
 * <br>bottom of border pane filter?
 * @author m.goerlich
 *
 */
public class MessageView extends BorderPane implements IMessageItemListener
{
	private Stage stage;
	
	private double MIN_WIDTH_VALUE = 400D;
	
	private double MIN_HEIGHT_VALUE = 400D;
	
	/**
	 * Save und Cancel wird es geben um aus der Stage zu kommen
	 */
	private ButtonType buttonType = ButtonType.CANCEL;
	
	private double startX, startY;
	
	private WindowToolPane toolPane;
	
	private ListView<MessageItem> listViewMessageItem;
	
	private TextArea textArea;
	
	private Button closeMessage;
	
	/**
	 * store the location of the mouse
	 */
	private boolean mousePointerIsInScene = false;
	
	public MessageView()
	{
		
		stage = new Stage();
		//modal damit der Anwender nicht zwischendurch was anderes aufruft.
		//TODO
		//stage.initModality(Modality.APPLICATION_MODAL);
		
		HBox topHBox = new HBox();
		toolPane = new WindowToolPane(stage, true, false);
		//anderes closing event für den Button wegen der Abfrage nach Speicherung
		
		topHBox.getChildren().addAll(UITools.createHorizontalSpacer(), toolPane);
		this.setTop(topHBox);
		
		
		GridPane gridPane = new GridPane();
		gridPane.setGridLinesVisible(true);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		//two column left smaller than right with the content
		
		//TODO ?
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(40);
		
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(60);
		gridPane.getColumnConstraints().addAll(column1, column2);
		
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(90);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(10);
	    gridPane.getRowConstraints().addAll(row1, row2);
		
		
		listViewMessageItem = new ListView<MessageItem>();
		listViewMessageItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MessageItem>() 
		{

			@Override
			public void changed(ObservableValue<? extends MessageItem> arg0, MessageItem arg1, MessageItem arg2) 
			{
				if(arg2 != null)
				{
					textArea.setText(arg2.getContent());
					//und freischalten, evtl. war diese gesperrt
					closeMessage.setDisable(false);
				}
				
			}
			
		});
		
		
		
		gridPane.add(listViewMessageItem, 0, 0, 1, 2);
		
		textArea = new TextArea();
		textArea.setWrapText(true);
		textArea.setEditable(false);
		HBox.setHgrow(textArea, Priority.ALWAYS);
		
		gridPane.add(textArea, 1, 0, 1, 1);
		
		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(3,3,3,3));
		
		
		closeMessage = new Button("close message");
		closeMessage.setDisable(true);
		closeMessage.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent arg0) {
				listViewMessageItem.getItems().remove(listViewMessageItem.getSelectionModel().getSelectedItem());
				
			}
			
		});
		
		buttonBox.getChildren().addAll(UITools.createHorizontalSpacer(), closeMessage, UITools.createHorizontalSpacer());
		
		gridPane.add(buttonBox, 1, 1, 1, 1);
		
		this.setCenter(gridPane);
		
		//listener für die Maus
		this.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				startX = event.getSceneX();
				startY = event.getSceneY();
				//herausfinden ob move oder oder resize erforderlich ist
				Cursor value = UITools.findCursorPosition(startX, startY, event);
				stage.getScene().setCursor(value);
			}
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) 
			{
				
				
				if(stage.getScene().getCursor() == Cursor.MOVE)
				{
					 stage.getScene().getWindow().setX( event.getScreenX() - startX );
					 stage.getScene().getWindow().setY( event.getScreenY() - startY);
				}
				else 
				{
					//handleResize(event);
				}
			}
			
		});
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mousePointerIsInScene = true;
				
			}
			
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mousePointerIsInScene = false;
				
				//TODO ? wenn er draußen ist, dann ist der zuletzt erhaltene darzustellen
				
				
			}
			
		});
		
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event)
			{
				//Hier die Prüfung ob der Cursor verändert werden muss bezieht sich auf das resize
				Cursor value = UITools.findCursorPosition(event.getSceneX(), event.getSceneY(), event);
				//Alles anzeigen außer move, weil das nervt ansonsten nur auf der Oberfläche
				//nur s, w und sued-west für den resize die andere Seite flackert und ich weiß nicht wieso
				if(value != Cursor.MOVE)
				{
					//setNewInitialEventCoordinates(event);
					stage.getScene().setCursor(value);
				}
				
					
			}
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				stage.getScene().setCursor(Cursor.DEFAULT);
			}
			
		});
		
		
		TimerScheduler.addMessageItemListener(this);
		
		
	}

	public ButtonType showAndWait()
	{
		Scene scene = new Scene(this);
		this.setId("entry_scene");
		scene.getRoot().setStyle("");
		
		try
		{
			ObservableList<CSSContainer> cssList = ResourceLoader.getGlobalCSSContainerList();
			CSSContainer cssContainer = null;
			if(cssList != null && cssList.size() > 0)
				cssContainer = cssList.get(0);
			
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
		stage.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
	
		stage.setWidth(MIN_WIDTH_VALUE);
		stage.setHeight(MIN_HEIGHT_VALUE);
		
		//ab nun wird gewartet bis Anwender mit einem Button Klick etwas bestätigt
		stage.showAndWait();
		
		return buttonType;
	}
	
	/**
	 * to override from outside the cellFactory
	 * @return
	 */
	public ListView<MessageItem> getListView()
	{
		return listViewMessageItem;
	}
	
	/**
	 * to change text from outside
	 * @return
	 */
	public Button getCloseMessageButton()
	{
		return this.closeMessage;
	}
	
	

	/**
	 * The processing of the param MessageItem is in a runnable jfx application thread embedded.
	 */
	@Override
	public void setMessageItem(MessageItem aMessageItem) 
	{
		
		Platform.runLater(new Runnable() 
		{

			@Override
			public void run() {
				listViewMessageItem.getItems().add(aMessageItem);
				
				//change the selections state only, when the mouse pointer outside the dialog
				
				if(!mousePointerIsInScene)
				{
					changeRightSideView(aMessageItem);
				}
			
				if(aMessageItem instanceof ExpirationMessageItem)
				{
					TimerScheduler.addTimerTask((ExpirationMessageItem)aMessageItem);
					((ExpirationMessageItem)aMessageItem).startTimeLine();
				}
				
			}
			
		});
	}

	/**
	 * refresh the right side with the param MessageItem
	 * @param aMessageItem
	 */
	protected void changeRightSideView(MessageItem aMessageItem) 
	{
		listViewMessageItem.getSelectionModel().select(aMessageItem);
		textArea.setText(aMessageItem.getContent());
		closeMessage.setDisable(false);
	}

	@Override
	public void removeMessageItemListener(MessageItem messageItem)
	{
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				//if selected, clear the view
				if(listViewMessageItem.getSelectionModel().getSelectedItem().equals(messageItem))
				{
					textArea.setText("");
					closeMessage.setDisable(true);
				}
				listViewMessageItem.getItems().remove(messageItem);
			}
			

		});
		
	}

}
