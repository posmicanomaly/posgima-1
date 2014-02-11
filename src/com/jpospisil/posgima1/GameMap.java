package com.jpospisil.posgima1;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameMap {
	private Tile tile;
	private ArrayList<Tile> tileArray;
	private ArrayList<String> rawArray;
	
	public GameMap()
	{
		MapGenerator mapGenerator = new MapGenerator(GameConstants.MAP_GENERATOR_COLS, GameConstants.MAP_GENERATOR_ROWS);
		this.rawArray = new ArrayList<String>();
		this.tileArray = new ArrayList<Tile>();
		this.rawArray = mapGenerator.getGameMapArray();
		//this.loadFromFile("resources/map.txt");
		this.setTilePositions();		
	}
	
	public boolean checkTerrainCollision(Tile tile)
	{
		if(tile == null)
			return false;
		if(tile.getCanPass() == false)
			return false;
		else
			return true;
	}
	public Tile getRandomFoodWorthyTile()
	{
		Random rand = new Random();
		int n = rand.nextInt(this.tileArray.size());		
		boolean foodPlaced = false;
		while(!foodPlaced)
		{
			String type = this.tileArray.get(n).getType();
			switch(type)
			{
			case "grass":
				foodPlaced = true;
				break;
				
			case "forest":
				foodPlaced = true;
				break;
				
			case "hill":
				foodPlaced = true;
				break;
				
			default:
				n = rand.nextInt(this.tileArray.size());
				break;				
			}			
		}		
		return this.tileArray.get(n);		
	}
	public Tile getRandomTileByType(String type)
	{
		Random rand = new Random();
		int n = rand.nextInt(this.tileArray.size());
		while(this.tileArray.get(n).getType() != type)
		{
			n = rand.nextInt(this.tileArray.size());
		}		
			return this.tileArray.get(n);		
	}
	
	public ArrayList<String> getRawArray() {
		return rawArray;
	}
	public ArrayList<Tile> getTileArray() {
		return tileArray;
	}
	
	public Tile getTileFromCoords(int x, int y) {
		for(Tile tile : tileArray) {
			if(tile.getX() == x && tile.getY() == y)
				return tile;
		}
		return null;	
	}
	public ArrayList<Tile> getTilesInRange(Player player, int left, int right, int up, int down)
	{
		ArrayList<Tile> visibleTiles = new ArrayList<Tile>();
		int limitNegX = player.getCurrentTile().getX() - (left * GameConstants.DEFAULT_FONT_SIZE);
		int limitPosX = player.getCurrentTile().getX() + (right * GameConstants.DEFAULT_FONT_SIZE);
		int limitNegY = player.getCurrentTile().getY() - (up * GameConstants.DEFAULT_FONT_SIZE);
		int limitPosY = player.getCurrentTile().getY() + (down * GameConstants.DEFAULT_FONT_SIZE);
		for(Tile tile : this.tileArray)
		{
			if((tile.getX() < limitPosX && tile.getX() > limitNegX) && (tile.getY() < limitPosY && tile.getY() > limitNegY))
			{
				tile.setVisibility(true);
				visibleTiles.add(tile);
			}
			else
				tile.setVisibility(false);
					
		}
		return visibleTiles;
		
	}
	
public boolean hasTileType(String type)
	{
		for(Tile tile : this.tileArray)
		{
			if(tile.getType() == type)
				return true;
		}
		return false;
	}
	
	//	private void loadFromStringArray(ArrayList<String> mapArray)
//	{
//		Scanner scanner = new Scanner(mapGenerator.getGameMap());
//		while (scanner.hasNextLine()) {
//		 rawArray.add(scanner.nextLine());		 
//		}
//		scanner.close();
//		GameConstants.printDebugMessage("Map Generator success");
//		
//	}
	//Loads a simple text file format into rawArray
	private void loadFromFile(String fileName)
	{
		try
		{
			Scanner in = new Scanner(new FileReader(fileName));
			while(in.hasNextLine())
			{
				rawArray.add(in.nextLine());
			}			
			in.close();
			if(GameConstants.DEBUG)
				GameConstants.printDebugMessage(fileName + " loaded ok");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			if(GameConstants.DEBUG)
				GameConstants.printDebugMessage(fileName + " failed to load");			
		}
	}
	
	private void setTilePassableFlag()
	{
		switch(tile.getType())
		{
		case "mountain":
			tile.setPassableFlag(false);
			break;
			
		case "water":
			tile.setPassableFlag(true);
			break;
			
		default:
			tile.setPassableFlag(true);
			break;
		}
	}
	
	private void setTilePositions() {
		for(int y = 0; y < rawArray.size(); y++) {
			for(int x = 0; x < rawArray.get(y).length(); x++) {
				int X = x * GameConstants.DEFAULT_FONT_SIZE;
				int Y = y * GameConstants.DEFAULT_FONT_SIZE;
				char glyph = rawArray.get(y).charAt(x);
				tile = new Tile(X, Y);
				tile.setType(this.setTileType(glyph));
				this.setTilePassableFlag();
				tileArray.add(tile);
			}			
		}
	}
	
	private String setTileType(char c)
	{
		switch(c)
		{
		case '.':
			return "grass";
			
		case 'M':
			return "mountain";
			
		case '&':
			return "forest";
			
		case '=':
			return "water";
			
		case 'L':
			return "lava";
			
		case '~':
			return "hill";
			
		case 'S':
			return "desert";
			
		default:
			return "error";
		}
	}
}