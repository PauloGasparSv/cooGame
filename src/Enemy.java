
public class Enemy extends Entity{	
		private double v;

		private double radius;

		private double angle;
		private double angleV;

		private double explosion_start;
		private double explosion_end;

		private long nextShot;

		

		public Player(double x,double y){
			setX(x);
			setY(y);
			this.radius = 9;
			initialize();

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


		public void update(){



		}

		public void draw(){



		}

		public void explode(long currentTime){
			setState(Entity.EXPLODING);
			explosion_start = currentTime;
			explosion_end = currentTime + 500;
		}

		public boolean isDoneExploding(long currentTime){
			return (currentTime > explosion_end);
		}

		public void setNextShoot(lont nextShot){
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
		public int getDeaths(){
			return deathCounter;
		}
		public void setNextShot(long nextShot){
			this.nextShot = nextShot;
		}

		public double getAlpha(long currentTime){
			return (currentTime - explosion_start) / (explosion_end - explosion_start);
		}

} 