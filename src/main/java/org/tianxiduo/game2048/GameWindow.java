package org.tianxiduo.game2048;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
  private GameContentConfig gameContentConfig = new GameContentConfig();
  
  public void buildGameContentConfig() {
    gameContentConfig.space = 12;
    gameContentConfig.edge = 60;
    gameContentConfig.rows = 4;
    gameContentConfig.columns = 4;
    gameContentConfig.boardPosX = 0;
    gameContentConfig.boardPosY = 0;
    gameContentConfig.boardWidth = gameContentConfig.rows * gameContentConfig.edge + (gameContentConfig.rows + 1) * gameContentConfig.space;
    gameContentConfig.boardHeight = gameContentConfig.boardWidth;
    
    gameContentConfig.colorTable.put(2, new Color(239, 227, 214));
    gameContentConfig.colorTable.put(4, new Color(238, 226, 205));
    gameContentConfig.colorTable.put(8, new Color(239, 178, 123));
    gameContentConfig.colorTable.put(16, new Color(247, 150, 99));
    gameContentConfig.colorTable.put(32, new Color(241, 127, 93));
    gameContentConfig.colorTable.put(64, new Color(243, 94, 61));
    gameContentConfig.colorTable.put(128, new Color(238, 206, 115));
    gameContentConfig.colorTable.put(256, new Color(237, 204, 97));
    gameContentConfig.colorTable.put(512, new Color(238, 198, 82));
    gameContentConfig.colorTable.put(1024, new Color(238, 198, 66));
    gameContentConfig.colorTable.put(2048, new Color(244, 196, 12));
  }

  public GameWindow() {
    buildGameContentConfig();
    
    final GameContent gameContent = new GameContent(gameContentConfig);
    gameContent.setUpGameContent(gameContentConfig);
    
    getContentPane().add(gameContent);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        gameContent.processKeyboardEvent(e);
      }
    });
  }

  public GameWindow(GameWindowConfig config) {
    this();
    setUpGameWindow(config);
  }

  public void setUpGameWindow(GameWindowConfig config) {
    setTitle(config.gameTitle);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(config.gameWidth, config.gameHeight);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
