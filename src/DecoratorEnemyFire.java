import java.awt.Color;


public class DecoratorEnemyFire extends Decorator{
	
	private Projectile p;

	private Player player;


	public DecoratorEnemyFire(Projectile p,Player player){
		this.p = p;
		this.player = player;
	}

	
	public void update(long currentTime,long delta){
		p.update(currentTime,delta);			
	}

	public void draw(long currentTime){
		if(p.getState() == ACTIVE){	
			GameLib.setColor(Color.RED);
			GameLib.drawCircle(p.getX(), p.getY(), getRadius());
		}
	}

	public Projectile getProj(){
		return p;
	}

	public double getRadius(){
		return 2;
	}
	public double getSpeedX(){
		return p.getSpeedX();
	}
	public double getSpeedY(){
		return p.getSpeedY();
	}

	public void setState(int state){
		this.p.setState(state);
	}


	public void setSpeedX(double vx){
		this.p.setSpeed(vx);
	}
	public void setSpeedY(double vy){
		this.p.setSpeedY(vy);
	}

}