package com.jpospisil.posgima1;

import java.util.Iterator;
import java.util.Random;

public class Player {

	private Tile currentTile;
	private GameMap currentMap;
	public boolean movedLastTurn;
	
	private String name;
	private boolean alive;
	private int moves;
	private int digCount;
	private int foodCount;
	private int hungerLevel;
	private int health;
	
	public Player() {
		currentTile = null;
		currentMap = null;
		
		this.name = "Player the playa hata";
		this.moves = 0;
		this.digCount = 0;
		this.foodCount = 0;
		this.hungerLevel = 0;
		this.health = 100;
		
		this.alive = true;
		// TODO Auto-generated constructor stub
	}
	public boolean isAlive()
	{
		return this.alive;
	}
	public void mine()
	{
		//SFMLUI.messages.add("You mined the mountain, clearing a path!\n");
		this.hungerLevel +=4;
		if(this.hungerLevel > 10)
			this.hungerLevel = 10;
	}
	private void die()
	{		
		this.alive = false;
		GameConstants.RENDER_REQUIRED = true;
	}
	public void swim()
	{
		Random random = new Random();
		int dead = random.nextInt(10);
		if(dead < 1)
		{
			GameConstants.DEATH_MESSAGE = "EATEN ALIVE!";
			this.die();
		}
	}
	public void eat()
	{
		if(this.getFoodCount() > 0)
		{
			this.hungerLevel -= 10;
			if(this.hungerLevel < 0)
				this.hungerLevel = 0;
			this.setFoodCount(this.getFoodCount() - 1);
			//SFMLUI.messages.add("You eat some food, you feel less hungry!\n");
			GameConstants.RENDER_REQUIRED = true;
		}
	}
	public void dig()
	{	
		
		
			for(Iterator<Item> item = this.currentTile.getItems().iterator(); item.hasNext(); )
			{
				Item i = item.next();
				if(i.getName() == "win")
				{
					//SFMLUI.messages.add("YOU WIN\n");
					GameConstants.won = true;
					item.remove();
					break;
				}
				else if(i.getName() == "food")
				{
					//SFMLUI.messages.add("Found some food!\n");
					this.setFoodCount(this.getFoodCount() + 1);
					item.remove();
					break;
				}
			}
			this.setMoves(this.getMoves() + 1);
			this.setDigCount(this.getDigCount() + 1);	
			this.currentTile.setDug(true);
		
	}
	
	public void setCurrentTile(Tile tile)
	{
		this.currentTile = tile;
	}
	
	public void setCurrentMap(GameMap currentMap)
	{
		this.currentMap = currentMap;
	}
	
	
	public void processPlayerRules()
	{
		if(!this.alive)
		{
			return;
		}
		if(this.getHealth() < GameConstants.MINIMUM_HEALTH)
		{
			GameConstants.DEATH_MESSAGE = "YOU STARVED!";
			this.die();
			return;
		}
		if(this.getCurrentTile().getType() != "road")
		{
			if(this.getHungerLevel() < GameConstants.MAXIMUM_HUNGER)
				this.setHungerLevel(this.getHungerLevel() + 1);			
		}
		if(this.getHungerLevel() >= GameConstants.MAXIMUM_HUNGER)
			this.setHealth(this.getHealth() - 1);
		
	}
	public boolean move(String direction)
	{
		Tile nextTile = null;
		switch(direction)
		{
		case "north":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX(), this.currentTile.getY() - GameConstants.DEFAULT_FONT_SIZE);
			break;
			
		case "south":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX(), this.currentTile.getY() + GameConstants.DEFAULT_FONT_SIZE);
			break;
			
		case "west":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX() - GameConstants.DEFAULT_FONT_SIZE, this.currentTile.getY());
			break;
			
		case "east":
			nextTile = currentMap.getTileFromCoords(this.currentTile.getX() + GameConstants.DEFAULT_FONT_SIZE, this.currentTile.getY());
			break;
			
		default:
			return false;
		}
		if(nextTile != null)	
		{
			if(currentMap.checkTerrainCollision(nextTile))
			{
			
				if(nextTile.getType() == "water")
					this.swim();
				this.currentTile = nextTile;
				this.setMovedLastTurn(true);
				this.setMoves(this.getMoves() + 1);
				return true;
			}
			else
			{
				//SFMLUI.messages.add("that " + nextTile.getType() + " hurt\n");
				return false;
			}
		}
		return false;
			
	}

	public Tile getCurrentTile()
	{
		return this.currentTile;
	}
	public GameMap getCurrentMap()
	{
		return this.currentMap;
	}

	public void setMovedLastTurn(boolean b)
	{
		this.movedLastTurn = b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getDigCount() {
		return digCount;
	}

	public void setDigCount(int digCount) {
		this.digCount = digCount;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isMovedLastTurn() {
		return movedLastTurn;
	}
	public boolean buildRoad() {
		if(this.getHungerLevel() > 5)
		{
			//
			return false;
		}
		this.getCurrentTile().setType("road");
		this.setHungerLevel(this.getHungerLevel() + 3);
		if(this.getHungerLevel() > 10)
			this.setHungerLevel(10);
		return true;
		
	}
	public boolean buildHouse() 
	{
		if(this.getHungerLevel() > 0)
		{
			//SFMLUI.messages.add("Too hungry to build a house..\n");
			return false;
		}
		else
		{
			this.getCurrentTile().setType("house");
			this.setHungerLevel(10);
			return true;
		}
	}
	public boolean sleep() {
		if(this.getHungerLevel() < 10)
		{
			this.setHealth(this.getHealth() + 1);
			this.setMovedLastTurn(true);
			return true;
		}
		return false;
		//else
			//SFMLUI.messages.add("Too hungry to sleep\n");
		
	}
	
}
