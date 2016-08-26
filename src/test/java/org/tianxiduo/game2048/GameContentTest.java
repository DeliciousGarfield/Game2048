package org.tianxiduo.game2048;

import static org.junit.Assert.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameContentTest {
	private GameContent gameContent;
	@Before
	public void setUp() {
		gameContent = new GameContent();
	}

	@Test
	public void testLeftwardTwoPairsTileMerge() {
		gameContent.setTileNumber(0, 0, 2);
		gameContent.setTileNumber(0, 1, 2);
		gameContent.setTileNumber(0, 2, 2);
		gameContent.setTileNumber(0, 3, 2);
		gameContent.moveLeft();
		
		assertEquals(gameContent.getTileNumber(0, 0), 4);
		assertEquals(gameContent.getTileNumber(0, 1), 4);
		assertEquals(gameContent.getTileNumber(0, 2), 0);
		assertEquals(gameContent.getTileNumber(0, 3), 0);
	}
	
	@Test
	public void testEventPass() {
		KeyEvent e = new KeyEvent();
		e.setKeyCode(KeyEvent.VK_LEFT);
		GameWindow gameWindow = new GameWindow();
		gameWindow.dispatchEvent(e);
		// gameWindow.processEvent(e);
		
		assertEquals(gameContent.direction, KeyEvent.VK_LEFT);
	}
	
	@Test
	public void testGameWin() {
		assertFalse(gameContent.win);
		gameContent.setTileNumber(0, 0, 1024);
		gameContent.setTileNumber(0, 1, 1024);
		gameContent.moveLeft();
		
		assertEquals(gameContent.getTileNumber(0, 0), 2048);
		assertTrue(gameContent.win);
	}
	
	@After
	public void tearDown() {
		gameContent = null;
	}

}
