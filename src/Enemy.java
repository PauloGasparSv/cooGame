
public class Enemy extends Entity{	
		private double v;

		private double radius;

		private double explosion_start;
		private double explosion_end;

		private long nextShot;

		private int deathCounter;

		public Player(double x,double y,double radius){
			setX(x);
			setY(y);
			this.radius = radius;
			initialize();

		}

		public void initialize(){
			nextShot = System.currentTimeMillis();
			explosion_end = 0;
			explosion_start = 0;
			v = 0;
			deathCounter = 0;
			setState(INACTIVE);
		}


		public void update(){



		}

		public void draw(){



		}

		public void explode(long currentTime){
			if(getState() != Entity.EXPLODING)
				deathCounter++;
			setState(Entity.EXPLODING);
			explosion_start = currentTime;
			explosion_end = currentTime + 2000;
		}

		public boolean isDoneExploding(long currentTime){
			return (currentTime > explosion_end);
		}

		public boolean canShoot(long currentTime){
			return (currentTime > nextShot);
		}

		public double getSpeed(){
			return v;
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