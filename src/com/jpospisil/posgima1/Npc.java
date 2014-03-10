package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.Random;

public class Npc extends Entity
{
	private boolean stuckAggro;
	private ArrayList<Tile> tileHistory;
	
	public Npc()
	{
		this.name = "npc";
		this.setHealth(20);
		this.setAlive(true);
	}

	
	public Npc(Tile tile)
	{
		currentTile = tile;
		this.tileHistory = new ArrayList<Tile>();
		this.name = "npc";

	}

	public String moveTowards(Player player)
	{
		int player_x = player.getCurrentTile().getX();
		int player_y = player.getCurrentTile().getY();
		
		int npc_x = this.getCurrentTile().getX();
		int npc_y = this.getCurrentTile().getY();
		
		
		
			
		int rand = new Random().nextInt(2);
		if(rand == 0)
		{
			if(player_x < npc_x)
				return this.move("west");
			else
				return this.move("east");
		}
		else
		{
			if(player_y < npc_y)
				return this.move("north");
			else
				return this.move("south");
		}
	}
	public void wander()
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
			this.move(direction);
		
	}
	public String move(String direction)
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
			return "false";
		default:
			return "false";
		}
		if (nextTile != null)
		{
			String quickCheck = nextTile.getType();
			switch (quickCheck)
			{
			case "water":
				return "false";
			case "house":
				return "false";
			default:
				if (currentMap.checkTerrainCollision(nextTile) && !currentMap.checkEntityCollision(nextTile))
				{
					
					this.getCurrentTile().removeEntity(this);
					this.currentTile = nextTile;
					this.getCurrentTile().addEntity(this);
					// this.setMovedLastTurn(true);
					// this.setMoves(this.getMoves() + 1);
					return "true";
				}
				else if(currentMap.checkEntityCollision(nextTile))
				{	
					this.combatWithEntity(nextTile.getEntities().get(0), nextTile);			
					return "collision with entity";
				}
				else
				{
					// SFMLUI.messages.add("that " + nextTile.getType() +
					// " hurt\n");
					return "false";
				}
			}

		}
		return "faslse";

	}
	
}
