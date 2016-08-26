package org.tianxiduo.game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameContent extends JPanel {
  private GameContentConfig gameContentConfig;
  private GameController gameController;
  
  private int direction;
  Random rand = new Random();

  public GameContent() {

  }

  public GameContent(GameContentConfig config) {
    this();
    setUpGameContent(config);
  }

  public void setUpGameContent(GameContentConfig config) {
    gameContentConfig = config;
    
    gameController = new GameController();
    gameController.setUpGameController(config);
  }

  private void drawBoard(Graphics g) {
    g.setColor(Color.darkGray);
    g.fillRect(gameContentConfig.boardPosX, gameContentConfig.boardPosY,
        gameContentConfig.boardWidth, gameContentConfig.boardHeight);
  }

  private void drawPlaceHolder(Graphics g) {
    for (int i = 0; i < gameContentConfig.rows; i++) {
      for (int j = 0; j < gameContentConfig.columns; j++) {
        drawTile(g, i, j, Color.gray);
      }
    }
  }

  private void drawTile(Graphics g, int x, int y, Color color) {
    g.setColor(color);
    g.fillRect(gameContentConfig.space + x * (gameContentConfig.space + gameContentConfig.edge),
        gameContentConfig.space + y * (gameContentConfig.space + gameContentConfig.edge),
        gameContentConfig.edge, gameContentConfig.edge);
  }

  private void drawNumber(Graphics g, int x, int y, Color color, int value) {
    String number = String.valueOf(value);

    Font font = new Font("Helvetica", Font.BOLD, 24);
    g.setFont(font);
    g.setColor(color);

    int numberWidthOffset = g.getFontMetrics().stringWidth(number) / 2;
    int numberHeightOffset = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2;

    g.drawString(number,
        gameContentConfig.space + x * (gameContentConfig.space + gameContentConfig.edge)
            + gameContentConfig.edge / 2 - numberWidthOffset,
        gameContentConfig.space + y * (gameContentConfig.space + gameContentConfig.edge)
            + gameContentConfig.edge / 2 + numberHeightOffset);
  }

  private void drawNumberTile(Graphics g, int x, int y, Color backColor, Color foreColor,
      int value) {
    drawTile(g, x, y, backColor);
    drawNumber(g, x, y, foreColor, value);
  }

  private void clearTile(Graphics g, int x, int y) {
    drawTile(g, x, y, Color.gray);
  }

  private void drawNumberTileByValue(Graphics g, int x, int y, int value) {
    if (value == 0)
      clearTile(g, x, y);
    else
      drawNumberTile(g, x, y, getTileBackColorByValue(value), getTileForeColorByValue(value), value);
  }

  private Color getTileForeColorByValue(int value) {
    if (value == 2 || value == 4)
      return Color.black;
    else
      return Color.white;
  }

  private Color getTileBackColorByValue(int value) {
    return (Color) gameContentConfig.colorTable.get(value);
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
    if (!gameController.isWin()) {
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
  
  private void redrawTiles(Graphics g) {
    for (int i = 0; i < gameContentConfig.rows; i++) {
      for (int j = 0; j < gameContentConfig.columns; j++) {
        drawNumberTileByValue(g, j, i, gameContentConfig.tiles[i][j]);
      }
    }
  }

  private void moveLeft(Graphics g) {
    gameController.moveLeft();
    redrawTiles(g);
  }

  private void moveRight(Graphics g) {
    gameController.moveRight();
    redrawTiles(g);
  }

  private void moveUp(Graphics g) {
    gameController.moveUp();
    redrawTiles(g);
  }

  private void moveDown(Graphics g) {
    gameController.moveDown();
    redrawTiles(g);
  }

  private void drawGameStartTiles(Graphics g) {
    int i = rand.nextInt(4);
    int j = rand.nextInt(4);
    gameController.updateTileNumberByValue(j, i, 2);
    drawNumberTileByValue(g, i, j, 2);

    int n = generateNewTileNumber();
    i = rand.nextInt(4);
    j = rand.nextInt(4);
    gameController.updateTileNumberByValue(j, i, n);
    drawNumberTileByValue(g, i, j, n);
  }

  private void drawNewRandomTile(Graphics g) {
    int empty = 0;
    for (int i = 0; i < gameContentConfig.rows; i++) {
      for (int j = 0; j < gameContentConfig.columns; j++) {
        if (gameContentConfig.tiles[i][j] == 0) {
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
    outer: for (int i = 0; i < gameContentConfig.rows; i++) {
      for (int j = 0; j < gameContentConfig.columns; j++) {
        if (gameContentConfig.tiles[i][j] == 0) {
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

    gameController.updateTileNumberByValue(newTileY, newTileX, n);
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
}
