package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.Random;

public class ActionHandler
{

	private ArrayList<Boolean>	actions;
	private Game				game;
	private boolean				mine, buildRoad, eat, dig, swim, buildHouse,
			buildFarm, sleep, increaseMapSize, decreaseMapSize, regenerateMap,
			moveNorth, moveSouth, moveWest, moveEast;

	
	public ActionHandler(Game game)
	{
		this.game = game;

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
		this.moveNorth = false;
		this.moveSouth = false;
		this.moveWest = false;
		this.moveEast = false;
		actions = new ArrayList<Boolean>();
		
		actions.add(mine);
		actions.add(buildRoad);
		actions.add(eat);
		actions.add(dig);
		actions.add(swim);
		actions.add(buildHouse);
		actions.add(buildFarm);
		actions.add(sleep);
		actions.add(increaseMapSize);
		actions.add(decreaseMapSize);
		actions.add(regenerateMap);
		
	}
	
	public boolean isMoveNorth() {
		return moveNorth;
	}

	public void setMoveNorth(boolean moveNorth) {
		this.moveNorth = moveNorth;
	}

	public boolean isMoveSouth() {
		return moveSouth;
	}

	public void setMoveSouth(boolean moveSouth) {
		this.moveSouth = moveSouth;
	}

	public boolean isMoveWest() {
		return moveWest;
	}

	public void setMoveWest(boolean moveWest) {
		this.moveWest = moveWest;
	}

	public boolean isMoveEast() {
		return moveEast;
	}

	public void setMoveEast(boolean moveEast) {
		this.moveEast = moveEast;
	}

