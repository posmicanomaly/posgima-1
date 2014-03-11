package com.jpospisil.posgima1;

public class Entity {
	protected Tile		currentTile;
	protected Tile		lookingAt;
	protected GameMap	currentMap;
	public boolean		movedLastTurn;

	protected String		name;
	protected boolean		alive;
	protected boolean		isMining;
	protected boolean		miningSuccess;
	protected int			moves;
	protected int			digCount;
	protected int			foodCount;
	protected int			seedCount;
	protected int			hungerLevel;
	protected int			health;
	protected CombatData  combatData;
	
	public CombatData getCombatData()
	{
		return this.combatData;
	}
	
	protected void combatWithEntity(Entity e, Tile location)
	{
		//System.out.println("Combat with " + this.getName() + " and " + e.getName() + " at tile: " + location.toString());
		this.combatData = new CombatData(this, e, location);			
	}
	public GameMap getCurrentMap()
	{
		return this.currentMap;
	}

	public Tile getCurrentTile()
	{
		return this.currentTile;
	}
	public int getDigCount()
	{
		return digCount;
	}

	public int getFoodCount()
	{
		return foodCount;
	}

	public int getHealth()
	{
		return health;
	}

	public int getHungerLevel()
	{
		return hungerLevel;
	}

	public Tile getLookingAt()
	{
		return lookingAt;
	}

	public int getMoves()
	{
		return moves;
	}

	public String getName()
	{
		return name;
	}

	public int getSeedCount()
	{
		return seedCount;
	}
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public void setCurrentMap(GameMap currentMap)
	{
		this.currentMap = currentMap;
	}

	public void setCurrentTile(Tile tile)
	{
		this.currentTile = tile;
	}

	public void setDigCount(int digCount)
	{
		this.digCount = digCount;
	}

	public void setFoodCount(int foodCount)
	{
		this.foodCount = foodCount;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public void setHungerLevel(int hungerLevel)
	{
		this.hungerLevel = hungerLevel;
	}

	public void setLookingAt(Tile lookingAt)
	{
		this.lookingAt = lookingAt;
	}

	public void setMining(boolean isMining)
	{
		this.isMining = isMining;
	}

	public void setMiningSuccess(boolean miningSuccess)
	{
		this.miningSuccess = miningSuccess;
	}

	public void setMovedLastTurn(boolean b)
	{
		this.movedLastTurn = b;
	}

	public void setMoves(int moves)
	{
		this.moves = moves;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSeedCount(int seedCount)
	{
		this.seedCount = seedCount;
	}
	
	public boolean isAlive()
	{
		return this.alive;
	}
}
