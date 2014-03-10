package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.Iterator;

public class Tile
{
	private ArrayList<Entity> entities;
	private ArrayList<Item>	items;
	private String			type;
	private boolean			canPass;
	private int				x, y;
	private boolean			visible;
	private boolean			dug;
	private int				dugDuration;

	public Tile(int x, int y)
	{
		entities = new ArrayList<Entity>();
		items = new ArrayList<Item>();
		this.x = x;
		this.y = y;
	}

	public ArrayList<Entity> getEntities()
	{
		return this.entities;
	}
	public void addEntity(Entity e)
	{
		this.entities.add(e);
	}
	public void removeEntity(Entity e)
	{
		Iterator<Entity> it = this.entities.iterator();
		while(it.hasNext())
		{
			Entity ent = it.next();
			if(ent.equals(e))
			{
				//System.out.println("removed " + it.toString());
			
				it.remove();
			}
		}
	}
	public void addItem(Item item)
	{
		items.add(item);
	}

	public void clearItems()
	{
		this.items = new ArrayList<Item>();
	}

	public boolean getCanPass()
	{
		return canPass;
	}

	public boolean getDug()
	{
		return this.dug;
	}

	public int getDugDuration()
	{
		return dugDuration;
	}

	public ArrayList<Item> getItems()
	{
		return this.items;
	}

	public String getType()
	{
		return this.type;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setCanPass(boolean canPass)
	{
		this.canPass = canPass;
	}

	public void setDug(boolean b)
	{
		dug = b;
		if (b == true)
		{
			this.dugDuration = 0;
		}
		if (b == false)
		{
			this.dugDuration = 0;
		}
	}

	public void setDugDuration(int dugDuration)
	{
		this.dugDuration = dugDuration;
	}

	public void setItems(ArrayList<Item> items)
	{
		this.items = items;
	}

	public void setPassableFlag(boolean b)
	{
		this.canPass = b;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setVisibility(boolean b)
	{
		this.visible = b;

	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

}
