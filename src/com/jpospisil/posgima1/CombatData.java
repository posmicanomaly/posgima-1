package com.jpospisil.posgima1;

public class CombatData {
	private Entity entity1;
	private Entity entity2;
	private Tile location;
	
	public CombatData(Entity entity1, Entity entity2, Tile location)
	{
		this.entity1 = entity1;
		this.entity2 = entity2;
		this.location = location;
	}
	
	public CombatData getCombatData()
	{
		return this;
	}
	
	public Entity getEntity2()
	{
		return this.entity2;
	}
	
	public Entity getEntity1()
	{
		return this.entity1;
	}
	
	public Tile getLocation()
	{
		return this.location;
	}
}
