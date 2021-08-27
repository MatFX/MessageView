package eu.matfx.tools;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResourceLoader 
{
	
	private static final String GLOBAL_RESOURCE_PATH = "/resources/global/";
	
	
	/*
	 * Wer sich die stylsheet Laderei bei fx ausgedacht hat, gehört auch eine Watschn.
	 */
	private static final String RESOURCE_DESTINATION = "/resources/";
	
	
	/**
	 * globale Resourcen für das Grundlayout
	 */
	private static final String GLOBAL_CSS_RESOURCE_DESTINATION = GLOBAL_RESOURCE_PATH + "css/";
	
	public static URL getGlobalResource(String cssFileName) throws MalformedURLException
	{
		File file = new File(GLOBAL_CSS_RESOURCE_DESTINATION+cssFileName);
		String complete = "file:/"+file.getAbsolutePath();
		return  ClassLoader.getSystemResource(complete);
	}
	
	public static ObservableList<CSSContainer> getGlobalCSSContainerList() throws MalformedURLException 
	{
		List<CSSContainer> cssList = new ArrayList<CSSContainer>();
		//War das schon immer so bescheiden, mit der Setzung eines Pfades?
		File file = new File("");
		file = new File(file.getAbsolutePath()+GLOBAL_CSS_RESOURCE_DESTINATION);
		
		File[] fileArray = file.listFiles();
		for(int i = 0; i < fileArray.length; i++)
		{
			String absPath = fileArray[i].getAbsolutePath();
			if(absPath.endsWith(".css"))
			{
				int lastIndex = absPath.lastIndexOf(File.separator);
				if(absPath.endsWith(".css"))
				{
					String filename = absPath.substring(lastIndex+1, absPath.length());
					cssList.add(new CSSContainer(filename, GLOBAL_CSS_RESOURCE_DESTINATION));
				}
			}
		}
		return FXCollections.observableArrayList(cssList);
	}
	
	public static URL getResourceProperty(String resourceProperty) throws MalformedURLException
	{
		
		File file = new File(RESOURCE_DESTINATION + resourceProperty);
		String complete = "file:/"+file.getAbsolutePath();
		return  ClassLoader.getSystemResource(complete);
	}
	
	
	

}
