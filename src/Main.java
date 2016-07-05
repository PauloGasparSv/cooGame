import java.awt.Color;

public class Main {
		
	/* Método principal */
	public static void main(String [] args){

		Stage stage = new Stage();
		MenuStage menu = new MenuStage();
		GameOver go = new GameOver();

		menu.render();
		stage.render();	
		go.render();
		
		
		System.exit(0);
	}
}
