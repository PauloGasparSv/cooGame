import java.awt.Color;

public class GameOver{
	

		
	public void render(){
		/* Indica que o jogo está em execução */
		boolean running = true;

		long started = System.currentTimeMillis();


		while(running){		

			if(System.currentTimeMillis() - started > 3000){
				for(int i = 0; i < 6;i++)
					if(GameLib.iskeyPressed(i)) 
						running = false;
			}
			
			
		
			GameLib.drawText(45,250,"Thanks for playing");
			GameLib.drawText(55,350,"GAME OVER");
	
	
			GameLib.display();
			
			
		}


		
	}


	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	


}