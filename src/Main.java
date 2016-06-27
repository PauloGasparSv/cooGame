import java.awt.Color;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	

	public static Entity findFreeIndex(Entity [] e){

		int i;
		
		for(i = 0; i < e.length; i++){
			
			if(e[i].getState() == Entity.INACTIVE) break;
		}
		
		return e[i];
	}

	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */ 

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		boolean running = true;

		int killCounter = 0;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		long currentTime = System.currentTimeMillis();

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
		
		int [] e_projectile_states = new int[200];				// estados
		double [] e_projectile_X = new double[200];				// coordenadas x
		double [] e_projectile_Y = new double[200];				// coordenadas y
		double [] e_projectile_VX = new double[200];			// velocidade no eixo x
		double [] e_projectile_VY = new double[200];			// velocidade no eixo y
		double e_projectile_radius = 2.0;						// raio (tamanho dos projéteis inimigos)
		
		/* estrelas que formam o fundo de primeiro plano */
		
		/* estrelas que formam o fundo de segundo plano */
		
		Background background = new Background();

		long nextEnemy2 = currentTime + 3000;
		double enemy2_spawnX = 10;
		int enemy2_count = 0;

		/* inicializações */
		
		for(int i = 0; i < e_projectile_states.length; i++) e_projectile_states[i] = INACTIVE;
		for(int i = 0; i < enemies.length; i++) enemies[i] = new Enemy(0,0);
		for(int i = 0; i < enemies2.length; i++) enemies2[i] = new Enemy2(0,0);
	
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
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
				
				for(int i = 0; i < e_projectile_states.length; i++){
					
					double dx = e_projectile_X[i] - player.getX();
					double dy = e_projectile_Y[i] - player.getY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					if(dist < (player.getRadius() + e_projectile_radius) * 0.8){
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
				p.update(delta);
			
			/* projeteis (inimigos) */
			
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == ACTIVE){
					
					/* verificando se projétil saiu da tela */
					if(e_projectile_Y[i] > GameLib.HEIGHT) {
						
						e_projectile_states[i] = INACTIVE;
					}
					else {
					
						e_projectile_X[i] += e_projectile_VX[i] * delta;
						e_projectile_Y[i] += e_projectile_VY[i] * delta;
					}
				}
			}
			
			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemies.length; i++){
				
				if(enemies[i].getState() == Entity.EXPLODING && enemies[i].isDoneExploding(currentTime)){		
					enemies[i].setState(Entity.INACTIVE);
				}
				
				
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
																							
							int free = findFreeIndex(e_projectile_states);
							
							if(free < e_projectile_states.length){
								
								e_projectile_X[free] = enemies[i].getX();
								e_projectile_Y[free] = enemies[i].getY();
								e_projectile_VX[free] = Math.cos(enemies[i].getAngle()) * 0.45;
								e_projectile_VY[free] = Math.sin(enemies[i].getAngle()) * 0.45 * (-1.0);
								e_projectile_states[free] = 1;
								
								enemies[i].setNextShot((long) (currentTime + 200 + Math.random() * 500));
							}
						}
					}
				}
			}
			
			/* inimigos tipo 2 */
			
			for(int i = 0; i < enemies2.length; i++){
				
				if(enemies2[i].getState() == Entity.EXPLODING){
					
					if(enemies2[i].isDoneExploding(currentTime)){
						
						enemies2[i].setState(Entity.INACTIVE);
					}
				}
				
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
							int [] freeArray = findFreeIndex(e_projectile_states, angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < e_projectile_states.length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);
										
									e_projectile_X[free] = enemies2[i].getX();
									e_projectile_Y[free] = enemies2[i].getY();
									e_projectile_VX[free] = vx * 0.30;
									e_projectile_VY[free] = vy * 0.30;
									e_projectile_states[free] = 1;
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
				p.draw();
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e_projectile_X[i], e_projectile_Y[i], e_projectile_radius);
				}
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
		
		System.exit(0);
	}
}
