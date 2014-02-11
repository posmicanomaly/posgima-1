package com.jpospisil.posgima1;
import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;


public class SFMLFont {
	private Font defaultFont;
	
	public SFMLFont() {
		this.createDefaultFont();
	}	
	
	private void createDefaultFont()
	{
		defaultFont = new Font();
		loadFontFromFile(defaultFont, "resources/cour.ttf");
	}
	
	public Font getDefaultFont()
	{
		return this.defaultFont;
	}
	
	private void loadFontFromFile(Font font, String path)
	{
		try
		{
			font.loadFromFile(Paths.get(path));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
