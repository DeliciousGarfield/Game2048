package org.tianxiduo.game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
	private final static String gameTitle = "2048";
	private final static int gameWidth = 640;
	private final static int gameHeight = 480;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GameWindow gameWindow = new GameWindow();
		
		gameWindow.setTitle(gameTitle);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setSize(gameWidth, gameHeight);
		gameWindow.setVisible(true);
	}

}

class GameWindow extends JFrame {
	private GameContent gameContent = new GameContent();
	
	{
		getContentPane().add(gameContent);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gameContent.processKeyboardEvent(e);
			}
		});
	}
}

class GameContent extends JPanel {
	private final int space = 12;
	private final int edge = 60;
	private final int rows = 4;
	private final int columns = 4;
	private final int boardPosX = 0;
	private final int boardPosY = 0;
	private final int boardWidth;
	private final int boardHeight;
	private final Map<Integer, Color> colorTable = new HashMap<Integer, Color>();
	private int direction;
	private int[][] tiles = new int[4][4];
	Random rand = new Random();
	private boolean win = false;
	private final static int NUMBER_2048 = 2048;
	
	{
		boardWidth = rows * edge + (rows + 1) * space;
		boardHeight = boardWidth;
		
		colorTable.put(2, new Color(255, 255, 255));
		colorTable.put(4, new Color(255, 245, 238));
		colorTable.put(8, new Color(255, 235, 205));
		colorTable.put(16, new Color(255, 192, 203));
		colorTable.put(32, new Color(255, 153, 18));
		colorTable.put(64, new Color(255, 128, 0));
		colorTable.put(128, new Color(255, 215, 0));
		colorTable.put(256, new Color(255, 127, 80));
		colorTable.put(512, new Color(255, 125, 64));
		colorTable.put(1024, new Color(255, 99, 71));
		colorTable.put(2048, new Color(255, 0, 0));
	}
	
	private void drawBoard(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(boardPosX, boardPosY, boardWidth, boardHeight);
	}
	
