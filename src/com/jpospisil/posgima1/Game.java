package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.Random;

import org.jsfml.graphics.Color;

public class Game{
	private ActionHandler actionHandler;
	private ArrayList<Npc> npcArray;
	public SFMLRenderWindow window;
	public SFMLASCIIRender Renderer;
	public MapManager mapManager;
	public SFMLInputManager input;
	public SFMLUI ui;
	public Player player;
	public Npc npc;
	
	private int hoursElapsed;

	public int getHoursElapsed() {
		return hoursElapsed;
	}

	public void setHoursElapsed(int hoursElapsed) {
		this.hoursElapsed = hoursElapsed;
	}

	private boolean newGame;

	public Game() {
		this.init();
		this.newGame();
	}
	
	private void growCrops()
	{
		if(this.getHoursElapsed()%24 == 0)
		{
			for(int i = 0; i < this.mapManager.getGameMap().getTileArray().size(); i++)
			{
				Tile tile = this.mapManager.getGameMap().getTileArray().get(i);
				if(tile.getType() == "farm")
				{
					if(tile.getItems().size() < 5)
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
	private void spawnNpc()
	{
		this.npcArray = new ArrayList<Npc>();
		for(int i = 0; i < new Random().nextInt(GameConstants.MAX_DEER_SEED); i++)
		{
			Npc npc = new Npc();
			npc.setCurrentMap(this.mapManager.getGameMap());
			npc.setCurrentTile(this.mapManager.getGameMap().getRandomFoodWorthyTile());
			this.npcArray.add(npc);
		}
		
	}
	private void regenerateDugTerrain()
	{
		for(Tile tile : this.mapManager.getGameMap().getTileArray())
		{
			if(tile.getDug())
			{
			if(tile.getType()!= "farm")
			{
				//24hrs * whatever for days
				if(tile.getDugDuration() > 24 * 1)
				{
					if(new Random().nextInt(10) < 1) {
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

	private void continueGame() {

		if (player.movedLastTurn) {
			this.setHoursElapsed(this.getHoursElapsed()+1);
			player.movedLastTurn = false;
			player.processPlayerRules();
			
			//is this order correct
			this.growCrops();
			this.regenerateDugTerrain();
			for(Npc npc : this.npcArray)
			{


				int rand = new Random().nextInt(10);
				switch(rand)
				{
				case 0: npc.move("north"); break;
				case 1: npc.move("south"); break;
				case 2: npc.move("west"); break;
				case 3: npc.move("east"); break;
				default: break;
				}
			}
			GameConstants.RENDER_REQUIRED = true;

		}
		//if (GameConstants.RENDER_REQUIRED)
		this.redrawAll();
		if (this.actionHandler.isMine())
			input.pollMiningKeys(player);
		else
			input.pollKeyEvents(player);
		this.processActions();
	}

	private void drawUI() {
		ui.drawSideUI(window.getWorldView(), player);
		ui.drawBottomUI();
	}

	public void gameLoop() {
		while (window.isOpen()) {

			if (isNewGame()) {
				this.newGame();
			}
			if (GameConstants.won) {
				wonGame();
			}
			if (player.isAlive()) {
				continueGame();
			}

			if (!player.isAlive()) {
				lostGame();
			}
		}
	}

	private String getRandomValidTileType(String foundMessage) {
		Random random = new Random();
		String type = "";
		boolean typePresentPassed = false;

		while (!typePresentPassed) {
			int t = random.nextInt(4);

			switch (t) {
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
			if (mapManager.getGameMap().hasTileType(type)) {
				System.out.println(type + " " + foundMessage);
				return type;

			}
		}
		return null;
	}

	public void init() {
		window = new SFMLRenderWindow(GameConstants.FORMATTED_GAME_INFO);//
		setNewGame(true);
	}

	public boolean isNewGame() {
		return newGame;
	}

	private void lostGame() {
		if (GameConstants.RENDER_REQUIRED) {
			window.getRenderWindow().clear(new Color(10, 10, 10));
			Renderer.deathScreen(player);
			drawUI();
			window.getRenderWindow().display();
			GameConstants.RENDER_REQUIRED = false;
		}
		this.processActions();
		input.pollDeathKeyEvents(player);
	}

	public void newGame() {
		this.actionHandler = new ActionHandler();
		GameConstants.won = false;
		Renderer = new SFMLASCIIRender(window);
		mapManager = new MapManager();
		input = new SFMLInputManager(window, this.actionHandler);
		ui = new SFMLUI(window, this);
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

		GameConstants.RENDER_REQUIRED = true;

		this.redrawAll();
		// new Thread(this).start();
		this.hoursElapsed = 0;
		setNewGame(false);
	}

	private void processActions() {
		ActionHandler a = this.actionHandler;
		// Add a player state, action manager, check the action requested with
		// the state
		// and then do it here instead, replace the constants

		if (a.isMine()) {
			if (!player.isMining()) {
				ui.messages.add("Mine in which direction? (esc to cancel)\n");
				this.redrawAll();
				player.setMining(true);

			}
			if (player.isMining()) {
				if (player.isMiningSuccess()) {

					if (player.mine()) {
						this.ui.messages
								.add("You mined through the mountain!\n");
						player.getLookingAt().setType("road");
						player.getLookingAt().setPassableFlag(true);
					} else {
						this.ui.messages.add("Too hungry to mine..\n");

					}
					a.setMine(false);
					player.setMining(false);

				}

			}
			GameConstants.RENDER_REQUIRED = true;
		}

		if (a.isDig()) {
			player.dig();
			a.setDig(false);
			player.setMovedLastTurn(true);
		}

		if (a.isBuildRoad()) {
			if (!player.buildRoad()) {
				this.ui.messages.add("Too hungry to make a road..\n");
				this.redrawAll();
			} else {
				this.ui.messages
						.add("You clear the terrain, creating a road\n");
				player.setMovedLastTurn(true);
			}
			a.setBuildRoad(false);
		}
		if(a.isBuildFarm()) {
			String message = player.buildFarm();
			if(message == "hungerError") {
				this.ui.messages.add("Too hungry to build a farm, lol\n");
				this.redrawAll();
			}
			else if(message == "noSeeds"){
				this.ui.messages.add("You need seeds to plant\n");
				this.redrawAll();
			}
			else if(message == "built"){
				this.ui.messages.add("You built a farm\n");
				player.setMovedLastTurn(true);
			}
			a.setBuildFarm(false);
		}

		// TODO something about motivation stat, and being too lazy to build a
		// house
		if (a.isBuildHouse()) {
			if (!player.buildHouse()) {
				this.ui.messages.add("Too hungry to build a house..\n");
				this.redrawAll();
			} else {
				this.ui.messages.add("You built a house\n");
				player.setMovedLastTurn(true);
			}
			a.setBuildHouse(false);
		}
		if (a.isSleep()) {
			String message = player.sleep();
			if(message == "sleeping")
			{
				this.ui.messages.add("Zzz\n");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(message == "tooHungry")
			{
				this.ui.messages.add("Too hungry to sleep\n");
				a.setSleep(false);
			}
		}

		if (a.isIncreaseMapSize()) {
			GameConstants.MAP_GENERATOR_COLS += 20;
			GameConstants.MAP_GENERATOR_ROWS += 20;
			GameConstants.resetSeedValues();
			this.ui.messages
					.add("Increased map size, press (P) to generate new world!"
							+ GameConstants.MAP_GENERATOR_ROWS + "x"
							+ GameConstants.MAP_GENERATOR_COLS + ")\n");
			GameConstants.RENDER_REQUIRED = true;
			a.setIncreaseMapSize(false);
		}

		if (a.isDecreaseMapSize()) {
			GameConstants.MAP_GENERATOR_COLS -= 20;
			GameConstants.MAP_GENERATOR_ROWS -= 20;
			GameConstants.resetSeedValues();
			this.ui.messages
					.add("Decreased map size, press (P) to generate new world!("
							+ GameConstants.MAP_GENERATOR_ROWS
							+ "x"
							+ GameConstants.MAP_GENERATOR_COLS + ")\n");
			GameConstants.RENDER_REQUIRED = true;
			a.setDecreaseMapSize(false);
		}

		if (a.isRegenerateMap()) {
			this.ui.messages
					.add("---GENERATING WORLD, THIS COULD TAKE A WHILE---\n");
			this.redrawAll();
			this.setNewGame(true);
			a.setRegenerateMap(false);
		}
	}

	public void redrawAll() {
		window.getRenderWindow().clear(new Color(10, 10, 10));
		Renderer.renderMap(mapManager.getGameMap(), player);

		// if(GameConstants.DEBUG)
		// Renderer.renderWin(mapManager.getGameMap());
		Renderer.renderPlayer(player);
		Renderer.renderAllNpc(this.npcArray);
		this.drawUI();
		window.getRenderWindow().display();
	}	

	public void setNewGame(boolean b) {
		newGame = b;
	}

	private void setupFood() {
		// food
		for (int i = 0; i < GameConstants.MAX_FOOD_SEED; i++) {
			mapManager.getGameMap().getRandomFoodWorthyTile()
					.addItem(new Item("food"));
		}
	}

	private void setupPlayer() {
		player = new Player();
		player.setCurrentMap(mapManager.getGameMap());
		player.setCurrentTile(player.getCurrentMap().getRandomTileByType(
				getRandomValidTileType("player tile")));
	}

	private void setupWinTile() {
		mapManager.getGameMap().getRandomTileByType(getRandomValidTileType("win tile"))
				.addItem(new Item("win"));
	}

	private void wonGame() {
		GameConstants.RENDER_REQUIRED = true;
		if (GameConstants.RENDER_REQUIRED) {
			window.getRenderWindow().clear(new Color(10, 10, 10));
			Renderer.winScreen(player);
			drawUI();
			window.getRenderWindow().display();
			GameConstants.RENDER_REQUIRED = false;
		}
		this.processActions();
		input.pollDeathKeyEvents(player);
	}

}
