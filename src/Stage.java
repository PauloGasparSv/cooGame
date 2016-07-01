import java.awt.Color;

public class Stage{
	

	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == Entity.INACTIVE) break;
		}
		
		return i;
	}

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == Entity.INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}

	public static int [] findFreeIndex(Entity [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i].getState() == Entity.INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	

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

		/* variáveis dos projéteis disparados pelo player */
		
		Projectile proj [] = new Projectile[10];
		for (int i = 0; i < 10; i++)
			proj[i] = new Projectile();

		/* variáveis dos inimigos tipo 1 */
		Enemy [] enemies = new Enemy[10];
		long nextEnemy1 = currentTime + 2000;					// instante em que um novo inimigo 1 deve aparecer
		
		/* variáveis doimigos tipo 2 */

		Enemy2 enemies2[] = new Enemy2[10];
		
		/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		

		Entity eproj[] = new DecoratorEnemyFire[200];
		for(int i = 0;i < 200; i ++)eproj[i] = DecoratorEnemyFire(new Projectile(),player);
		
		/* estrelas que formam o fundo de primeiro plano */
		
		/* estrelas que formam o fundo de segundo plano */
		
		Background background = new Background();

		long nextEnemy2 = currentTime + 3000;
		double enemy2_spawnX = 10;
		int enemy2_count = 0;

		/* inicializações */
		for(int i = 0; i < enemies.length; i++) enemies[i] = new Enemy(0,0,player);
		for(int i = 0; i < enemies2.length; i++) enemies2[i] = new Enemy2(0,0,player);
	
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();


		while(running){
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			if(player.getState() == Entity.ACTIVE){
				
				/* colisões player - projeteis (inimigo) */
				
				for(int i = 0; i < eproj.length; i++){
					
					double dx = eproj[i].getProj.getX() - player.getX();
					double dy = eproj[i].getProj.getY() - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + eproj[i].getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
			
				/* colisões player - inimigos */
							
				for(int i = 0; i < enemies.length; i++){
					
					double dx = enemies[i].getX() - player.getX();
					double dy = enemies[i].getY() - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemies[i].getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
				
				for(int i = 0; i < enemies2.length; i++){
					
					double dx = enemies2[i].getX() - player.getX();
					double dy = enemies2[i].getY() - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + enemies2[i].getRadius()) * 0.8){
						player.explode(currentTime);
					}
				}
			}
			
			/* colisões projeteis (player) - inimigos */
			
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
						}
					}
				}
				
				for(int i = 0; i < enemies2.length; i++){
					
					if(enemies2[i].getState() == ACTIVE){
						
						double dx = enemies2[i].getX() - proj[k].getX();
						double dy = enemies2[i].getY() - proj[k].getY();
						double dist = Math.sqrt(dx * dx + dy * dy);
						
						if(dist < enemies2[i].getRadius()){
							enemies2[i].explode(currentTime);
						}
					}
				}
			}
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			
			for(Projectile p:proj)
				p.update(currentTime,delta);
			
			/* projeteis (inimigos) */
			
			for(int i = 0; i < eproj.length; i++)
				eproj[i].update(currentTime,delta);
			
			
			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemies.length; i++){
				
				enemies[i].update(currentTime,delta);
				
				if(enemies[i].getState() == Entity.ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemies[i].getY() > GameLib.HEIGHT + 10) {
						
						enemies[i].setState(Entity.INACTIVE);
					}
					else {
					
						enemies[i].setX(enemies[i].getX() + enemies[i].getSpeed() * Math.cos(enemies[i].getAngle()) * delta);
						enemies[i].setY(enemies[i].getY() + enemies[i].getSpeed() * Math.sin(enemies[i].getAngle()) * delta * (-1.0));
						enemies[i].setAngle(enemies[i].getAngle() + enemies[i].getAngleV() * delta);
						
						if(enemies[i].canShoot(currentTime) && enemies[i].getY() < player.getY()){
																							
							int free = 0;
							for(free = 0; free <= eproj.length; free++){
								if(free == eproj.length)break;
								if(eproj[free].getState() == Entity.INACTIVE)break;
							}
							
							if(free < eproj.length){
								
								eproj[free].setX(enemies[i].getX());
								eproj[free].setY(enemies[i].getY());
								eproj[free].setSpeedX(Math.cos(enemies[i].getAngle()) * 0.45);
								eproj[free].setSpeedY(Math.sin(enemies[i].getAngle()) * 0.45 * (-1.0));
								eproj[free].setState(Entity.ACTIVE);
								enemies[i].setNextShot((long) (currentTime + 200 + Math.random() * 500));
							}
						}
					}
				}
			}
			
			/* inimigos tipo 2 */
			
			for(int i = 0; i < enemies2.length; i++){
				
				enemies2[i].update(currentTime,delta);
				
				if(enemies2[i].getState()  == Entity.ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(	enemies2[i].getX() < -10 || enemies2[i].getX() > GameLib.WIDTH + 10 ) {
						
						enemies2[i].setState(Entity.INACTIVE);
					}
					else {
						
						boolean shootNow = false;
						double previousY = enemies2[i].getY();
												
						enemies2[i].setX(enemies2[i].getX() +  enemies2[i].getSpeed() * Math.cos(enemies2[i].getAngle()) * delta);
						enemies2[i].setY(enemies2[i].getY()  + enemies2[i].getSpeed() * Math.sin(enemies2[i].getAngle()) * delta * (-1.0));
						enemies2[i].setAngle(enemies2[i].getAngle() + enemies2[i].getAngleV() * delta);
						
						double threshold = GameLib.HEIGHT * 0.30;
						
						if(previousY < threshold && enemies2[i].getY() >= threshold) {
							
							if(enemies2[i].getX() < GameLib.WIDTH / 2) enemies2[i].setAngleV(0.003);
							else enemies2[i].setAngleV(-0.003);
						}
						
						if(enemies2[i].getAngleV() > 0 && Math.abs(enemies2[i].getAngle() - 3 * Math.PI) < 0.05){
							
							enemies2[i].setAngleV(0.0);
							enemies2[i].setAngle(3 * Math.PI);
							shootNow = true;
						}
						
						if(enemies2[i].getAngleV() < 0 && Math.abs(enemies2[i].getAngle()) < 0.05){
							
							enemies2[i].setAngleV (0.0);
							enemies2[i].setAngle (0.0);
							shootNow = true;
						}
																		
						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							int [] freeArray = findFreeIndex(eproj, angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < eproj.length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);
										
									eproj[free].setX(enemies2[i].getX());
									eproj[free].setY(enemies2[i].getY());
									eproj[free].setSpeedX(vx * 0.30);
									eproj[free].setSpeedY(vy * 0.30);
									eproj[free].setState(Entity.ACTIVE);
								}
							}
						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			
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
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			
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
					enemies2[free].setState(ACTIVE);

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
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */


			player.update(currentTime,delta);

		
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			
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
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda estão dentro	*/
			/* da tela de jogo após processar entrada do usuário.       */
			
			

			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo distante */
	
			background.draw(delta);

			/* desenhando player */
			
			player.draw(currentTime);
			
			/* deenhando projeteis (player) */
			
			for(Projectile p:proj)
				p.draw(currentTime);
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < eproj.length; i++){
				eproj[i].draw(currentTime);
			}
			
			/* desenhando inimigos (tipo 1) */
			
			for(Enemy e : enemies)
				e.draw(currentTime);
			
			for(Enemy2 e : enemies2)
				e.draw(currentTime);

			/* desenhando inimigos (tipo 2) */
			
		
			GameLib.drawText(10,50,"DEATHS "+player.getDeaths());
			GameLib.drawText(320,50,"POINTS "+killCounter);
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}


		
	}


	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	


}