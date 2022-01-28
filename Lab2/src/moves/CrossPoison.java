package moves;

import ru.ifmo.se.pokemon.*;

public class CrossPoison extends PhysicalMove{
	
	public CrossPoison(){
		super(Type.POISON, 70, 100);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Cross Poison";
	}
	
	@Override
	protected void applyOppEffects(Pokemon p){
		if(Math.random() < 0.1){
			Effect.poison(p);
		}
	}

	@Override
	protected double calcCriticalHit(Pokemon att, Pokemon def){
		if(Math.random()< att.getStat(Stat.SPEED)*3/512){
			return 2;
		}else{
			return 1;
		}
	}

}