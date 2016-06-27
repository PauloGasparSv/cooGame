import java.awt.Color;

public class Projectile{

	private double x;
	private double y;
	private double vx;
	private double vy;
	
	private int state;

	public Projectile(){
		state = Entity.INACTIVE;
		vy = -1;
	}

	public void update(long delta){

		if(getState() == Entity.ACTIVE){
			
					/* verificando se projétil saiu da tela */
			if(getY() < 0) {
						
				setState(Entity.INACTIVE);
			}
			else {
			
				setX(getX()+ getSpeedX() * delta);
				setY(getY() + getSpeedY() * delta);
			}
		}

	}


	public void draw(){

		if(getState() == Entity.ACTIVE){	
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
			GameLib.drawLine(getX() - 1, getY()- 3, getX() - 1, getY() + 3);
			GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
		}

	}

	public int getState(){
		return state;
	}

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getSpeedX(){
		return vx;
	}
	public double getSpeedY(){
		return vy;
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

	public void setSpeedX(double vx){
		this.vx = vx;
	}
	public void setSpeedY(double vy){
		this.vy = vy;
	}

}