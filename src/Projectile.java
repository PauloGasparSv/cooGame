import java.awt.Color;

public class Projectile extends Entity{

	private double vx;
	private double vy;
	
	private int state;

	public Projectile(){
		state = INACTIVE;
		vy = -1;
	}

	public void update(long currentTime,long delta){

		if(getState() == ACTIVE){
			
					/* verificando se projétil saiu da tela */
			if(getY() < -2 || getY() > GameLib.HEIGHT + 20) {
						
				setState(INACTIVE);
			}
			else {
			
				setX(getX()+ getSpeedX() * delta);
				setY(getY() + getSpeedY() * delta);
			}
		}

	}


	public void draw(long currentTime){

		if(getState() == ACTIVE){	
			GameLib.setColor(Color.GREEN);
			GameLib.drawLine(getX(), getY() - 5, getX(), getY() + 5);
			GameLib.drawLine(getX() - 1, getY()- 3, getX() - 1, getY() + 3);
			GameLib.drawLine(getX() + 1, getY() - 3, getX() + 1, getY() + 3);
		}

	}

	public int getState(){
		return state;
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


	public void setSpeedX(double vx){
		this.vx = vx;
	}
	public void setSpeedY(double vy){
		this.vy = vy;
	}

}