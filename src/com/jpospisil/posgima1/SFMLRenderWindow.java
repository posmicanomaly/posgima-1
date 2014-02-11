package com.jpospisil.posgima1;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;


public class SFMLRenderWindow {

	private RenderWindow window;
	private View worldView;
	private ConstView defaultView;
	private String title = "test";
	
	public SFMLRenderWindow(String title) {
		window = new RenderWindow();
		
		//this.resolutionX = sizeX;
		//this.resolutionY = sizeY;
		this.title = title;
		
		this.createWindow();
		
		worldView = new View();
		defaultView = new View();
		defaultView = window.getDefaultView();
		worldView = new View(defaultView.getCenter(), defaultView.getSize());
		//worldView.setSize(VideoMode.getDesktopMode().width, VideoMode.getDesktopMode().height);
		worldView.setViewport(new FloatRect(GameConstants.VIEWPORT_TOP_X,
											GameConstants.VIEWPORT_TOP_Y,
											GameConstants.VIEWPORT_BOTTOM_X,
											GameConstants.VIEWPORT_BOTTOM_Y));
		//currentView.zoom(GameConstants.DEFAULT_ZOOM_LEVEL);
		
		this.clearWindow(Color.BLACK);
		this.displayWindow();		
	}
	
	
	private void clearWindow()
	{
		window.clear();
	}
	
	private void clearWindow(Color color)
	{
		window.clear(color);
	}
	
	private void createWindow()
	{
		if(GameConstants.DEBUG_WINDOWED)
		{
			window.create(new VideoMode(GameConstants.WIDTH,  GameConstants.HEIGHT), this.title);
			window.setPosition(new Vector2i(0, 0));
		}
		else
			window.create(new VideoMode(GameConstants.WIDTH, GameConstants.HEIGHT), this.title, window.FULLSCREEN);
		window.setKeyRepeatEnabled(GameConstants.KEY_REPEAT_STATUS);
		window.setMouseCursorVisible(true);
	}
	
	private void displayWindow()
	{
		window.display();
	}
	
	public RenderWindow getRenderWindow()
	{
		return this.window;
	}
	
	public View getWorldView()
	{
		return this.worldView;
	}
	
	public boolean isOpen()
	{
		return this.window.isOpen();
	}
	
	public void setZoom(View view, float zoomLevel)
	{
		view.zoom(zoomLevel);
	}
	
}
