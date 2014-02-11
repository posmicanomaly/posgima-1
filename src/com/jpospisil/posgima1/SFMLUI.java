package com.jpospisil.posgima1;
import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.View;


public class SFMLUI {

	public ArrayList<String> messages = new ArrayList<String>();	
	
	private SFMLRenderWindow window;
	private View sideView;
	private View bottomView;
	public SFMLUI(SFMLRenderWindow window) 
	{
		this.window = window;
		sideView = new View(window.getRenderWindow().getDefaultView().getCenter(), window.getRenderWindow().getDefaultView().getSize());
		bottomView = new View(window.getRenderWindow().getDefaultView().getCenter(), window.getRenderWindow().getDefaultView().getSize());
		sideView.setSize(GameConstants.WIDTH, GameConstants.HEIGHT);
		bottomView.setSize(GameConstants.WIDTH, GameConstants.HEIGHT);
		//sideView.setViewport(new FloatRect(0,0,1,1));
		
	}
	//currentview is used to reset it after
	
	public void drawBottomUI()
	{
		window.getRenderWindow().setView(bottomView);
		String madeMessage = "";
		for(int i = messages.size() -1; i > 0; i--)
		{
			madeMessage += messages.get(i);
		}
		Text text = new Text(madeMessage, GameConstants.FONTS.getDefaultFont(), GameConstants.BOTTOM_UI_FONT_SIZE);
		text.setPosition((int)(GameConstants.WIDTH * GameConstants.VIEWPORT_TOP_X), (int) (GameConstants.HEIGHT * GameConstants.VIEWPORT_BOTTOM_Y));		
		text.setColor(Color.WHITE);
		window.getRenderWindow().draw(text);		
	}
	public void drawSideUI(View currentView, Player player)
	{
		
		int health = player.getHealth();
		int digs = player.getDigCount();
		int hunger = player.getHungerLevel();
		String name = player.getName();
		int moves = player.getMoves();
		int food = player.getFoodCount();
		
		window.getRenderWindow().setView(this.sideView);
		Text text = new Text();
		text.setString(player.getName() + "\n"
					 + "\n"
					 + "Health: " + player.getHealth() + "\n"					
					 + "Hunger: " + player.getHungerLevel() + "\n"
					 + "Food: " + food +"\n"
					 + "Digs: " + digs +"\n"
					 + "Moves: " + moves +"\n"
					 + "\n"
					 + player.getCurrentTile().getType() + "\n"
					 + "");
		text.setFont(GameConstants.FONTS.getDefaultFont());
		text.setPosition(0, 0);
		text.setCharacterSize(GameConstants.SIDE_UI_FONT_SIZE);
		
		text.setColor(Color.WHITE);
		
		window.getRenderWindow().draw(text);
		//window.getRenderWindow().display();
		
		window.getRenderWindow().setView(currentView);
	}

}
