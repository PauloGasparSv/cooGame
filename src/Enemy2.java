import java.awt.Color;
import java.util.*;

public class Enemy2 extends Entity{	
		private double v;

		private double radius;

		private double angle;
		private double angleV;

		private double explosion_start;
		private double explosion_end;

		private long nextShot;

		private Player player;
		

		private List<DecoratorEnemyFire> proj;

		public Enemy2(double x,double y,Player player){
			setX(x);
			setY(y);
			this.radius = 12;
			initialize();
			this.player =  player;

			proj  = new LinkedList<DecoratorEnemyFire>();
		}

		public void initialize(){
			nextShot = System.currentTimeMillis();
			explosion_end = 0;
			explosion_start = 0;
			v = 0;
			angle = 0;
			angleV = 0;
			setState(INACTIVE);
		}


		public void update(long currentTime,long delta){
			if(getState() == EXPLODING){
					
					if(isDoneExploding(currentTime)){
						
						setState(Entity.INACTIVE);
					}
			}

			if(	getX() < -10 || getX() > GameLib.WIDTH + 10 ) {
				setState(Entity.INACTIVE);
			}

			if(getState() == ACTIVE){					
				double dx = getX() - player.getX();
				double dy = getY() - player.getY();
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < (player.getRadius() + getRadius()) * 0.8 && player.getState() == ACTIVE){
					player.explode(currentTime);
				}


				setX(getX() +  getSpeed() * Math.cos(getAngle()) * delta);
				setY(getY()  + getSpeed() * Math.sin(getAngle()) * delta * (-1.0));
				setAngle(getAngle() + getAngleV() * delta);


				if(getY() < GameLib.HEIGHT * 0.30 &&getY() > GameLib.HEIGHT * 0.1 ) {
							
					if(getX() < GameLib.WIDTH / 2) setAngleV(0.003);
					else setAngleV(-0.003);
				}

				boolean shootNow = false;


				if(getAngleV() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05){
					
					setAngleV(0.0);
					setAngle(3 * Math.PI);
					shootNow = true;
				}
						
				if(getAngleV() < 0 && Math.abs(getAngle()) < 0.05){
							
					setAngleV (0.0);
					setAngle (0.0);
					shootNow = true;
				}

				if(shootNow){
					double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
					
									
					double a = angles[0] + Math.random() * Math.PI/6 - Math.PI/12;
					double vx = Math.cos(a);
					double vy = Math.sin(a);
					
					DecoratorEnemyFire	 temp = new DecoratorEnemyFire(new Projectile());

					temp.setX(getX());
					temp.setY(getY());
					temp.setSpeedX(vx * 0.30);
					temp.setSpeedY(vy * 0.30);
					temp.setState(ACTIVE);
						
					
				}

				for (ListIterator<DecoratorEnemyFire> iter = proj.listIterator(); iter.hasNext(); ) {
	    			DecoratorEnemyFire p = iter.next();
				  	p.update(player,currentTime,delta);
				    if (p.getState() == INACTIVE) {
				    	iter.remove();
				    }

				}


			}
				


		}

		public void draw(long currentTime){
			if(getState() == EXPLODING){
					
				double alpha = getAlpha(currentTime);
				GameLib.drawExplosion(getX(), getY(), alpha);
			}
				
			if(getState() == ACTIVE){
			
				GameLib.setColor(Color.MAGENTA);
				GameLib.drawDiamond(getX(), getY(), getRadius());
			}
			for (ListIterator<DecoratorEnemyFire> iter = proj.listIterator(); iter.hasNext(); ) {
	    		DecoratorEnemyFire p = iter.next();
	    		//p.draw(currentTime);
	    	}

		}

		public void explode(long currentTime){
			setState(EXPLODING);
			explosion_start = currentTime;
			explosion_end = currentTime + 500;
		}

		public boolean isDoneExploding(long currentTime){
			return (currentTime > explosion_end);
		}

		public void setNextShoot(long nextShot){
			this.nextShot = nextShot;
		}

		public boolean canShoot(long currentTime){
			return (currentTime > nextShot);
		}

		public double getSpeed(){
			return v;
		}
		public double getAngle(){
			return angle;
		}
		public double getAngleV(){
			return angleV;
		}
		public void setAngle(double angle){
			this.angle = angle;
		}
		public void setAngleV(double anglev){
			this.angleV = angleV;
		}

		public void setSpeed(double v){
			this.v = v;
		}

		public double getRadius(){
			return radius;
		}
		public void setRadius(double radius){
			this.radius = radius;
		}
		public void setNextShot(long nextShot){
			this.nextShot = nextShot;
		}

		public double getAlpha(long currentTime){
			return (currentTime - explosion_start) / (explosion_end - explosion_start);
		}

} 