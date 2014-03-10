package com.jpospisil.posgima1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

public class SFMLASCIIRender
{

	private class ASCIIRenderGlyph
	{
		private boolean	backgroundRequired;
		private String	backgroundType;
		private String	type;
		private String	glyph;
		private Color	color;

		public ASCIIRenderGlyph(String type, String glyph, Color color)
		{
			this.type = type;
			this.glyph = glyph;
			this.color = color;
			backgroundRequired = false;
			backgroundType = null;
		}

		public String getBackgroundType()
		{
			return backgroundType;
		}

		public Color getColor()
		{
			return color;
		}

		public String getGlyph()
		{
			return glyph;
		}

		public String getType()
		{
			return type;
		}

		public boolean isBackgroundRequired()
		{
			return backgroundRequired;
		}

		public void setBackgroundRequired(boolean backgroundRequired)
		{
			this.backgroundRequired = backgroundRequired;
		}

		public void setBackgroundType(String backgroundType)
		{
			this.backgroundType = backgroundType;
		}

		public void setColor(Color color)
		{
			this.color = color;
		}

		public void setGlyph(String glyph)
		{
			this.glyph = glyph;
		}

		public void setType(String type)
		{
			this.type = type;
		}

	}

	private Font						defaultFont;
	private int							defaultFontSize;
	private HashMap<String, Color>		tileTypeColorMap;

	public static String				GRASS_GLYPH		= "\u2591";					// "\u00B7";
																					// //middle
																					// dot
	// unicode
	private String						PLAYER_GLYPH	= "@";
	private String						MOUNTAIN_GLYPH	= "\u25B2";				// triangle
	private String						FOREST_GLYPH	= "&";
	private String						WATER_GLYPH		= "\u2591";
	private String						LAVA_GLYPH		= "=";
	private String						HILL_GLYPH		= "\u0361";
	private String						ROAD_GLYPH		= "\u2588";
	private String						DESERT_GLYPH	= "\u2591";
	private String						DUG_GLYPH		= "\u25A0";
	private String						HOUSE_GLYPH		= "\u06E9";
	private String						FARM_GLYPH		= "\u25A1";

	private String						ERROR_GLYPH		= "?";

	private Color						GRASS_COLOR		= new Color(0, 255, 0,
																100);
	// public static Color MOUNTAIN_COLOR = Color.add(Color.BLACK, Color.WHITE);
	private static Color				MOUNTAIN_COLOR	= new Color(255, 255,
																255, 100);
	private static Color				FOREST_COLOR	= new Color(30, 180,
																50, 255);
	private static Color				WATER_COLOR		= Color.BLUE;
	private static Color				LAVA_COLOR		= Color.RED;
	private static Color				HILL_COLOR		= new Color(255, 255,
																0, 120);
	private static Color				DESERT_COLOR	= new Color(200, 200, 0);
	private static Color				ERROR_COLOR		= Color.WHITE;
	private static Color				ROAD_COLOR		= new Color(80, 47, 15);
	private static Color				DUG_COLOR		= new Color(139, 119,
																101, 125);
	private static Color				HOUSE_COLOR		= Color.WHITE;
	private static Color				PLAYER_COLOR	= Color.MAGENTA;

	private static Color				FARM_COLOR		= Color.GREEN;
	private SFMLRenderWindow			window;

	private Text						text;

	private ArrayList<ASCIIRenderGlyph>	renderGlyphs;

	public SFMLASCIIRender(SFMLRenderWindow window)
	{
		this.window = window;
		this.renderGlyphs = this.buildRenderGlyphArray();
		this.setRenderGlyphBackgrounds();
		this.tileTypeColorMap = this.buildTileTypeColorMap();

		this.defaultFont = GameConstants.FONTS.getDefaultFont();
		this.defaultFontSize = GameConstants.DEFAULT_FONT_SIZE;
	}

