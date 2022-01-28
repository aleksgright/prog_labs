package moves;

import ru.ifmo.se.pokemon.*;

public class Bite extends PhysicalMove{
	
	public Bite(){
		super(Type.DARK, 60, 100);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Bite";
	}
	
	@Override
	protected void applyOppEffects(Pokemon p){
		if(Math.random() < 0.3){
			Effect.flinch(p);
		}
	}

}