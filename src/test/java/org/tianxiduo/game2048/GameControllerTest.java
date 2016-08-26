package org.tianxiduo.game2048;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {
  private GameContentConfig gameContentConfig;
  private GameController gameController;

  @Before
  public void setUp() {
    gameContentConfig = new GameContentConfig();
    gameContentConfig.space = 12;
    gameContentConfig.edge = 60;
    gameContentConfig.rows = 4;
    gameContentConfig.columns = 4;
    gameContentConfig.boardPosX = 0;
    gameContentConfig.boardPosY = 0;
    gameContentConfig.boardWidth = gameContentConfig.rows * gameContentConfig.edge + (gameContentConfig.rows + 1) * gameContentConfig.space;
    gameContentConfig.boardHeight = gameContentConfig.boardWidth;
    
    gameController = new GameController();
    gameController.setUpGameController(gameContentConfig);
  }

  @Test
  public void testLeftwardTwoPairsTileMerge() {
    gameController.setTileNumber(0, 0, 2);
    gameController.setTileNumber(0, 1, 2);
    gameController.setTileNumber(0, 2, 2);
    gameController.setTileNumber(0, 3, 2);
    gameController.moveLeft();

    assertEquals(gameController.getTileNumber(0, 0), 4);
    assertEquals(gameController.getTileNumber(0, 1), 4);
    assertEquals(gameController.getTileNumber(0, 2), 0);
    assertEquals(gameController.getTileNumber(0, 3), 0);
  }

  @Test
  public void testGameWin() {
    assertFalse(gameController.isWin());
    gameController.setTileNumber(0, 0, 1024);
    gameController.setTileNumber(0, 1, 1024);
    gameController.moveLeft();

    assertEquals(gameController.getTileNumber(0, 0), 2048);
    assertTrue(gameController.isWin());
  }

  @After
  public void tearDown() {
    gameController = null;
  }

}
