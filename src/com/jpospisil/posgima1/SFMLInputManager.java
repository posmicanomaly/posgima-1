package com.jpospisil.posgima1;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;


public class SFMLInputManager {

	private ActionHandler actionHandler;
	//private GameMap gameMap;
	private SFMLASCIIRender render;
	private SFMLRenderWindow SFMLWindow;
	private RenderWindow window;
	//private Player player;
	
	
	public SFMLInputManager(SFMLRenderWindow sfmlRenderWindow, ActionHandler actionHandler) {
		SFMLWindow = sfmlRenderWindow;
		window = sfmlRenderWindow.getRenderWindow();
		this.actionHandler = actionHandler;
	}
	
	public void pollMiningKeys(Player player)
	{


		//Game.redrawAll();
		boolean done = false;		
		//while(!done)
		//{
		for(Event event : this.window.pollEvents())
		{
			if(event.type == Event.Type.KEY_PRESSED)
			{

				Tile tile = null;
				KeyEvent keyEvent = event.asKeyEvent();
				switch(keyEvent.key)
				{
				case ESCAPE:
					this.actionHandler.setMine(false);
					player.setMining(false);
					return;						

				case UP:
					tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX(), player.getCurrentTile().getY() - GameConstants.DEFAULT_FONT_SIZE);
					
					break;

				case DOWN:
					tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX(), player.getCurrentTile().getY() + GameConstants.DEFAULT_FONT_SIZE);
					break;

				case RIGHT:
					tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX() + GameConstants.DEFAULT_FONT_SIZE, player.getCurrentTile().getY());
					break;

				case LEFT:
					tile = player.getCurrentMap().getTileFromCoords(player.getCurrentTile().getX() - GameConstants.DEFAULT_FONT_SIZE, player.getCurrentTile().getY());
					break;
				}
				
				if(tile == null)
				{
					return;							
				}
				if(!player.getCurrentMap().checkTerrainCollision(tile) && tile.getType() == "mountain")
				{					
					player.setMiningSuccess(true);
					player.setLookingAt(tile);
					//return true;
				}
			}				
			//if(done)
			//{
			//player.mine();
			
			//}				
			//}			
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
					this.actionHandler.setIncreaseMapSize(true);					
					break;
					
				case DASH:
					this.actionHandler.setDecreaseMapSize(true);					
					break;
					
				case S:
					if(player.getCurrentTile().getType() == "house")
						this.actionHandler.setSleep(true);
					break;
					
				case H:
					if(player.getCurrentTile().getType() != "water" && player.getCurrentTile().getType() != "house")
						this.actionHandler.setBuildHouse(true);
					break;
					
				case R:
					if(player.getCurrentTile().getType() != "water" && player.getCurrentTile().getType() != "road" && player.getCurrentTile().getType() != "house")
						this.actionHandler.setBuildRoad(true);					
					break;
					
				case D:
					if(player.getCurrentTile().getType() != "water")
						this.actionHandler.setDig(true);
					break;
					
				case P:
					this.actionHandler.setRegenerateMap(true);					
					break;
					
				case K:
					GameConstants.toggleKeyRepeat(SFMLWindow);
					break;
					
				case M:					
					this.actionHandler.setMine(true);					
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
					this.actionHandler.setRegenerateMap(true);					
					break;
				}
			}
		}
		
	}
}
