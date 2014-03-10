package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.Random;

import org.jsfml.graphics.Color;

public class Game
{
	private ActionHandler	actionHandler;
	private ArrayList<Npc>	npcArray;
	public SFMLRenderWindow	window;
	public SFMLASCIIRender	Renderer;
	public MapManager		mapManager;
	public SFMLInputManager	input;
	public SFMLUI			ui;
	public Player			player;
	public Npc				npc;

	private int				hoursElapsed;

	private boolean			newGame;

	public Game()
	{
		this.init();
		this.newGame();
	}

	private void processAllNpc()
	{
		for (Npc npc : this.npcArray)
		{

			int rand = new Random().nextInt(10);
			String direction;
			switch (rand)
			{
			case 0:
				direction = "north";
				break;
			case 1:
				direction = "south";
				break;
			case 2:
				direction = "west";
				break;
			case 3:
				direction = "east";
				break;
			default:
				direction = "stay";
				break;
			}
			npc.move(direction);
		}
	}
	private void determineInputMenu()
	{
		if (this.actionHandler.isMine())
			input.pollMiningKeys(player);
		else
			input.pollKeyEvents(player);
	}
	
	private void processNormalGame()
	{

		if (player.movedLastTurn)
		{
			this.setHoursElapsed(this.getHoursElapsed() + 1);
			player.movedLastTurn = false;
			player.processPlayerRules();

			// is this order correct
			this.growCrops();
			this.regenerateDugTerrain();
			this.processAllNpc();
			
			GameConstants.RENDER_REQUIRED = true;

		}
	}
	private void continueGame()
	{
		this.processNormalGame();				
		this.redrawAll();
		this.determineInputMenu();		
		this.processActions();
	}

	private void drawUI()
	{
		ui.drawSideUI(window.getWorldView(), player);
		ui.drawBottomUI();
	}

	public void gameLoop()
	{
		while (window.isOpen())
		{

			if (isNewGame())
			{
				this.newGame();
			}
			else if (GameConstants.won)
			{
				wonGame();
			}
			else if (player.isAlive())
			{
				continueGame();
			}

			else if (!player.isAlive())
			{
				lostGame();
			}
		}
	}

	public int getHoursElapsed()
	{
		return hoursElapsed;
	}

	private String getRandomValidTileType(String foundMessage)
	{
		Random random = new Random();
		String type = "";
		boolean typePresentPassed = false;

		while (!typePresentPassed)
		{
			int t = random.nextInt(4);

			switch (t)
			{
			case 0:
				type = "grass";
				break;
			case 1:
				type = "forest";
				break;
			case 2:
				type = "desert";
				break;
			case 3:
				type = "hill";
				break;
			}
			System.out.println("\n" + type + " searching...");
			if (mapManager.getGameMap().hasTileType(type))
			{
				System.out.println(type + " " + foundMessage);
				return type;

			}
		}
		System.out.println(type + " not found");
		return null;
	}

	private void growCrops()
	{
		if (this.getHoursElapsed() % 24 == 0)
		{
			for (int i = 0; i < this.mapManager.getGameMap().getTileArray()
					.size(); i++)
			{
				Tile tile = this.mapManager.getGameMap().getTileArray().get(i);
				if (tile.getType() == "farm")
				{
					if (tile.getItems().size() < 5)
					{
						tile.addItem(new Item("food"));
						tile.setDug(false);
					}
					else
					{
						tile.addItem(new Item("seed"));
						tile.setDug(false);
					}
				}
			}
		}
	}

	public void init()
	{
		window = new SFMLRenderWindow(GameConstants.FORMATTED_GAME_INFO);//
		setNewGame(true);
	}

	public boolean isNewGame()
	{
		return newGame;
	}

	private void lostGame()
	{
		
			window.getRenderWindow().clear(new Color(10, 10, 10));
			Renderer.deathScreen(player);
			drawUI();
			window.getRenderWindow().display();
			
		
		this.processActions();
		input.pollDeathKeyEvents(player);
	}

	private void newGame()
	{
		ui = new SFMLUI(window, this);
		this.actionHandler = new ActionHandler(this);
		GameConstants.won = false;
		Renderer = new SFMLASCIIRender(window);
		mapManager = new MapManager();
		input = new SFMLInputManager(window, this.actionHandler);
		
		ui.messages.clear();
		ui.messages.add("new game\n");
		ui.messages
				.add("------------------------------------------\n"
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
		// mapManager = new MapManager();
		setupPlayer();
		setupWinTile();
		setupFood();
		spawnNpc();

		//GameConstants.RENDER_REQUIRED = true;

		this.redrawAll();
		// new Thread(this).start();
		this.hoursElapsed = 0;
		setNewGame(false);
	}

	private void processActions()
	{	
		// Add a player state, action manager, check the action requested with
		// the state
		// and then do it here instead, replace the constants
		this.actionHandler.processActions(player);		
	}

	public void redrawAll()
	{
		window.getRenderWindow().clear(new Color(10, 10, 10));
		Renderer.renderMap(mapManager.getGameMap(), player);

		// if(GameConstants.DEBUG)
		 Renderer.renderWin(mapManager.getGameMap());
		Renderer.renderPlayer(player);
		Renderer.renderAllNpc(this.npcArray);
		this.drawUI();
		window.getRenderWindow().display();
	}

	private void regenerateDugTerrain()
	{
		for (Tile tile : this.mapManager.getGameMap().getTileArray())
		{
			if (tile.getDug())
			{
				if (tile.getType() != "farm")
				{
					// 24hrs * whatever for days
					if (tile.getDugDuration() > 24 * 1)
					{
						if (new Random().nextInt(10) < 1)
						{
							tile.setDug(false);
						}
					}
					else
					{
						tile.setDugDuration(tile.getDugDuration() + 1);
					}
				}
			}
		}
	}

	public void setHoursElapsed(int hoursElapsed)
	{
		this.hoursElapsed = hoursElapsed;
	}

	public void setNewGame(boolean b)
	{
		newGame = b;
	}

	private void setupFood()
	{
		// food
		for (int i = 0; i < GameConstants.MAX_FOOD_SEED; i++)
		{
			mapManager.getGameMap().getRandomFoodWorthyTile()
					.addItem(new Item("food"));
		}
	}

	private void setupPlayer()
	{
		player = new Player();
		player.setCurrentMap(mapManager.getGameMap());
		player.setCurrentTile(player.getCurrentMap().getRandomTileByType(
				getRandomValidTileType("player tile")));
	}

	private void setupWinTile()
	{
		mapManager.getGameMap()
				.getRandomTileByType(getRandomValidTileType("win tile"))
				.addItem(new Item("win"));
	}

	private void spawnNpc()
	{
		this.npcArray = new ArrayList<Npc>();
		for (int i = 0; i < new Random().nextInt(GameConstants.MAX_DEER_SEED); i++)
		{
			Npc npc = new Npc();
			npc.setCurrentMap(this.mapManager.getGameMap());
			npc.setCurrentTile(this.mapManager.getGameMap()
					.getRandomFoodWorthyTile());
			this.npcArray.add(npc);
		}

	}

	private void wonGame()
	{		
		window.getRenderWindow().clear(new Color(10, 10, 10));
		Renderer.winScreen(player);
		drawUI();
		window.getRenderWindow().display();		
		this.processActions();
		input.pollDeathKeyEvents(player);
	}

}
