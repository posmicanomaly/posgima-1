package com.jpospisil.posgima1;
import java.util.Random;

import org.jsfml.graphics.Color;


public abstract class Game {
	public static SFMLRenderWindow window;
	public static SFMLASCIIRender Renderer;
	public static MapManager mapManager;
	public static SFMLInputManager input;
	public static SFMLUI ui;
	public static Player player;
	
	private static boolean newGame;
	
	public static void redrawAll()
	{
		window.getRenderWindow().clear(new Color(10, 10, 10));
		Renderer.renderMap(mapManager.getGameMap(), player);

		//if(GameConstants.DEBUG)
		//	Renderer.renderWin(mapManager.getGameMap());
		Renderer.renderPlayer(player);
		ui.drawSideUI(window.getWorldView(), player);
		SFMLUI.drawBottomUI();
		window.getRenderWindow().display();
	}
	public Game()
	{
		
	}
	public static void init()
	{
		window = new SFMLRenderWindow(GameConstants.FORMATTED_GAME_INFO);//
		setNewGame(true);
	}
	private static void wonGame()
	{
		GameConstants.RENDER_REQUIRED = true;
		if(GameConstants.RENDER_REQUIRED)
		{
			window.getRenderWindow().clear(new Color(10, 10, 10));
			Renderer.winScreen(player);
			ui.drawSideUI(window.getWorldView(), player);
			ui.drawBottomUI();
			window.getRenderWindow().display();
			GameConstants.RENDER_REQUIRED = false;
		}
		input.pollDeathKeyEvents(player);
	}
	
	private static void lostGame()
	{
		if(GameConstants.RENDER_REQUIRED)
		{
			window.getRenderWindow().clear(new Color(10, 10, 10));
			Renderer.deathScreen(player);
			ui.drawSideUI(window.getWorldView(), player);
			ui.drawBottomUI();
			window.getRenderWindow().display();
			GameConstants.RENDER_REQUIRED = false;
		}
		input.pollDeathKeyEvents(player);
	}
	
	private static void continueGame()
	{
		if(GameConstants.PLAYER_INTENT_TO_MINE)
			input.pollMiningKeys(player);
		if(player.movedLastTurn)
		{
			player.movedLastTurn = false;
			player.processPlayerRules();
			GameConstants.RENDER_REQUIRED = true;

		}
		if(GameConstants.RENDER_REQUIRED)
			Game.redrawAll();
		input.pollKeyEvents(player);
	}
	
	public static void gameLoop()
	{
		while(window.isOpen())
		{
			
			if(isNewGame())
			{
				newGame();				
			}
			if(GameConstants.won)
			{
				wonGame();				
			}
			if(player.isAlive())
			{					
				continueGame();	
			}					
			
			if(!player.isAlive())
			{
				lostGame();
			}			
		}
	}
	
	public static void newGame()
	{
		Random random = new Random();
		String type = null;
		GameConstants.won = false;
		Renderer = new SFMLASCIIRender(window);
		mapManager = new MapManager();
		input = new SFMLInputManager(window, mapManager.getGameMap());
		ui = new SFMLUI(window);
		SFMLUI.messages.clear();
		SFMLUI.messages.add("new game\n");
		SFMLUI.messages.add("------------------------------------------\n"
				+ "You awake in a strange land, (D)ig for the buried artifact!\n"
				+ "(D)ig for Food to avoid starvation! Don't forget to (E)at it!\n"
				+ "(M)ountains are no (M)atch for you!\n"
				+ "(R)oads save hunger! Because that's how it is!\n"						
				+ "Build a (H)ouse if you're not too hungry!\n"
				+ "(H)ouses are good for (S)leeping in to regain health!\n"
				+ "Beware! The waters are full of danger!\n"
				+ "---------Debug active\n(P) for new map/game\n"
				+ "(K) to toggle keyrepeat(not recommended)\n"
				+ "(-) to decrease the map, (=) to increase it, followed by (P) to regenerate(huge maps take a long time to build)\n");
		//mapManager = new MapManager();
		player = new Player();
		player.setCurrentMap(mapManager.getGameMap());
		boolean typePresentPassed = false;
		type = null;
		while(!typePresentPassed)
		{
			int t = random.nextInt(4);
			
			switch(t)
			{
			case 0:	type = "grass"; break;
			case 1: type = "forest"; break;
			case 2: type = "desert"; break;
			case 3: type = "hill"; break;
			}
			System.out.println(type + " searching(player)...");
			if(mapManager.getGameMap().hasTileType(type))
			{
				System.out.println(type + " has been found(player)");
				typePresentPassed = true;
			}
		}
		player.setCurrentTile(player.getCurrentMap().getRandomTileByType(type));
		
		typePresentPassed = false;
		;
		while(!typePresentPassed)
		{
			int t = random.nextInt(4);
			
			switch(t)
			{
			case 0:	type = "grass"; break;
			case 1: type = "forest"; break;
			case 2: type = "desert"; break;
			case 3: type = "hill"; break;
			}
			System.out.println(type + " searching...");
			if(mapManager.getGameMap().hasTileType(type))
			{
				System.out.println(type + " has been found");
				typePresentPassed = true;
			}
		}
		mapManager.getGameMap().getRandomTileByType(type).addItem(new Item("win"));
		//food
		for(int i = 0; i < GameConstants.MAX_FOOD_SEED; i++)
		{
			mapManager.getGameMap().getRandomFoodWorthyTile().addItem(new Item("food"));
		}
		
		GameConstants.RENDER_REQUIRED = true;
		setNewGame(false);
	}
	public static boolean isNewGame() {
		return newGame;
	}
	public static void setNewGame(boolean b) {
		newGame = b;
	}
	
}
