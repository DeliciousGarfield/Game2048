package org.tianxiduo.game2048;

public class GameMain {
  private static GameWindowConfig gameWindowConfig = new GameWindowConfig();
  
  public static void buildGameWindowConfig() {
    gameWindowConfig.gameTitle = "2048";
    gameWindowConfig.gameWidth = 305;
    gameWindowConfig.gameHeight = 325;
  }

  public static void bootstrap() {
    buildGameWindowConfig();
    
    GameWindow gameWindow = new GameWindow();
    gameWindow.setUpGameWindow(gameWindowConfig);
  }

  public static void main(String[] args) {
    bootstrap();
  }

}
