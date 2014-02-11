package com.jpospisil.posgima1;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class MapGenerator {

	private int waterSeeds;
	private int hillSeeds;
	private int mountainSeeds;
	private int forestSeeds;
	private int desertSeeds;
	
	private char baseMapGlyph;
	
	private String gameMap;
	private ArrayList<String> gameMapArray;
	public MapGenerator(int cols, int rows) 
	{
	
		gameMapArray = new ArrayList<String>();
		Random rand = new Random();
		///////////////////////////////////
		waterSeeds = rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX);
		hillSeeds = rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX);
		mountainSeeds = rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX);
		forestSeeds = rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX);
		desertSeeds = rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX);
		
		int mapStyle = rand.nextInt(5);
		switch(mapStyle)
		{
			//mountain map
			case 0:
				if(GameConstants.DEBUG) System.out.println("\nmountain map");
				hillSeeds *= 2;
				waterSeeds *= .5f;
				forestSeeds *= .5f;
				mountainSeeds *= 2;
				this.baseMapGlyph = '.';
				break;
				
			//hill map
			case 1:
				if(GameConstants.DEBUG) System.out.println("\nhill map");
				hillSeeds *= 2;
				waterSeeds *= .5f;
				forestSeeds *= .5f;
				mountainSeeds *= .5f;
				this.baseMapGlyph = '.';
				break;
				
			//forest map
			case 2:
				if(GameConstants.DEBUG) System.out.println("\nforest map");
				hillSeeds *= .5f;
				waterSeeds *= .5f;
				forestSeeds *= 2;
				mountainSeeds *= .5f;
				this.baseMapGlyph = '.';
				break;
				
			//water map
			case -1:
				if(GameConstants.DEBUG) System.out.println("\nwater map");
				hillSeeds *= .5f;
				waterSeeds *= 2;
				forestSeeds *= .5f;
				mountainSeeds *= .5f;
				this.baseMapGlyph = '.';
				break;
				
			case 4:
				if(GameConstants.DEBUG) System.out.println("\ndesert map");
				desertSeeds *= 4;
				waterSeeds *= .25f;
				forestSeeds *= .25f;
				this.baseMapGlyph = '.';
				break;
				
			default:
				if(GameConstants.DEBUG) System.out.println("\nDefault");
				this.baseMapGlyph = '.';
				break;
					
		}
		gameMap = this.generateBaseMap(this.baseMapGlyph, cols, rows);
		
		///////////////////////////////////
		if(GameConstants.DEBUG)	System.out.print("\nBuilding Map: Constructing gameMapArray");
		Scanner scanner = new Scanner(gameMap);
		while (scanner.hasNextLine()) {
		 gameMapArray.add(scanner.nextLine());		 
		}
		scanner.close();
		
		///////////////////////////////////		
		if(GameConstants.DEBUG)	System.out.print("\nSeeding Water" + waterSeeds);
		for(int i = 0; i < waterSeeds; i++)
			this.seedTerrain('=');
		
		if(GameConstants.DEBUG)	System.out.print("\nSeeding Hills" + hillSeeds);
		for(int i = 0; i < hillSeeds; i++)
			this.seedTerrain('~');
		
		//for(int lavaSeeds = 0; lavaSeeds < rand.nextInt(GameConstants.MAP_FEATURE_SEED_MAX) * GameConstants.MAP_LAVA_FREQ_MOD; lavaSeeds++)
		//	this.seedTerrain('L');
		
		if(GameConstants.DEBUG)	System.out.print("\nSeeding Mountains" + mountainSeeds);
		for(int i = 0; i < mountainSeeds; i++)
			this.seedTerrain('M');
		
		if(GameConstants.DEBUG)	System.out.print("\nSeeding Forests" + forestSeeds);
		for(int i = 0; i < forestSeeds; i++)
			this.seedTerrain('&');
		
		if(GameConstants.DEBUG) System.out.print("\nSeeding Deserts" + desertSeeds);
		for(int i = 0; i < desertSeeds; i++)
			this.seedTerrain('S');
		
		
		//this.addBoxRoom('M', 0, 0, 50, 50);
		if(GameConstants.DEBUG) System.out.print("\nDone");
		
	}
	
	private void addBoxRoom(char boundingGlyph,int x, int y, int height, int width)
	{
		for(int i = y; i < y + height; i++)
		{
			char mapLineChars[] = gameMapArray.get(i).toCharArray();
		
			for(int j = x; j < x + width; j++)
			{				
				if(i == y || i == height - 1)//first row
				{				
					mapLineChars[j] = boundingGlyph;
				}
				
				if(j == x || j == width - 1)//sides
				{
					mapLineChars[j] = boundingGlyph;
				}				
			}
			gameMapArray.set(i, new String(mapLineChars));
		}
	}
	
	private String generateBaseMap(char baseGlyph, int cols, int rows)
	{
		if(GameConstants.DEBUG) System.out.print("\ngenerateBaseMap");
		String baseMap = "";
		char temp[];
		for(int i = 0; i < rows; i++)
		{
			temp = new char[cols];
			for(int j = 0; j < cols; j++)
			{
				//if(GameConstants.DEBUG)
				//	System.out.print("\n" + i + "/" + rows + " : " + j + "/" + cols);
				temp[j] = baseGlyph;
				//baseMap += temp;
				
			}
			baseMap += new String(temp);
			baseMap += "\n";
		}
		//return baseMap;
		return baseMap;
	}
	
	public ArrayList<String> getGameMapArray()
	{
		return this.gameMapArray;
	}

	private void seedTerrain(char seedGlyph)
	{
		//if(GameConstants.DEBUG)	System.out.print(".");
		Random rand = new Random();
		int seedStrength = rand.nextInt(GameConstants.MAP_MAX_SEED_STRENGTH);
		int row = -1;
		int col = -1;
		boolean rowInRange = false;
		boolean colInRange = false;
		while(!rowInRange && !colInRange)
		{			
			if(row < 0 || row > GameConstants.MAP_GENERATOR_ROWS)
			{
				row = rand.nextInt(GameConstants.MAP_GENERATOR_ROWS);
			}
			else
			{
					
				rowInRange = true;
			}
			
			
			if(col < 0 || col > GameConstants.MAP_GENERATOR_COLS)
			{
				
				col = rand.nextInt(GameConstants.MAP_GENERATOR_COLS);
			}
			else
			{
				
				colInRange = true;
			}
			
		}
		int seedSuccess = 0;
		while(seedSuccess < seedStrength)
		{
			
			char[] mapLineChars = gameMapArray.get(row).toCharArray();
			switch(mapLineChars[col])
			{
			case 'M':
				if(seedGlyph == '=')
					mapLineChars[col] = seedGlyph;
				break;
			case '=':
				break;
			default:
				mapLineChars[col] = seedGlyph;
			}
			
			
			gameMapArray.set(row, new String(mapLineChars));
			
			boolean spread = false;
			while(!spread)
			{
				int direction = rand.nextInt(5);
				switch(direction)
				{
				case 0:
					if(row - 1 >= 0)
					{
						row -= 1;
						seedSuccess++;
						spread = true;
					}
					break;

				case 1:
					if(row + 1 < GameConstants.MAP_GENERATOR_ROWS)
					{
						row += 1;
						seedSuccess++;
						spread = true;
					}
					break;

				case 2:
					if(col - 1 >= 0)
					{
						col -= 1;
						seedSuccess++;
						spread = true;
					}
					break;

				case 3:
					if(col + 1 < GameConstants.MAP_GENERATOR_COLS)
					{
						col += 1;
						seedSuccess++;
						spread = true;
					}
					break;
					
				case 4:
					seedSuccess++;
					spread = true;
					break;
					
				}
			}
		}
	}
}
