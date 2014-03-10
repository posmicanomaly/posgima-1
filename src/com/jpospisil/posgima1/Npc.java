package com.jpospisil.posgima1;

public class Npc extends Player
{

	public Npc()
	{

	}

	public Npc(Tile tile)
	{
		currentTile = tile;

	}

	public boolean move(String direction)
	{
		Tile nextTile = null;
		switch (direction)
		{
		case "north":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX(),
					this.currentTile.getY() - GameConstants.DEFAULT_FONT_SIZE);
			break;

		case "south":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX(),
					this.currentTile.getY() + GameConstants.DEFAULT_FONT_SIZE);
			break;

		case "west":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX()
					- GameConstants.DEFAULT_FONT_SIZE, this.currentTile.getY());
			break;

		case "east":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX()
					+ GameConstants.DEFAULT_FONT_SIZE, this.currentTile.getY());
			break;
		case "stay":
			return false;
		default:
			return false;
		}
		if (nextTile != null)
		{
			String quickCheck = nextTile.getType();
			switch (quickCheck)
			{
			case "water":
				return false;
			case "house":
				return false;
			default:
				if (currentMap.checkTerrainCollision(nextTile))
				{

					this.currentTile = nextTile;
					// this.setMovedLastTurn(true);
					// this.setMoves(this.getMoves() + 1);
					return true;
				}
				else
				{
					// SFMLUI.messages.add("that " + nextTile.getType() +
					// " hurt\n");
					return false;
				}
			}

		}
		return false;

	}
}
