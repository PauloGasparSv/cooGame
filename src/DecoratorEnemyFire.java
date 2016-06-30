import java.awt.Color;
public class DecoratorEnemyFire extends Decorator{
	
	private Projectile p;


	public DecoratorEnemyFire(Projectile p){
		this.p = p;
	}

	
	public void update(long currentTime,long delta){
		p.update(currentTime,delta);			
	}

	public void draw(long currentTime){
		if(p.getState() == ACTIVE){	
			GameLib.setColor(Color.RED);
			GameLib.drawCircle(p.getX(), p.getY(), 2);
		}
	}


}