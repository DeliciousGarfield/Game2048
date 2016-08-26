package org.tianxiduo.game2048;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class GameContentConfig {
  public int space;
  public int edge;
  public int rows;
  public int columns;
  public int boardPosX;
  public int boardPosY;
  public int boardWidth;
  public int boardHeight;
  public Map<Integer, Color> colorTable = new HashMap<Integer, Color>();
  public int[][] tiles = new int[4][4];
}