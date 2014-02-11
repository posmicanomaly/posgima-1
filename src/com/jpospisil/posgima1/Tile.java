package com.jpospisil.posgima1;

import java.util.ArrayList;

public class Tile {
	private ArrayList<Item> items;
	private String type;
	private boolean canPass;
	private int x, y;
	private boolean visible;
	private boolean dug;

	public Tile(int x, int y) {
		items = new ArrayList<Item>();
		this.x = x;
		this.y = y;
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public boolean getCanPass() {
		return canPass;
	}

	public boolean getDug() {
		return this.dug;
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

	public String getType() {
		return this.type;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setDug(boolean b) {
		dug = true;

	}

	public void setPassableFlag(boolean b) {
		this.canPass = b;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVisibility(boolean b) {
		this.visible = b;

	}

}
