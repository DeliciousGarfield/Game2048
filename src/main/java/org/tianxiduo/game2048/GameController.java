package org.tianxiduo.game2048;

public class GameController {
  private GameContentConfig gameContentConfig;

  private boolean win = false;
  private final static int NUMBER_2048 = 2048;

  public GameController() {

  }

  public GameController(GameContentConfig config) {
    this();
    setUpGameController(config);
  }

  public void setUpGameController(GameContentConfig config) {
    gameContentConfig = config;
  }

  public void moveLeft() {
    for (int i = 0; i < gameContentConfig.rows; i++) {
      int n = 0;
      int prev = 0;
      for (int j = 0; j < gameContentConfig.columns; j++) {
        if (gameContentConfig.tiles[i][j] != 0) {
          if (gameContentConfig.tiles[i][j] != prev) {
            n++;
            prev = gameContentConfig.tiles[i][j];
            updateTileNumberByValue(i, n - 1, gameContentConfig.tiles[i][j]);
          } else {
            prev = 0;
            updateTileNumberByValue(i, n - 1, gameContentConfig.tiles[i][j] * 2);
          }
        }
      }

      for (int k = n; k < gameContentConfig.columns; k++) {
        gameContentConfig.tiles[i][k] = 0;
      }
    }
  }

  public void moveRight() {
    for (int i = 0; i < gameContentConfig.rows; i++) {
      int n = 0;
      int prev = 0;
      for (int j = gameContentConfig.columns - 1; j >= 0; j--) {
        if (gameContentConfig.tiles[i][j] != 0) {
          if (gameContentConfig.tiles[i][j] != prev) {
            n++;
            prev = gameContentConfig.tiles[i][j];
            updateTileNumberByValue(i, gameContentConfig.columns - n,
                gameContentConfig.tiles[i][j]);
          } else {
            prev = 0;
            updateTileNumberByValue(i, gameContentConfig.columns - n,
                gameContentConfig.tiles[i][j] * 2);
          }
        }
      }

      for (int k = 0; k < gameContentConfig.columns - n; k++) {
        gameContentConfig.tiles[i][k] = 0;
      }
    }
  }

  public void moveUp() {
    for (int j = 0; j < gameContentConfig.columns; j++) {
      int n = 0;
      int prev = 0;
      for (int i = 0; i < gameContentConfig.rows; i++) {
        if (gameContentConfig.tiles[i][j] != 0) {
          if (gameContentConfig.tiles[i][j] != prev) {
            n++;
            prev = gameContentConfig.tiles[i][j];
            updateTileNumberByValue(n - 1, j, gameContentConfig.tiles[i][j]);
          } else {
            prev = 0;
            updateTileNumberByValue(n - 1, j, gameContentConfig.tiles[i][j] * 2);
          }
        }
      }

      for (int k = n; k < gameContentConfig.rows; k++) {
        gameContentConfig.tiles[k][j] = 0;
      }
    }
  }

  public void moveDown() {
    for (int j = 0; j < gameContentConfig.columns; j++) {
      int n = 0;
      int prev = 0;
      for (int i = gameContentConfig.rows - 1; i >= 0; i--) {
        if (gameContentConfig.tiles[i][j] != 0) {
          if (gameContentConfig.tiles[i][j] != prev) {
            n++;
            prev = gameContentConfig.tiles[i][j];
            updateTileNumberByValue(gameContentConfig.rows - n, j, gameContentConfig.tiles[i][j]);
          } else {
            prev = 0;
            updateTileNumberByValue(gameContentConfig.rows - n, j,
                gameContentConfig.tiles[i][j] * 2);
          }
        }
      }

      for (int k = 0; k < gameContentConfig.rows - n; k++) {
        gameContentConfig.tiles[k][j] = 0;
      }
    }
  }

  public void updateTileNumberByValue(int x, int y, int value) {
    gameContentConfig.tiles[x][y] = value;
    if (value == NUMBER_2048)
      win = true;
  }
  
  public boolean isWin() {
    return win;
  }

  public void setTileNumber(int i, int j, int value) {
    gameContentConfig.tiles[i][j] = value;
  }

  public int getTileNumber(int i, int j) {
    return gameContentConfig.tiles[i][j];
  }
}
