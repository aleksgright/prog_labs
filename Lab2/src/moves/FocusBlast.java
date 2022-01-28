package moves;

import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove{
	
	public FocusBlast(){
		super(Type.FIGHTING, 120, 70);
	}

	@Override
	protected java.lang.String describe(){
		return "использует Focus Blast";
	}

	@Override
	protected void applyOppEffects(Pokemon p){
		if(Math.random() < 0.1){
			p.setMod(Stat.SPECIAL_DEFENSE, -1);
		}
	}
}