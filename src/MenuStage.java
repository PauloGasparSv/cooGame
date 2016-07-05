import java.awt.Color;

public class MenuStage{
	

		
	public void render(){
		/* Indica que o jogo está em execução */
		boolean running = true;


		
		GameLib.initGraphics();


		while(running){		
			for(int i = 0; i < 6;i++)
				if(GameLib.iskeyPressed(i)) 
					running = false;
			
			
		
			GameLib.drawText(45,250,"PRESS ANY DIRECTION");
			GameLib.drawText(55,350,"OR CONTROL TO PLAY");
	
	
			GameLib.display();
			
			
		}


		
	}


	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	


}