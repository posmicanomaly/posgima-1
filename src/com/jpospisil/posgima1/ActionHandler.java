package com.jpospisil.posgima1;

import java.util.ArrayList;

public class ActionHandler
{

	private ArrayList<Boolean>	actions;
	private boolean				mine, buildRoad, eat, dig, swim, buildHouse,
			buildFarm, sleep, increaseMapSize, decreaseMapSize, regenerateMap;

	public ActionHandler()
	{

		this.mine = false;
		this.buildRoad = false;
		this.eat = false;
		this.dig = false;
		this.swim = false;
		this.buildHouse = false;
		this.buildFarm = false;
		this.sleep = false;
		this.increaseMapSize = false;
		this.decreaseMapSize = false;
		this.regenerateMap = false;
	}

	public ArrayList<Boolean> getActions()
	{
		return actions;
	}

	public boolean isBuildFarm()
	{
		return buildFarm;
	}

	public boolean isBuildHouse()
	{
		return buildHouse;
	}

	public boolean isBuildRoad()
	{
		return buildRoad;
	}

	public boolean isDecreaseMapSize()
	{
		return decreaseMapSize;
	}

	public boolean isDig()
	{
		return dig;
	}

	public boolean isEat()
	{
		return eat;
	}

	public boolean isIncreaseMapSize()
	{
		return increaseMapSize;
	}

	public boolean isMine()
	{
		return mine;
	}

	public boolean isRegenerateMap()
	{
		return regenerateMap;
	}

	public boolean isSleep()
	{
		return sleep;
	}

	public boolean isSwim()
	{
		return swim;
	}

	public void setActions(ArrayList<Boolean> actions)
	{
		this.actions = actions;
	}

	public void setBuildFarm(boolean buildFarm)
	{
		this.buildFarm = buildFarm;
	}

	public void setBuildHouse(boolean buildHouse)
	{
		this.buildHouse = buildHouse;
	}

	public void setBuildRoad(boolean buildRoad)
	{
		this.buildRoad = buildRoad;
	}

	public void setDecreaseMapSize(boolean decreaseMapSize)
	{
		this.decreaseMapSize = decreaseMapSize;
	}

	public void setDig(boolean dig)
	{
		this.dig = dig;
	}

	public void setEat(boolean eat)
	{
		this.eat = eat;
	}

	public void setIncreaseMapSize(boolean increaseMapSize)
	{
		this.increaseMapSize = increaseMapSize;
	}

	public void setMine(boolean mine)
	{
		this.mine = mine;
	}

	public void setRegenerateMap(boolean regenerateMap)
	{
		this.regenerateMap = regenerateMap;
	}

	public void setSleep(boolean sleep)
	{
		this.sleep = sleep;
	}

	public void setSwim(boolean swim)
	{
		this.swim = swim;
	}

}
