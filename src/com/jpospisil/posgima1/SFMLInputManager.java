package com.jpospisil.posgima1;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;


public class SFMLInputManager {

	
	private GameMap gameMap;
	private SFMLASCIIRender render;
	private SFMLRenderWindow SFMLWindow;
	private RenderWindow window;
	private Player player;
	
	
	public SFMLInputManager(SFMLRenderWindow sfmlRenderWindow, GameMap gameMap) {
		SFMLWindow = sfmlRenderWindow;
		window = sfmlRenderWindow.getRenderWindow();
		this.gameMap = gameMap;
	}
	
	public void pollMiningKeys(Player player)
	{
		SFMLUI.messages.add("Mine in which direction? (esc to cancel)\n");
		
		Game.redrawAll();
		boolean done = false;
		boolean cancel = false;
		while(!done)
		{
			for(Event event : this.window.pollEvents())
			{
				if(event.type == Event.Type.KEY_PRESSED)
				{
					
					Tile tile = null;
					KeyEvent keyEvent = event.asKeyEvent();
					switch(keyEvent.key)
					{
					case ESCAPE:
						
						cancel = true;
						break;

					case UP:
						
						tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX(), player.getCurrentTile().getY() - GameConstants.DEFAULT_FONT_SIZE);
						if(tile == null)
						{
							cancel = true;
							break;
						}
						if(!player.getCurrentMap().checkTerrainCollision(tile) && tile.getType() == "mountain")
						{
							tile.setType("road");
							tile.setPassableFlag(true);
							done = true;
						}						
						
						break;

					case DOWN:
						tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX(), player.getCurrentTile().getY() + GameConstants.DEFAULT_FONT_SIZE);
						if(tile == null)
						{
							cancel = true;
							break;
						}
						if(!player.getCurrentMap().checkTerrainCollision(tile) && tile.getType() == "mountain")
						{
							tile.setType("road");
							tile.setPassableFlag(true);
							done = true;
						}
						
						break;

					case RIGHT:
						tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX() + GameConstants.DEFAULT_FONT_SIZE, player.getCurrentTile().getY());
						if(tile == null)
						{
							cancel = true;
							break;
						}
						if(!player.getCurrentMap().checkTerrainCollision(tile) && tile.getType() == "mountain")
						{
							tile.setType("road");
							tile.setPassableFlag(true);
							done = true;
						}
						
						break;

					case LEFT:
						tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX() - GameConstants.DEFAULT_FONT_SIZE, player.getCurrentTile().getY());
						if(tile == null)
						{
							cancel = true;
							break;
						}
						if(!player.getCurrentMap().checkTerrainCollision(tile) && tile.getType() == "mountain")
						{
							tile.setType("road");
							tile.setPassableFlag(true);
							done = true;
						}
						
						break;
					}					
				}
				if(cancel)
					break;
				if(done)
				{
					player.mine();
				}
				
			}
			if(cancel)
			{
				SFMLUI.messages.add("cancelled\n");
			
				break;
			}
			GameConstants.PLAYER_INTENT_TO_MINE = false;
		}

	}
	public void pollKeyEvents(Player player)
	{
		for(Event event : this.window.pollEvents())
		{
			if(event.type == Event.Type.KEY_PRESSED)
			{
				player.setMovedLastTurn(false);
				KeyEvent keyEvent = event.asKeyEvent();
				
				switch(keyEvent.key)
				{
				case ESCAPE:
					this.window.close();
					break;
					
				case UP:
					player.move("north");
					break;
					
				case DOWN:
					player.move("south");
					break;
					
				case LEFT:
					player.move("west");
					break;
					
				case RIGHT:
					player.move("east");
					break;
					
				case EQUAL:
					//SFMLWindow.setZoom(SFMLWindow.getWorldView(), .5f);
					GameConstants.MAP_GENERATOR_COLS += 20;
					GameConstants.MAP_GENERATOR_ROWS += 20;
					GameConstants.resetSeedValues();
					SFMLUI.messages.add("Increased map size, press (P) to generate new world!" + GameConstants.MAP_GENERATOR_ROWS + "x" + GameConstants.MAP_GENERATOR_COLS + ")\n");
					GameConstants.RENDER_REQUIRED = true;
					
					break;
					
				case DASH:					
					//SFMLWindow.setZoom(SFMLWindow.getWorldView(), 2.f);
					GameConstants.MAP_GENERATOR_COLS -= 20;
					GameConstants.MAP_GENERATOR_ROWS -= 20;
					GameConstants.resetSeedValues();
					SFMLUI.messages.add("Decreased map size, press (P) to generate new world!(" + GameConstants.MAP_GENERATOR_ROWS + "x" + GameConstants.MAP_GENERATOR_COLS + ")\n");
					GameConstants.RENDER_REQUIRED = true;
					break;
					
				case S:
					if(player.getCurrentTile().getType() == "house")
					{
						player.sleep();						
					}
					break;
					
				case H:
					if(player.getCurrentTile().getType() != "water" && player.getCurrentTile().getType() != "house")
					{
						player.buildHouse();
						player.setMovedLastTurn(true);
					}
					break;
					
				case R:
					if(player.getCurrentTile().getType() != "water" && player.getCurrentTile().getType() != "road" && player.getCurrentTile().getType() != "house")
					{
						player.buildRoad();					
						player.setMovedLastTurn(true);
					}
					break;
					
				case D:
					if(player.getCurrentTile().getType() != "water")
					{
						player.dig();
						player.setMovedLastTurn(true);
					}
					break;
					
				case P:
					SFMLUI.messages.add("---GENERATING WORLD, THIS COULD TAKE A WHILE---\n");
					Game.redrawAll();
					Game.setNewGame(true);
					break;
					
				case K:
					GameConstants.toggleKeyRepeat(SFMLWindow);
					break;
					
				case M:
					if(player.getHungerLevel() <= 5)
						GameConstants.PLAYER_INTENT_TO_MINE = true;
					else
						SFMLUI.messages.add("Too hungry to mine...\n");
					GameConstants.RENDER_REQUIRED = true;
					break;
					
				case E:
					player.eat();
					
					
				default:
					break;
				
				}
			}
			
		}
			
	}

	public void pollDeathKeyEvents(Player player2) {
		for(Event event : this.window.pollEvents())
		{
			if(event.type == Event.Type.KEY_PRESSED)
			{
				//player.setMovedLastTurn(false);
				KeyEvent keyEvent = event.asKeyEvent();
				
				switch(keyEvent.key)
				{
				case ESCAPE:
					this.window.close();
					break;
				case P:
					Game.setNewGame(true);
					break;
				}
			}
		}
		
	}
}
