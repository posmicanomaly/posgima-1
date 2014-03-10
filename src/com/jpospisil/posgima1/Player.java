package com.jpospisil.posgima1;

import java.util.Iterator;
import java.util.Random;

public class Player extends Entity
{

	
	
	

	public Player()
	{
		currentTile = null;
		currentMap = null;
		this.lookingAt = null;

		this.name = "Player the playa hata";
		this.moves = 0;
		this.digCount = 0;
		this.foodCount = 0;
		this.seedCount = 1;
		this.hungerLevel = 0;
		this.health = 1000;
		this.isMining = false;
		this.miningSuccess = false;

		this.alive = true;
		// TODO Auto-generated constructor stub
	}

	public String buildFarm()
	{
		if (this.getSeedCount() > 0)
		{
			if (this.getHungerLevel() > 0)
			{
				return "hungerError";
			}
			else
			{
				this.getCurrentTile().setType("farm");
				this.setHungerLevel(10);
				this.setSeedCount(this.getSeedCount() - 1);
				return "built";
			}
		}
		return "noSeeds";
	}

	public boolean buildHouse()
	{
		if (this.getHungerLevel() > 0)
		{
			// SFMLUI.messages.add("Too hungry to build a house..\n");
			return false;
		}
		else
		{
			this.getCurrentTile().setType("house");
			this.setHungerLevel(10);
			return true;
		}
	}

	public boolean buildRoad()
	{
		if (this.getHungerLevel() > 5)
		{
			//
			return false;
		}
		this.getCurrentTile().setType("road");
		this.setHungerLevel(this.getHungerLevel() + 3);
		if (this.getHungerLevel() > 10)
			this.setHungerLevel(10);
		return true;

	}

	

	public void dig()
	{

		for (Item item : this.currentTile.getItems())
		{
			if (item.getName() == "food")
				this.setFoodCount(this.getFoodCount() + 1);
			else if (item.getName() == "seed")
			{
				this.setSeedCount(this.getSeedCount() + 1);
			}
			else if (item.getName() == "win")
			{
				GameConstants.won = true;
			}
		}
		this.currentTile.clearItems();
		this.setMoves(this.getMoves() + 1);
		this.setDigCount(this.getDigCount() + 1);
		this.currentTile.setDug(true);

	}

	public void eat()
	{
		if (this.getFoodCount() > 0)
		{
			
			if(this.getHungerLevel() > 0)
			{
				this.hungerLevel -= 10;
				if (this.hungerLevel < 0)
					this.hungerLevel = 0;
				this.setFoodCount(this.getFoodCount() - 1);				
				//GameConstants.RENDER_REQUIRED = true;
			}
			//if not hungry don't eat, == 0
		}
	}

	

	

	

	public boolean isMining()
	{
		return isMining;
	}

	public boolean isMiningSuccess()
	{
		return miningSuccess;
	}

	public boolean isMovedLastTurn()
	{
		return movedLastTurn;
	}

	public boolean mine()
	{
		// SFMLUI.messages.add("You mined the mountain, clearing a path!\n");
		this.miningSuccess = false;
		if (this.getHungerLevel() <= 5)
		{
			this.hungerLevel += 4;
			if (this.hungerLevel > 10)
				this.hungerLevel = 10;
			return true;
		}
		else
			return false;

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

		default:
			return "invalid direction";
		}
		if (nextTile != null)
		{
			if (currentMap.checkTerrainCollision(nextTile) && !currentMap.checkEntityCollision(nextTile))
			{

				if (nextTile.getType() == "water")
					this.swim();
				this.getCurrentTile().removeEntity(this);
				this.currentTile = nextTile;
				this.getCurrentTile().addEntity(this);
				this.setMovedLastTurn(true);
				this.setMoves(this.getMoves() + 1);
				return "no collisions";
			}
			else if(currentMap.checkEntityCollision(nextTile))
			{
				this.combatWithEntity(nextTile.getEntities().get(0), nextTile);
				this.setMovedLastTurn(true);
				this.setMoves(this.getMoves() + 1);
				return "collision with entity";
			}
			else
			{
				// SFMLUI.messages.add("that " + nextTile.getType() +
				// " hurt\n");
				return "collision with " + nextTile.getType();
			}
		}
		return "nextTile is null";

	}

	
	public void processPlayerRules()
	{
		if (!this.alive)
		{
			return;
		}		
		
		if (this.getCurrentTile().getType() != "road")
		{
			if (this.getHungerLevel() < GameConstants.MAXIMUM_HUNGER)
				this.setHungerLevel(this.getHungerLevel() + 1);
		}
		
		if (this.getHungerLevel() >= GameConstants.MAXIMUM_HUNGER)
			this.setHealth(this.getHealth() - 1);
		
		if (this.getHealth() < GameConstants.MINIMUM_HEALTH)
		{
			GameConstants.DEATH_MESSAGE = "YOU STARVED!";
			this.die();
			return;
		}

	}

	public boolean rest()
	{
		if (this.getHungerLevel() < 10)
		{
			this.setHealth(this.getHealth() + 1);
			this.setMovedLastTurn(true);
			return true;
		}
		return false;
		// else
		// SFMLUI.messages.add("Too hungry to sleep\n");

	}

	

	public String sleep()
	{
		if (this.getHungerLevel() < 10)
		{
			this.setHealth(this.getHealth() + 1);
			this.setMovedLastTurn(true);
			return "sleeping";
		}
		return "tooHungry";
		// else
		// SFMLUI.messages.add("Too hungry to sleep\n");

	}

	public void swim()
	{
		Random random = new Random();
		int dead = random.nextInt(10);
		if (dead < 1)
		{
			GameConstants.DEATH_MESSAGE = "EATEN ALIVE!";
			this.die();
		}
	}

}
