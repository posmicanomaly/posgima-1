package com.jpospisil.posgima1;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

public class SFMLInputManager
{

	private ActionHandler		actionHandler;
	// private GameMap gameMap;
	private SFMLASCIIRender		render;
	private SFMLRenderWindow	SFMLWindow;
	private RenderWindow		window;

	// private Player player;

	public SFMLInputManager(SFMLRenderWindow sfmlRenderWindow,
			ActionHandler actionHandler)
	{
		SFMLWindow = sfmlRenderWindow;
		window = sfmlRenderWindow.getRenderWindow();
		this.actionHandler = actionHandler;
	}

	public void pollDeathKeyEvents(Player player2)
	{
		for (Event event : this.window.pollEvents())
		{
			if (event.type == Event.Type.KEY_PRESSED)
			{
				// player.setMovedLastTurn(false);
				KeyEvent keyEvent = event.asKeyEvent();

				switch (keyEvent.key)
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

	public void pollKeyEvents(Player player)
	{
		for (Event event : this.window.pollEvents())
		{
			if (event.type == Event.Type.KEY_PRESSED)
			{
				player.setMovedLastTurn(false);
				KeyEvent keyEvent = event.asKeyEvent();
				ActionHandler a = this.actionHandler;
				switch (keyEvent.key)
				{
				case ESCAPE:
					this.window.close();
					break;

				case UP:
					a.setMoveNorth(true);					
					break;

				case DOWN:
					a.setMoveSouth(true);					
					break;

				case LEFT:
					a.setMoveWest(true);					
					break;

				case RIGHT:
					a.setMoveEast(true);					
					break;

				case EQUAL:
					a.setIncreaseMapSize(true);
					break;

				case DASH:
					a.setDecreaseMapSize(true);
					break;

				case S:					
					a.setSleep(true);
					break;

				case H:					
					a.setBuildHouse(true);
					break;

				case F:
					a.setBuildFarm(true);
					break;

				case P:					
					a.setBuildRoad(true);
					break;

				case D:					
					a.setDig(true);
					break;

				case Z:
					a.setRegenerateMap(true);
					break;

				case K:
					GameConstants.toggleKeyRepeat(SFMLWindow);
					break;

				case M:
					a.setMine(true);
					break;

				case E:
					a.setEat(true);
					break;

				default:
					break;

				}
			}

		}

	}

	public void pollMiningKeys(Player player)
	{

		// Game.redrawAll();
		boolean done = false;
		// while(!done)
		// {
		for (Event event : this.window.pollEvents())
		{
			if (event.type == Event.Type.KEY_PRESSED)
			{

				Tile tile = null;
				KeyEvent keyEvent = event.asKeyEvent();
				switch (keyEvent.key)
				{
				case ESCAPE:
					this.actionHandler.setMine(false);
					player.setMining(false);
					return;

				case UP:
					tile = player.getCurrentMap().getTileFromCoords(
							player.getCurrentTile().getX(),
							player.getCurrentTile().getY()
									- GameConstants.DEFAULT_FONT_SIZE);

					break;

				case DOWN:
					tile = player.getCurrentMap().getTileFromCoords(
							player.getCurrentTile().getX(),
							player.getCurrentTile().getY()
									+ GameConstants.DEFAULT_FONT_SIZE);
					break;

				case RIGHT:
					tile = player.getCurrentMap().getTileFromCoords(
							player.getCurrentTile().getX()
									+ GameConstants.DEFAULT_FONT_SIZE,
							player.getCurrentTile().getY());
					break;

				case LEFT:
					tile = player.getCurrentMap().getTileFromCoords(
							player.getCurrentTile().getX()
									- GameConstants.DEFAULT_FONT_SIZE,
							player.getCurrentTile().getY());
					break;
				}

				if (tile == null)
				{
					return;
				}
				if (!player.getCurrentMap().checkTerrainCollision(tile)
						&& tile.getType() == "mountain")
				{
					player.setMiningSuccess(true);
					player.setLookingAt(tile);
					// return true;
				}
			}
			// if(done)
			// {
			// player.mine();

			// }
			// }
		}

	}
}
