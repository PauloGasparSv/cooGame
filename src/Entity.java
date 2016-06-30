

public abstract class Entity{

		public static final int INACTIVE = 0;
		public static final int ACTIVE = 1;
		public static final int EXPLODING = 2;

		private double x,y;
		private int state;
	
		public abstract void draw(long currentTime);
		public abstract void update(long currentTime,long delta);

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

		public static int findFreeIndex(int [] stateArray){
			int i;
			for( i = 0; i < stateArray.length; i++){
				if(stateArray[i] == INACTIVE) break;
			}
		
			return i;
		}

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
	



}