	private ArrayList<ASCIIRenderGlyph> buildRenderGlyphArray()
	{
		ArrayList<ASCIIRenderGlyph> array = new ArrayList<ASCIIRenderGlyph>();
		array.add(new ASCIIRenderGlyph("player", this.PLAYER_GLYPH,
				this.PLAYER_COLOR));

		array.add(new ASCIIRenderGlyph("grass", this.GRASS_GLYPH,
				this.GRASS_COLOR));
		array.add(new ASCIIRenderGlyph("mountain", this.MOUNTAIN_GLYPH,
				this.MOUNTAIN_COLOR));
		array.add(new ASCIIRenderGlyph("forest", this.FOREST_GLYPH,
				this.FOREST_COLOR));
		array.add(new ASCIIRenderGlyph("water", this.WATER_GLYPH,
				this.WATER_COLOR));
		array.add(new ASCIIRenderGlyph("hill", this.HILL_GLYPH, this.HILL_COLOR));
		array.add(new ASCIIRenderGlyph("desert", this.DESERT_GLYPH,
				this.DESERT_COLOR));
		array.add(new ASCIIRenderGlyph("error", this.ERROR_GLYPH,
				this.ERROR_COLOR));
		array.add(new ASCIIRenderGlyph("road", this.ROAD_GLYPH, this.ROAD_COLOR));
		array.add(new ASCIIRenderGlyph("dug", this.DUG_GLYPH, this.DUG_COLOR));
		array.add(new ASCIIRenderGlyph("house", this.HOUSE_GLYPH,
				this.HOUSE_COLOR));
		array.add(new ASCIIRenderGlyph("farm", this.FARM_GLYPH, this.FARM_COLOR));
		array.add(new ASCIIRenderGlyph("npc", "n", Color.WHITE));
		return array;
	}

	private HashMap<String, Color> buildTileTypeColorMap()
	{
		HashMap<String, Color> colorMap = new HashMap<String, Color>();
		colorMap.put("grass", this.GRASS_COLOR);
		colorMap.put("mountain", this.MOUNTAIN_COLOR);
		colorMap.put("forest", this.FOREST_COLOR);
		colorMap.put("water", this.WATER_COLOR);
		colorMap.put("hill", this.HILL_COLOR);
		colorMap.put("desert", this.DESERT_COLOR);
		colorMap.put("error", this.ERROR_COLOR);
		colorMap.put("road", this.ROAD_COLOR);
		colorMap.put("dug", this.DUG_COLOR);
		colorMap.put("house", this.HOUSE_COLOR);
		colorMap.put("farm", this.FARM_COLOR);
		return colorMap;
	}

	public void deathScreen(Player player)
	{
		window.getRenderWindow().setView(window.getWorldView());

		text = new Text(GameConstants.DEATH_MESSAGE + "\nres(P)awn",
				this.defaultFont, this.defaultFontSize * 4);
		text.setPosition(window.getWorldView().getCenter());
		text.setColor(Color.WHITE);
		window.getRenderWindow().draw(text);

	}

