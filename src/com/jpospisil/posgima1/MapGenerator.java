package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	public MapGenerator(int cols, int rows) {

		gameMapArray = new ArrayList<String>();

		this.setRandomSeedValues();
		this.setRandomMapStyle();

		this.gameMap = this.generateBaseMap(this.baseMapGlyph, cols, rows);
		this.gameMapArray = this.makeGameMapArray(this.gameMap);

		this.seedAllTerrain(this.makeTerrainHashMap());

		if (GameConstants.DEBUG)
			System.out.print("\nDone");
	}

	private String generateBaseMap(char baseGlyph, int cols, int rows) {
		if (GameConstants.DEBUG)
			System.out.print("\ngenerateBaseMap");
		String baseMap = "";
		char temp[];
		for (int i = 0; i < rows; i++) {
			temp = new char[cols];
			for (int j = 0; j < cols; j++) {
				temp[j] = baseGlyph;
			}
			baseMap += new String(temp);
			baseMap += "\n";
		}
		return baseMap;
	}

	public ArrayList<String> getGameMapArray() {
		return this.gameMapArray;
	}

	private ArrayList<String> makeGameMapArray(String gameMapString) {
		ArrayList<String> array = new ArrayList<String>();
		
		if (GameConstants.DEBUG)
			System.out.print("\nBuilding Map: Constructing gameMapArray");
		Scanner scanner = new Scanner(gameMapString);
		while (scanner.hasNextLine()) {
			array.add(scanner.nextLine());
		}
		scanner.close();
		
		return array;
	}

	private HashMap<String, Integer> makeTerrainHashMap() {
		HashMap<String, Integer> terrain = new HashMap<String, Integer>();
		
		
		terrain.put("M", this.mountainSeeds);
		terrain.put("~", this.hillSeeds);
		terrain.put("&", this.forestSeeds);
		terrain.put("=", this.waterSeeds);
		terrain.put("S", this.desertSeeds);

		return terrain;
	}

	private void seedAllTerrain(HashMap<String, Integer> terrainMap) {
		Iterator<String> iterator = terrainMap.keySet().iterator();
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (GameConstants.DEBUG)
				System.out.print("\nSeeding \"" + key + "\" "
						+ terrainMap.get(key));
			
			for (int i = 0; i < terrainMap.get(key); i++) {
				this.seedTerrain(key);
			}
		}
	}

	private void seedTerrain(String key) {		
		char charKey = key.charAt(0);		
		Random rand = new Random();
		
		int seedStrength = rand.nextInt(GameConstants.MAP_MAX_SEED_STRENGTH);
		int row = -1;
		int col = -1;
		
		boolean rowInRange = false;
		boolean colInRange = false;
		
		while (!rowInRange && !colInRange) {
			if (row < 0 || row > GameConstants.MAP_GENERATOR_ROWS) {
				row = rand.nextInt(GameConstants.MAP_GENERATOR_ROWS);
			} else {

				rowInRange = true;
			}

			if (col < 0 || col > GameConstants.MAP_GENERATOR_COLS) {

				col = rand.nextInt(GameConstants.MAP_GENERATOR_COLS);
			} else {

				colInRange = true;
			}

		}
		
		int seedSuccess = 0;
		while (seedSuccess < seedStrength) {

			char[] mapLineChars = gameMapArray.get(row).toCharArray();
			switch (mapLineChars[col]) {
			case 'M':
				if (charKey == '=')
					mapLineChars[col] = charKey;
				break;
			case '=':
				break;
			default:
				mapLineChars[col] = charKey;
			}

			gameMapArray.set(row, new String(mapLineChars));

			boolean spread = false;
			while (!spread) {
				int direction = rand.nextInt(5);
				switch (direction) {
				// Up Down Left Right
				case 0:
					if (row - 1 >= 0) {
						row -= 1;
						seedSuccess++;
						spread = true;
					}
					break;
				case 1:
					if (row + 1 < GameConstants.MAP_GENERATOR_ROWS) {
						row += 1;
						seedSuccess++;
						spread = true;
					}
					break;
				case 2:
					if (col - 1 >= 0) {
						col -= 1;
						seedSuccess++;
						spread = true;
					}
					break;
				case 3:
					if (col + 1 < GameConstants.MAP_GENERATOR_COLS) {
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

	private void setRandomMapStyle() {
		Random rand = new Random();

		int style = rand.nextInt(5);
		switch (style) {
		// mountain map
		case 0:
			if (GameConstants.DEBUG)
				System.out.println("\nmountain map");
			hillSeeds *= 2;
			waterSeeds *= .5f;
			forestSeeds *= .5f;
			mountainSeeds *= 2;
			this.baseMapGlyph = '.';
			break;

		// hill map
		case 1:
			if (GameConstants.DEBUG)
				System.out.println("\nhill map");
			hillSeeds *= 2;
			waterSeeds *= .5f;
			forestSeeds *= .5f;
			mountainSeeds *= .5f;
			this.baseMapGlyph = '.';
			break;

		// forest map
		case 2:
			if (GameConstants.DEBUG)
				System.out.println("\nforest map");
			hillSeeds *= .5f;
			waterSeeds *= .5f;
			forestSeeds *= 2;
			mountainSeeds *= .5f;
			this.baseMapGlyph = '.';
			break;

		// water map
		case -1:
			if (GameConstants.DEBUG)
				System.out.println("\nwater map");
			hillSeeds *= .5f;
			waterSeeds *= 2;
			forestSeeds *= .5f;
			mountainSeeds *= .5f;
			this.baseMapGlyph = '.';
			break;

		case 4:
			if (GameConstants.DEBUG)
				System.out.println("\ndesert map");
			desertSeeds *= 4;
			waterSeeds *= .25f;
			forestSeeds *= .25f;
			this.baseMapGlyph = '.';
			break;

		default:
			if (GameConstants.DEBUG)
				System.out.println("\nDefault");
			this.baseMapGlyph = '.';
			break;
		}
	}

	private void setRandomSeedValues() {
		Random rand = new Random();
		int MAX = GameConstants.MAP_FEATURE_SEED_MAX;

		waterSeeds = rand.nextInt(MAX);
		hillSeeds = rand.nextInt(MAX);
		mountainSeeds = rand.nextInt(MAX);
		forestSeeds = rand.nextInt(MAX);
		desertSeeds = rand.nextInt(MAX);
	}

	private void zz_addBoxRoom(char boundingGlyph, int x, int y, int height,
			int width) {
		for (int i = y; i < y + height; i++) {
			char mapLineChars[] = gameMapArray.get(i).toCharArray();

			for (int j = x; j < x + width; j++) {
				if (i == y || i == height - 1)// first row
				{
					mapLineChars[j] = boundingGlyph;
				}

				if (j == x || j == width - 1)// sides
				{
					mapLineChars[j] = boundingGlyph;
				}
			}
			gameMapArray.set(i, new String(mapLineChars));
		}
	}
}