	private void drawPlaceHolder(Graphics g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				drawTile(g, i, j, Color.gray);
			}
		}
	}
	
	private void drawTile(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(space + x * (space + edge), space + y * (space + edge), edge, edge);
	}
	
	private void drawNumber(Graphics g, int x, int y, Color color, int value) {
		String number = String.valueOf(value);
		
		Font font = new Font("Helvetica", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(color);
		
		int numberWidth = g.getFontMetrics().stringWidth(number);
		int numberHeight = g.getFontMetrics().getAscent() + g.getFontMetrics().getDescent();

		g.drawString(number, space + x * (space + edge) + edge / 2 - numberWidth / 2, space + y * (space + edge) + edge / 2 + numberHeight / 2);
	}
	
	private void drawNumberTile(Graphics g, int x, int y, Color backColor, Color foreColor, int value) {
		drawTile(g, x, y, backColor);
		drawNumber(g, x, y, foreColor, value);
	}
	
	private void clearTile(Graphics g, int x, int y) {
		drawTile(g, x, y, Color.gray);
	}
	
	private void drawNumberTileByValue(Graphics g, int x, int y, int value) {
		drawNumberTile(g, x, y, getTileBackColorByValue(value), getTileForeColorByValue(value), value);
	}
	
	private Color getTileForeColorByValue(int value) {
		if (value == 2 || value == 4)
			return Color.black;
		else 
			return Color.white;
	}
	
	private Color getTileBackColorByValue(int value) {
		return (Color)colorTable.get(value);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		switch (direction) {
		case KeyEvent.VK_LEFT:
			moveLeft(g);
			drawNewRandomTile(g);
			break;
		case KeyEvent.VK_RIGHT:
			moveRight(g);
			drawNewRandomTile(g);
			break;
		case KeyEvent.VK_UP:
			moveUp(g);
			drawNewRandomTile(g);
			break;
		case KeyEvent.VK_DOWN:
			moveDown(g);
			drawNewRandomTile(g);
			break;
		default:
			drawBoard(g);
			drawPlaceHolder(g);
			drawGameStartTiles(g);
		}
	}
	
	void processKeyboardEvent(KeyEvent e) {
		if (!win) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
				direction = e.getKeyCode();
				repaint();
			}
		}
	}
	
	private void moveLeft(Graphics g) {
		for (int i = 0; i < rows; i++) {
			int n = 0;
			int prev = 0;
			for (int j = 0; j < columns; j++) {
				if (tiles[i][j] != 0) {
					if (tiles[i][j] != prev) {
						n++;
						prev = tiles[i][j];
						updateTileNumberByValue(i, n - 1, tiles[i][j]);
						drawNumberTileByValue(g, n - 1, i, tiles[i][j]);
					}
					else {
						prev = 0;
						updateTileNumberByValue(i, n - 1, tiles[i][j] * 2);
						drawNumberTileByValue(g, n - 1, i, tiles[i][j] * 2);
					}
				}
			}
			
			for (int k = n; k < columns; k++) {
				tiles[i][k] = 0;
				clearTile(g, k, i);
			}
		}
	}
	
	private void moveRight(Graphics g) {
		for (int i = 0; i < rows; i++) {
			int n = 0;
			int prev = 0;
			for (int j = columns - 1; j >= 0; j--) {
				if (tiles[i][j] != 0) {
					if (tiles[i][j] != prev) {
						n++;
						prev = tiles[i][j];
						updateTileNumberByValue(i, columns - n, tiles[i][j]);
						drawNumberTileByValue(g, columns - n, i, tiles[i][j]);
					}
					else {
						prev = 0;
						updateTileNumberByValue(i, columns - n, tiles[i][j] * 2);
						drawNumberTileByValue(g, columns - n, i, tiles[i][j] * 2);
					}
				}
			}
			
			for (int k = 0; k < columns - n; k++) {
				tiles[i][k] = 0;
				clearTile(g, k, i);
			}
		}
	}
	
	private void moveUp(Graphics g) {
		for (int j = 0; j < columns; j++) {
			int n = 0;
			int prev = 0;
			for (int i = 0; i < rows; i++) {
				if (tiles[i][j] != 0) {
					if (tiles[i][j] != prev) {
						n++;
						prev = tiles[i][j];
						updateTileNumberByValue(n - 1, j, tiles[i][j]);
						drawNumberTileByValue(g, j, n - 1, tiles[i][j]);
					}
					else {
						prev = 0;
						updateTileNumberByValue(n - 1, j, tiles[i][j] * 2);
						drawNumberTileByValue(g, j, n - 1, tiles[i][j] * 2);
					}
				}
			}
			
			for (int k = n; k < rows; k++) {
				tiles[k][j] = 0;
				clearTile(g, j, k);
			}
		}
	}
	
	private void moveDown(Graphics g) {
		for (int j = 0; j < columns; j++) {
			int n = 0;
			int prev = 0;
			for (int i = rows - 1; i >= 0; i--) {
				if (tiles[i][j] != 0) {
					if (tiles[i][j] != prev) {
						n++;
						prev = tiles[i][j];
						updateTileNumberByValue(rows - n, j, tiles[i][j]);
						drawNumberTileByValue(g, j, rows - n, tiles[i][j]);
					}
					else {
						prev = 0;
						updateTileNumberByValue(rows - n, j, tiles[i][j] * 2);
						drawNumberTileByValue(g, j, rows - n, tiles[i][j] * 2);
					}
				}
			}
			
			for (int k = 0; k < rows - n; k++) {
				tiles[k][j] = 0;
				clearTile(g, j, k);
			}
		}
	}
	
	private void drawGameStartTiles(Graphics g) {
		int i = rand.nextInt(4);
		int j = rand.nextInt(4);
		updateTileNumberByValue(j, i, 2);
		drawNumberTileByValue(g, i, j, 2);
		
		int n = generateNewTileNumber();
		i = rand.nextInt(4);
		j = rand.nextInt(4);
		updateTileNumberByValue(j, i, n);
		drawNumberTileByValue(g, i, j, n);
	}
	
	private void drawNewRandomTile(Graphics g) {
		int empty = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (tiles[i][j] == 0) {
					empty++;
				}
			}
		}
		
		if (empty == 0)
			return;

		int newTileIndex = rand.nextInt(empty) + 1;
		
		int newTileX = 0;
		int newTileY = 0;
		empty = 0;
		outer:
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (tiles[i][j] == 0) {
					empty++;
					if (empty == newTileIndex) {
						newTileX = j;
						newTileY = i;
						break outer;
					}
				}
			}
		}
		
		int n = generateNewTileNumber();
		
		updateTileNumberByValue(newTileY, newTileX, n);
		drawNumberTileByValue(g, newTileX, newTileY, n);
	}

	private int generateNewTileNumber() {
		int n;
		if (rand.nextInt(10) >= 7)
			n = 4;
		else
			n = 2;
		return n;
	}
	
	private void updateTileNumberByValue(int x, int y, int value) {
		tiles[x][y] = value;
		if (value == NUMBER_2048)
			win = true;
	}
}
