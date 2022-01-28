package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove{
	
	public Confide(){
		super(Type.NORMAL, 0, 100);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Confide";
	}
	
	@Override
	protected void applyOppEffects(Pokemon p){
		p.setMod(Stat.SPECIAL_ATTACK, -1);
	}

}