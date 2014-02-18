package com.jpospisil.posgima1;

import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

public class SFMLASCIIRender {

	private SFMLRenderWindow window;
	private Text text;

	public SFMLASCIIRender(SFMLRenderWindow window) {
		this.window = window;
	}

	public void deathScreen(Player player) {
		window.getRenderWindow().setView(window.getWorldView());

		text = new Text(GameConstants.DEATH_MESSAGE + "\nres(P)awn",
				GameConstants.FONTS.getDefaultFont(),
				GameConstants.DEFAULT_FONT_SIZE * 4);
		text.setPosition(window.getWorldView().getCenter());
		text.setColor(Color.WHITE);
		window.getRenderWindow().draw(text);

	}

	// Returns a SFML Color based on the tile's type
	private Color determineColorFromType(String type) {
		switch (type) {
		case "farm":
			return GameConstants.FARM_COLOR;
		case "house":
			return GameConstants.HOUSE_COLOR;
		case "dug":
			return GameConstants.DUG_COLOR;
		case "grass":
			return GameConstants.GRASS_COLOR;

		case "mountain":
			return GameConstants.MOUNTAIN_COLOR;

		case "forest":
			return GameConstants.FOREST_COLOR;

		case "water":
			return GameConstants.WATER_COLOR;

		case "lava":
			return GameConstants.LAVA_COLOR;

		case "hill":
			return GameConstants.HILL_COLOR;

		case "road":
			return GameConstants.ROAD_COLOR;

		case "desert":
			return GameConstants.DESERT_COLOR;

		case "error":
			return GameConstants.ERROR_COLOR;
		}
		return null;
	}

	private void drawBackgroundGrassGlyph(Tile tile) {
		window.getRenderWindow().setView(window.getWorldView());
		text.setString(GameConstants.GRASS_GLYPH);
		text.setColor(this.determineColorFromType("grass"));
		text.setPosition(tile.getX(), tile.getY());
		window.getRenderWindow().draw(text);
	}

	private void drawDugGlyph(Tile tile) {
		window.getRenderWindow().setView(window.getWorldView());
		text.setString(GameConstants.DUG_GLYPH);
		text.setColor(this.determineColorFromType("dug"));
		text.setPosition(tile.getX(), tile.getY());
		window.getRenderWindow().draw(text);
	}

	// Returns all tile glyphs, which are defined in GameConstants based on
	// tile's type
	private void getGlyphs(GameMap gameMap, Player player) {
		window.getRenderWindow().setView(window.getWorldView());
		Font font = GameConstants.FONTS.getDefaultFont();
		int fontSize = GameConstants.DEFAULT_FONT_SIZE;
		text = new Text(" ", font, fontSize);
		int renderedCount = 0;
		ArrayList<Tile> visibleTiles = gameMap.getTilesInRange(player,
				GameConstants.VISIBLE_LEFT, GameConstants.VISIBLE_RIGHT,
				GameConstants.VISIBLE_UP, GameConstants.VISIBLE_DOWN);
		for (int i = 0; i < visibleTiles.size(); i++) {

			Tile tile = visibleTiles.get(i);
			switch (tile.getType()) {
			case "farm":
				this.drawBackgroundGrassGlyph(tile);
				text.setString(GameConstants.FARM_GLYPH);
				break;
				
			case "house":
				this.drawBackgroundGrassGlyph(tile);
				text.setString(GameConstants.HOUSE_GLYPH);
				break;
			case "grass":
				text.setString(GameConstants.GRASS_GLYPH);
				break;

			case "mountain":

				text.setString(GameConstants.MOUNTAIN_GLYPH);
				break;

			case "forest":
				this.drawBackgroundGrassGlyph(tile);
				text.setString(GameConstants.FOREST_GLYPH);
				break;

			case "water":
				text.setString(GameConstants.WATER_GLYPH);
				break;

			case "lava":
				text.setString(GameConstants.LAVA_GLYPH);
				break;

			case "hill":
				this.drawBackgroundGrassGlyph(tile);
				text.setString(GameConstants.HILL_GLYPH);
				break;

			case "road":
				text.setString(GameConstants.ROAD_GLYPH);
				break;

			case "desert":
				text.setString(GameConstants.DESERT_GLYPH);
				break;

			case "error":
				text.setString(GameConstants.ERROR_GLYPH);

			default:
				text.setString(GameConstants.ERROR_GLYPH);
				break;
			}
			text.setPosition(tile.getX(), tile.getY());
			text.setColor(this.determineColorFromType(tile.getType()));
			// tileGlyphs.add(text);
			window.getRenderWindow().draw(text);
			if (tile.getDug())
				if(tile.getType() != "road"	&& tile.getType() != "house")
					this.drawDugGlyph(tile);

			renderedCount++;

		}
		// if(GameConstants.DEBUG)
		// System.out.print("\nRendered: " + renderedCount + " t? " +
		// gameMap.getTileArray().size());
		//

	}

	// Loops the glyphs and draws them all to the screen
	public void renderMap(GameMap gameMap, Player player) {
		this.getGlyphs(gameMap, player);
		// for(Text text : this.getGlyphs(gameMap))
		// window.getRenderWindow().draw(text);
	}

	public void renderPlayer(Player player) {
		text = new Text("@", GameConstants.FONTS.getDefaultFont(),
				GameConstants.DEFAULT_FONT_SIZE);
		text.setColor(GameConstants.PLAYER_COLOR);
		text.setStyle(Text.BOLD);
		Tile tile = player.getCurrentTile();
		text.setPosition(tile.getX(), tile.getY());

		// hack for 16:9 horizontal tiles showing halves
		float windowCenterHack = (float) (tile.getX() - (GameConstants.WIDTH * .01 + 1));
		if (GameConstants.HEIGHT % 9 == 0) {
			window.getWorldView().setCenter(
					(float) (tile.getX() - (GameConstants.WIDTH * .01 + 1)),
					tile.getY());
		} else
			// 16:10 master race
			window.getWorldView().setCenter(tile.getX(), tile.getY());
		window.getRenderWindow().setView(window.getWorldView());
		window.getRenderWindow().draw(text);

	}

	public void renderWin(GameMap gameMap) {
		for (Tile tile : gameMap.getTileArray()) {
			for (Item item : tile.getItems()) {
				if (item.getName() == "win") {
					Text text = new Text("X",
							GameConstants.FONTS.getDefaultFont(),
							GameConstants.DEFAULT_FONT_SIZE);
					text.setColor(GameConstants.PLAYER_COLOR);
					text.setPosition(tile.getX(), tile.getY());
					window.getRenderWindow().draw(text);
				}
			}
		}
	}

	public void winScreen(Player player) {
		window.getRenderWindow().setView(window.getWorldView());

		text = new Text("you win!" + "\nres(P)awn",
				GameConstants.FONTS.getDefaultFont(),
				GameConstants.DEFAULT_FONT_SIZE * 4);
		text.setPosition(window.getWorldView().getCenter());
		text.setColor(Color.WHITE);
		window.getRenderWindow().draw(text);

	}

}