	// Returns a SFML Color based on the tile's type
	private Color determineColorFromType(String type)
	{
		Iterator<String> iterator = this.tileTypeColorMap.keySet().iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			if (key == type)
			{
				return tileTypeColorMap.get(key);
			}
		}
		return this.ERROR_COLOR;
	}

	private boolean drawBackgroundGlyph(String type, Tile tile)
	{
		window.getRenderWindow().setView(window.getWorldView());
		ASCIIRenderGlyph g = this.getRenderGlyphFromType(type);
		if (g != null)
		{
			text.setString(g.getGlyph());
			text.setColor(g.getColor());
			text.setPosition(tile.getX(), tile.getY());
			window.getRenderWindow().draw(text);
			return true;
		}
		return false;
	}

	private void drawDugGlyph(Tile tile)
	{
		window.getRenderWindow().setView(window.getWorldView());
		text.setString(this.DUG_GLYPH);
		text.setColor(this.determineColorFromType("dug"));
		text.setPosition(tile.getX(), tile.getY());
		window.getRenderWindow().draw(text);
	}

	private void drawMapGlyphs(GameMap gameMap, Player player)
	{
		window.getRenderWindow().setView(window.getWorldView());

		text = new Text(" ", this.defaultFont, this.defaultFontSize);

		ArrayList<Tile> visibleTiles = gameMap.getTilesInRange(player,
				GameConstants.VISIBLE_LEFT, GameConstants.VISIBLE_RIGHT,
				GameConstants.VISIBLE_UP, GameConstants.VISIBLE_DOWN);

		for (int i = 0; i < visibleTiles.size(); i++)
		{
			Tile tile = visibleTiles.get(i);
			for (int j = 0; j < this.renderGlyphs.size(); j++)
			{
				ASCIIRenderGlyph g = this.renderGlyphs.get(j);
				if (tile.getType() == g.getType())
				{
					if (g.isBackgroundRequired())
					{
						this.drawBackgroundGlyph(g.getBackgroundType(), tile);
					}
					text.setString(g.getGlyph());
					text.setColor(g.getColor());
					text.setPosition(tile.getX(), tile.getY());
					window.getRenderWindow().draw(text);
					if (tile.getDug())
					{
						if (tile.getType() != "road"
								&& tile.getType() != "house")
						{
							this.drawDugGlyph(tile);
						}
					}

				}
			}
		}
	}

	private ASCIIRenderGlyph getRenderGlyphFromType(String type)
	{
		for (ASCIIRenderGlyph g : this.renderGlyphs)
		{
			if (g.getType() == type)
				return g;
		}
		return null;
	}

	public void renderAllNpc(ArrayList<Npc> npcArray)
	{
		for (Npc npc : npcArray)
		{
			
			renderNpc(npc);
		}
	}

	// Loops the glyphs and draws them all to the screen
	public void renderMap(GameMap gameMap, Player player)
	{
		this.drawMapGlyphs(gameMap, player);
		// this.getGlyphs(gameMap, player);
		// for(Text text : this.getGlyphs(gameMap))
		// window.getRenderWindow().draw(text);
	}

	public void renderNpc(Npc npc)
	{
		ASCIIRenderGlyph g = this.getRenderGlyphFromType("npc");

		if (g != null)
		{
			text = new Text(g.getGlyph(), this.defaultFont,
					this.defaultFontSize);
			text.setColor(g.getColor());
			Tile tile = npc.getCurrentTile();
			text.setPosition(tile.getX(), tile.getY());
			window.getRenderWindow().draw(text);
		}

	}

	public void renderPlayer(Player player)
	{
		ASCIIRenderGlyph g = this.getRenderGlyphFromType("player");
		if (g != null)
		{
			text = new Text(g.getGlyph(), this.defaultFont,
					this.defaultFontSize);
			text.setColor(g.getColor());
			text.setStyle(Text.BOLD);
			Tile tile = player.getCurrentTile();
			text.setPosition(tile.getX(), tile.getY());

			// hack for 16:9 horizontal tiles showing halves
			float windowCenterHack = (float) (tile.getX() - (GameConstants.WIDTH * .01 + 1));
			if (GameConstants.HEIGHT % 9 == 0)
			{
				window.getWorldView().setCenter(windowCenterHack, tile.getY());
			}
			else
				// 16:10 master race
				window.getWorldView().setCenter(tile.getX(), tile.getY());
			window.getRenderWindow().setView(window.getWorldView());
			window.getRenderWindow().draw(text);
		}

	}

	public void renderWin(GameMap gameMap)
	{
		for (Tile tile : gameMap.getTileArray())
		{
			for (Item item : tile.getItems())
			{
				if (item.getName() == "win")
				{
					Text text = new Text("X", this.defaultFont,
							this.defaultFontSize);
					text.setColor(this.PLAYER_COLOR);
					text.setPosition(tile.getX(), tile.getY());
					window.getRenderWindow().draw(text);
				}
			}
		}
	}

	private void setRenderGlyphBackgrounds()
	{
		for (ASCIIRenderGlyph g : this.renderGlyphs)
		{
			switch (g.getType())
			{
			case "forest":
				g.setBackgroundRequired(true);
				g.setBackgroundType("grass");
				break;
			case "hill":
				g.setBackgroundRequired(true);
				g.setBackgroundType("grass");
				break;
			case "house":
				g.setBackgroundRequired(true);
				g.setBackgroundType("grass");
				break;
			case "farm":
				g.setBackgroundRequired(true);
				g.setBackgroundType("grass");
				break;
			}
		}
	}

	public void winScreen(Player player)
	{
		window.getRenderWindow().setView(window.getWorldView());

		text = new Text("you win!" + "\nres(P)awn", this.defaultFont,
				this.defaultFontSize * 4);
		text.setPosition(window.getWorldView().getCenter());
		text.setColor(Color.WHITE);
		window.getRenderWindow().draw(text);

	}

}
