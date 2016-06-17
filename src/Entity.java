

public abstract class Entity{

		public static final int INACTIVE = 0;
		public static final int ACTIVE = 1;
		public static final int EXPLODING = 2;

		private double x,y;
		private int state;
	
		public abstract void draw();
		public abstract void update();

		public int getState(){
			return state;
		}
		public void setState(int state){
			this.state = state;
		}
		public void setX(double x){
			this.x = x;
		}
		public void setY(double y){
			this.y = y;
		}
		public double getX(){
			return x;
		}
		public double getY(){
			return y;
		}

}