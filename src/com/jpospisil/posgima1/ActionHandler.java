package com.jpospisil.posgima1;

import java.util.ArrayList;

public class ActionHandler {

	private ArrayList<Boolean> actions;
	private boolean mine,
				   buildRoad,
				   eat,
				   dig,
				   swim,
				   buildHouse,
				   sleep,
				   increaseMapSize,
				   decreaseMapSize,
				   regenerateMap;
				   
	public ActionHandler() {
		this.actions = new ArrayList<Boolean>();
		this.mine = false;
		this.buildRoad = false;
		this.eat = false;
		this.dig = false;
		this.swim = false;
		this.buildHouse = false;
		this.sleep = false;
		this.increaseMapSize = false;
		this.decreaseMapSize = false;
		this.regenerateMap = false;
		
		this.actions.add(mine);
		this.actions.add(buildRoad);
		this.actions.add(eat);
		this.actions.add(swim);
		this.actions.add(buildHouse);
		this.actions.add(sleep);		
	}

	public ArrayList<Boolean> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Boolean> actions) {
		this.actions = actions;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isBuildRoad() {
		return buildRoad;
	}

	public void setBuildRoad(boolean buildRoad) {
		this.buildRoad = buildRoad;
	}

	public boolean isEat() {
		return eat;
	}

	public void setEat(boolean eat) {
		this.eat = eat;
	}

	public boolean isDig() {
		return dig;
	}

	public void setDig(boolean dig) {
		this.dig = dig;
	}

	public boolean isSwim() {
		return swim;
	}

	public void setSwim(boolean swim) {
		this.swim = swim;
	}

	public boolean isBuildHouse() {
		return buildHouse;
	}

	public void setBuildHouse(boolean buildHouse) {
		this.buildHouse = buildHouse;
	}

	public boolean isSleep() {
		return sleep;
	}

	public void setSleep(boolean sleep) {
		this.sleep = sleep;
	}

	public boolean isIncreaseMapSize() {
		return increaseMapSize;
	}

	public void setIncreaseMapSize(boolean increaseMapSize) {
		this.increaseMapSize = increaseMapSize;
	}

	public boolean isDecreaseMapSize() {
		return decreaseMapSize;
	}

	public void setDecreaseMapSize(boolean decreaseMapSize) {
		this.decreaseMapSize = decreaseMapSize;
	}

	public boolean isRegenerateMap() {
		return regenerateMap;
	}

	public void setRegenerateMap(boolean regenerateMap) {
		this.regenerateMap = regenerateMap;
	}

}
