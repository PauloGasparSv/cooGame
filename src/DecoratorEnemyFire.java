import java.awt.Color;


public class DecoratorEnemyFire extends Decorator{
	
	private Projectile p;



	public DecoratorEnemyFire(Projectile p){
		this.p = p;
	}

	
	public void update(Player player,long currentTime,long delta){
		
		if(p.getState() == ACTIVE){
		
			System.out.println("POW");
			p.update(currentTime,delta);

			double dx = getProj().getX() - player.getX();
			double dy = getProj().getY() - player.getY();
			double dist = Math.sqrt(dx * dx + dy * dy);


			if(dist < (player.getRadius() + getRadius()) * 0.8 && player.getState() != EXPLODING){
				player.explode(currentTime);
				p.setState(EXPLODING);
			}
		}

	}

	public void draw(long currentTime){
		if(p.getState() == ACTIVE){	
			p.draw(currentTime);
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
		this.p.setSpeedX(vx);
	}
	public void setSpeedY(double vy){
		this.p.setSpeedY(vy);
	}

	public void setX(double x){
		this.p.setX(x);
	}
	public void setY(double y){
		this.p.setY(y);
	}
}