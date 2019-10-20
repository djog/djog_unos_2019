package org.djog_unos.tankgame.game;

public class GameView  
{
	public static void drawGame(TankGame game)
	{
		game.background.draw();
		game.player.draw();
		game.box1.draw();
		game.box2.draw();
		game.box3.draw(); 
	}
}