import java.awt.Color;
import java.util.*;


public class Enemy extends Entity{	
		private double v;

		private double radius;

		private double angle;
		private double angleV;

		private double explosion_start;
		private double explosion_end;

		private long nextShot;

		private Player player;



		private DecoratorEnemyFire proj;


		public Enemy(double x,double y,Player player){
			setX(x);
			setY(y);
			this.radius = 9;
			initialize();
			this.player = player;
			proj   = new  DecoratorEnemyFire(new Projectile());

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

			if(getState() == EXPLODING && isDoneExploding(currentTime)){		
				setState(Entity.INACTIVE);
			}

			if(getY() > GameLib.HEIGHT + 10) {
						
				setState(Entity.INACTIVE);
				return;
			}

			if(getState() == ACTIVE){					

				setX(getX() + getSpeed() * Math.cos(getAngle()) * delta);
				setY(getY() + getSpeed() * Math.sin(getAngle()) * delta * (-1.0));
				setAngle(getAngle() + getAngleV() * delta);

				double dx = getX() - player.getX();
				double dy = getY() - player.getY();
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < (player.getRadius() + getRadius()) * 0.8  && player.getState() != EXPLODING){
					
					player.explode(currentTime);
					explode(currentTime);
					
				}


				if(canShoot(currentTime) && getY() < player.getY() && proj.getProj().getState() != ACTIVE){	
					proj.setX(getX());
					proj.setY(getY());
					proj.setSpeedX(Math.cos(getAngle()) * 0.45);
					proj.setSpeedY(Math.sin(getAngle()) * 0.45 * (-1.0));
					proj.setState(Entity.ACTIVE);		
					
					setNextShot((long) (currentTime + 200 + Math.random() * 500));
				}


				

			}
			proj.update(player,currentTime,delta);
			
				

		}

		public void draw(long currentTime){
			if(getState() == EXPLODING){
				GameLib.setColor(Color.RED);
				GameLib.drawExplosion(getX(), getY(), getAlpha(currentTime));
			}
				
			if(getState() == ACTIVE){
		
				GameLib.setColor(Color.CYAN);
				GameLib.drawCircle(getX(), getY(), getRadius());
			}
		
	    	proj.draw(currentTime);
	    	


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