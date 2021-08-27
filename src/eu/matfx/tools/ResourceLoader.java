package eu.matfx.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class ResourceLoader 
{
	
	private static final String GLOBAL_RESOURCE_PATH = "/resources/global/";
	
	private static final String ICONS = GLOBAL_RESOURCE_PATH + "/icons/";
	
	public static final String SUFFIX_FILE = ".png";
	
	/**
	 * globale Resourcen für das Grundlayout
	 */
	private static final String GLOBAL_CSS_RESOURCE_DESTINATION = GLOBAL_RESOURCE_PATH + "css/";
	
	private static final String RESOURCE_DESTINATION = "/resources/";
	
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
	
	public static Image getImageFromIconFolder(String fileName)
	{
		Image image = null;
		
		if(!fileName.contains(ResourceLoader.SUFFIX_FILE))
			fileName = fileName + ResourceLoader.SUFFIX_FILE;
		try
		{
			InputStream ins = ResourceLoader.getResourceStream(ICONS, fileName);
			image = new Image(ins);
			ins.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}
	
	public static InputStream getResourceStream(String pkname, String fname) throws FileNotFoundException
	{
		String resname = "" + pkname + "/" + fname;
		File file = new File("");
		
		file = new File(file.getAbsoluteFile() + resname);
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
	
		return inputStream;
	}
	
	
	
	

}
