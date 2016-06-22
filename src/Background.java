import java.awt.Color;

public class Background{
	
	private double [][] estrelasFrente = new double[50][2];
	private double [][] estrelasFundo = new double[50][2];
	private double countFrente;
	private double countTraz;
	private double speed;

	public Background(){

		speed = 0.045;
		countFrente = 0;
		countTraz = 0;

		for(int i = 0; i < estrelasFrente.length; i++){
			
			estrelasFrente[i][0] = Math.random() * GameLib.WIDTH;
			estrelasFrente[i][1] = Math.random() * GameLib.HEIGHT;
			estrelasFundo[i][0] = Math.random() * GameLib.WIDTH;
			estrelasFundo[i][1] = Math.random() * GameLib.HEIGHT;
		}

	}

	public void update(){



	}


	public void draw(long delta){

		GameLib.setColor(Color.DARK_GRAY);
		countTraz += speed * delta;
			
		for(int i = 0; i < estrelasFundo.length; i++){
			GameLib.fillRect(estrelasFundo[i][0], (estrelasFundo[i][1] + countTraz) % GameLib.HEIGHT, 2, 2);
		}
			
		/* desenhando plano de fundo próximo */
			
		GameLib.setColor(Color.GRAY);
		countFrente += speed * delta;
			
		for(int i = 0; i < estrelasFrente.length; i++){
				
			GameLib.fillRect(estrelasFrente[i][0], (estrelasFrente[i][1] + countFrente) % GameLib.HEIGHT, 3, 3);
		}
					


	}


}