import java.awt.Color;

public class Enemy extends Entity{	
		private double v;

		private double radius;

		private double angle;
		private double angleV;

		private double explosion_start;
		private double explosion_end;

		private long nextShot;

		private Player player;

		public Enemy(double x,double y,Player player){
			setX(x);
			setY(y);
			this.radius = 9;
			initialize();
			this.player = player;

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
				

		}

		public void draw(long currentTime){
			if(getState() == EXPLODING){
					GameLib.drawExplosion(getX(), getY(), getAlpha(currentTime));
				}
				
				if(getState() == ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(getX(), getY(), getRadius());
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