	public void processActions(Player player)
	{
		if (this.isMine())
		{
			this.doMine(player);			
		}

		if (this.isDig())
		{
			this.doDig(player);			
		}

		if (this.isBuildRoad())
		{
			this.doBuildRoad(player);
		}
		if (this.isBuildFarm())
		{
			this.doBuildFarm(player);
		}

		// TODO something about motivation stat, and being too lazy to build a
		// house
		if (this.isBuildHouse())
		{
			this.doBuildHouse(player);
			
		}
		if (this.isSleep())
		{
			this.doSleep(player);
			
		}

		if (this.isIncreaseMapSize())
		{
			this.doIncreaseMapSize();
			
		}

		if (this.isDecreaseMapSize())
		{
			this.doDecreaseMapSize();
			
		}

		if (this.isRegenerateMap())
		{
			this.doRegenerateMap();
			
		}
		if (this.isEat())
			this.doEat(player);
		if (this.isMoveNorth())
			this.doMove(player, "north");
		if (this.isMoveSouth())
			this.doMove(player, "south");
		if (this.isMoveEast())
			this.doMove(player, "east");
		if (this.isMoveWest())
			this.doMove(player, "west");
	}
	public void doEat(Player player)
	{
		player.eat();
		this.setEat(false);
	}
	public void doPlayerCombat(CombatData combatData, boolean silent)
	{
		Entity entity1 = combatData.getEntity1();
		Entity entity2 = combatData.getEntity2();
		Tile loc = combatData.getLocation();
		
		int rand = new Random().nextInt(2);
		
		int playerDamage = new Random().nextInt(10);
		int npcDamage = new Random().nextInt(10);
		
		if(rand == 0)
		{
			entity1.setHealth(entity1.getHealth() - npcDamage);
			
				this.game.ui.messages.add(entity2.getName() + " hit " + entity1.getName() + " for " + npcDamage + " points of damage\n");
			
			if(entity1.getHealth() < GameConstants.MINIMUM_HEALTH)
			{
				this.game.ui.messages.add(entity1.getName() + " was killed by " + entity2.getName() + "\n");
				((Player) entity1).die();
				loc.removeEntity(loc.getEntities().get(0));
				return;
			}
			
			entity2.setHealth(entity2.getHealth() - playerDamage);
			this.game.ui.messages.add(entity1.getName() + " hit " + entity2.getName() + " for " + playerDamage + " points of damage\n");
		}
		else
		{
			entity2.setHealth(entity2.getHealth() - playerDamage);
			this.game.ui.messages.add(entity1.getName() + " hit " + entity2.getName() + " for " + playerDamage + " points of damage\n");
			
			if(entity2.getHealth() < GameConstants.MINIMUM_HEALTH)
			{
				this.game.ui.messages.add(entity2.getName() + " was killed by " + entity1.getName() + "\n");
				((Npc) entity2).die(entity1);
				loc.removeEntity(loc.getEntities().get(0));
				return;
			}
			
			entity1.setHealth(entity1.getHealth() - npcDamage);
			this.game.ui.messages.add(entity2.getName() + " hit " + entity1.getName() + " for " + npcDamage + " points of damage\n");
		}		
	}
	public void doNpcMove(Npc npc, Player player)
	{
		String message = npc.moveTowards(player);
		if(message.equals("collision with entity"))
		{
			//this.doCombat(npc.combatData, true);
		}
	}
	public void doMove(Player player, String direction)
	{
		String message = player.move(direction);
		if(message.equals("collision with entity"))
		{
			this.doPlayerCombat(player.getCombatData(), false);
		}
		else if(!message.equals("no collisions"))
			this.game.ui.messages.add(message + "\n");
		
		//set all to false, because don't know which bool we came from
		this.setMoveEast(false);
		this.setMoveSouth(false);
		this.setMoveNorth(false);
		this.setMoveWest(false);
	}
	public void doRegenerateMap()
	{
		this.game.ui.messages.add("---GENERATING WORLD, THIS COULD TAKE A WHILE---\n");
		this.game.redrawAll();
		this.game.setNewGame(true);
		this.setRegenerateMap(false);
	}
	public void doDecreaseMapSize()
	{
		GameConstants.MAP_GENERATOR_COLS -= 20;
		GameConstants.MAP_GENERATOR_ROWS -= 20;
		GameConstants.resetSeedValues();
		this.game.ui.messages
				.add("Decreased map size, press (P) to generate new world!("
						+ GameConstants.MAP_GENERATOR_ROWS
						+ "x"
						+ GameConstants.MAP_GENERATOR_COLS + ")\n");
		GameConstants.RENDER_REQUIRED = true;
		this.setDecreaseMapSize(false);
	}
	public void doIncreaseMapSize()
	{
		GameConstants.MAP_GENERATOR_COLS += 20;
		GameConstants.MAP_GENERATOR_ROWS += 20;
		GameConstants.resetSeedValues();
		this.game.ui.messages
				.add("Increased map size, press (P) to generate new world!"
						+ GameConstants.MAP_GENERATOR_ROWS + "x"
						+ GameConstants.MAP_GENERATOR_COLS + ")\n");
		GameConstants.RENDER_REQUIRED = true;
		this.setIncreaseMapSize(false);
	}
	public void doMine(Player player)
	{
		if (!player.isMining())
		{
			this.game.ui.messages.add("Mine in which direction? (esc to cancel)\n");
			this.game.redrawAll();
			player.setMining(true);

		}
		if (player.isMining())
		{
			if (player.isMiningSuccess())
			{

				if (player.mine())
				{
					this.game.ui.messages
							.add("You mined through the mountain!\n");
					player.getLookingAt().setType("road");
					player.getLookingAt().setPassableFlag(true);
				}
				else
				{
					this.game.ui.messages.add("Too hungry to mine..\n");

				}
				this.setMine(false);
				player.setMining(false);

			}

		}
		GameConstants.RENDER_REQUIRED = true;
	}
	public void doSleep(Player player)
	{
		if (player.getCurrentTile().getType() != "house")
			return;
		String message = player.sleep();
		if (message == "sleeping")
		{
			this.game.ui.messages.add("Zzz\n");
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (message == "tooHungry")
		{
			this.game.ui.messages.add("Too hungry to sleep\n");
			this.setSleep(false);
		}
	}
	
	public void doBuildHouse(Player player)
	{
		if (player.getCurrentTile().getType() == "water"
				|| player.getCurrentTile().getType() == "house")
			return;
		
		if (!player.buildHouse())
		{
			this.game.ui.messages.add("Too hungry to build a house..\n");
			this.game.redrawAll();
		}
		else
		{
			this.game.ui.messages.add("You built a house\n");
			player.setMovedLastTurn(true);
		}
		this.setBuildHouse(false);
	}
	
	public void doBuildFarm(Player player)
	{
		if (player.getCurrentTile().getType() == "water")
			return;
		String message = player.buildFarm();
		if (message == "hungerError")
		{
			this.game.ui.messages.add("Too hungry to build a farm, lol\n");
			this.game.redrawAll();
		}
		else if (message == "noSeeds")
		{
			this.game.ui.messages.add("You need seeds to plant\n");
			this.game.redrawAll();
		}
		else if (message == "built")
		{
			this.game.ui.messages.add("You built a farm\n");
			player.setMovedLastTurn(true);
		}
		this.setBuildFarm(false);
	}

	public void doBuildRoad(Player player)
	{
		if (player.getCurrentTile().getType() == "water"
				|| player.getCurrentTile().getType() == "road"
				|| player.getCurrentTile().getType() == "house")
			return;
		if (!player.buildRoad())
		{
			this.game.ui.messages.add("Too hungry to make a road..\n");
			this.game.redrawAll();
		}
		else
		{
			this.game.ui.messages
					.add("You clear the terrain, creating a road\n");
			player.setMovedLastTurn(true);
		}
		this.setBuildRoad(false);
	}
	
	public void doDig(Player player)
	{
		if (player.getCurrentTile().getType() == "water")
			return;
		player.dig();
		this.setDig(false);
		player.setMovedLastTurn(true);
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
