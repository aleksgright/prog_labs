package moves;

import ru.ifmo.se.pokemon.*;

public class Thunder extends SpecialMove{
	
	public Thunder(){
		super(Type.ELECTRIC, 110, 70);
	}

	@Override
	protected java.lang.String describe(){
		return "использует Thunder";
	}

	@Override
	protected void applyOppEffects(Pokemon p){
		if(Math.random() < 0.3){
			Effect.paralyze(p);
		}
	}



}