import java.awt.Color;

public class Stage{
	

		

	public void render(){
		/* Indica que o jogo está em execução */
		boolean running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		long currentTime = System.currentTimeMillis();

		int killCounter = 0;

		/* variáveis do player */
		


		Player player = new Player(GameLib.WIDTH / 2,GameLib.HEIGHT * 0.90,12);
		player.setSpeedX(0.25);
		player.setSpeedY(0.25);

		Projectile proj [] = new Projectile[10];
		for (int i = 0; i < 10; i++)
			proj[i] = new Projectile();

		Enemy [] enemies = new Enemy[10];
		long nextEnemy1 = currentTime + 2000;					// instante em que um novo inimigo 1 deve aparecer
		
		Enemy2 enemies2[] = new Enemy2[10];

		Background background = new Background();

		long nextEnemy2 = currentTime + 3000;
		double enemy2_spawnX = 10;
		int enemy2_count = 0;

		for(int i = 0; i < enemies.length; i++) enemies[i] = new Enemy(0,0,player);
		for(int i = 0; i < enemies2.length; i++) enemies2[i] = new Enemy2(0,0,player);
	
		while(running){

			delta = System.currentTimeMillis() - currentTime;
			
			currentTime = System.currentTimeMillis();
		
			for(int k = 0; k < proj.length; k++){
				
				for(int i = 0; i < enemies.length; i++){
										
					if(enemies[i].getState() == Entity.ACTIVE){
					
						double dx = enemies[i].getX() - proj[k].getX();
						double dy = enemies[i].getY() - proj[k].getY();
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemies[i].getRadius()){
							if(enemies[i].getState() != Entity.EXPLODING)
								killCounter++;
							enemies[i].explode(currentTime);
							proj[k].setState(Entity.INACTIVE);
						}
					}
				}
				
				for(int i = 0; i < enemies2.length; i++){
					
					if(enemies2[i].getState() == Entity.ACTIVE){
						
						double dx = enemies2[i].getX() - proj[k].getX();
						double dy = enemies2[i].getY() - proj[k].getY();
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemies2[i].getRadius()){
							enemies2[i].explode(currentTime);
						}
					}
				}
			}
				
			for(Projectile p:proj)
				p.update(currentTime,delta);
			
	

			if(currentTime > nextEnemy1){
				
				int indice = -1;
				for(int i  = 0;i < enemies.length; i++)
					if(enemies[i].getState() == Entity.INACTIVE)
						indice = i;
								
				if(indice != -1){
					enemies[indice].setX (Math.random() * (GameLib.WIDTH - 20.0) + 10.0);
					enemies[indice].setY(-10.0);
					enemies[indice].setSpeed(0.20 + Math.random() * 0.15);
					enemies[indice].setAngle(3 * Math.PI / 2);
					enemies[indice].setAngleV(0.0);
					enemies[indice].setState(Entity.ACTIVE);
					enemies[indice].setNextShot(currentTime + 500);
					nextEnemy1 = currentTime + 500;
				}
			}
			
			if(currentTime > nextEnemy2){
				
				int free = -1;
				for(int i  = 0;i < enemies2.length; i++)
					if(enemies2[i].getState() == Entity.INACTIVE)
						free = i;
								
				if(free < enemies2.length){
					
					enemies2[free].setX(enemy2_spawnX);
					enemies2[free].setY(-10.0);
					enemies2[free].setSpeed(0.42);
					enemies2[free].setAngle((3 * Math.PI) / 2);
					enemies2[free].setAngleV(0.0);
					enemies2[free].setState(Entity.ACTIVE);

					enemy2_count++;
					
					if(enemy2_count < 10){
						
						nextEnemy2 = currentTime + 120;
					}
					else {
						
						enemy2_count = 0;
						enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
						nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
					}
				}
			}

			player.update(currentTime,delta);

		
			
			if(player.getState() == Entity.ACTIVE){
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) player.setY(player.getY() - delta * player.getSpeedY());
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player.setY(player.getY() + delta * player.getSpeedY());
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player.setX(player.getX() - delta * player.getSpeedX());
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player.setX(player.getX() + delta * player.getSpeedY());
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					
					if(player.canShoot(currentTime)){
						int free = -1;

						for(int i =0;  i < 10; i++)
							if(proj[i].getState() == Entity.INACTIVE)
								free = i;

						if(free == -1)return;
						 						
						if(free < proj.length){
							
							proj[free].setX(player.getX());
							proj[free].setY(player.getY() - 2 * player.getRadius());
							proj[free].setSpeedX(0.0);
							proj[free].setSpeedY (-1.0);
							proj[free].setState(Entity.ACTIVE);
							player.setNextShot(currentTime + 100);
						}
					}	
				}
			}
			
			
			
			for(Enemy e : enemies)
				e.update(currentTime,delta);
			for(Enemy2 e : enemies2)
				e.update(currentTime,delta);
			
	
			background.draw(delta);

			
			player.draw(currentTime);
			
			
			for(Projectile p:proj)
				p.draw(currentTime);
			
			
			for(Enemy e : enemies)
				e.draw(currentTime);
			
			for(Enemy2 e : enemies2)
				e.draw(currentTime);
		
			GameLib.drawText(10,50,"DEATHS "+player.getDeaths());
			GameLib.drawText(320,50,"POINTS "+killCounter);
			GameLib.display();
			
			busyWait(currentTime + 5);

			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE) || player.getDeaths() > 3) running = false;
		}


		
	}


	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	


}