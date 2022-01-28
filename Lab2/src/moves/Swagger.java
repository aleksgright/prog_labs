package moves;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove{
	
	public Swagger(){
		super(Type.NORMAL, 0, 85);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Swagger";
	}
	
	@Override
	protected void applyOppEffects(Pokemon p){
		Effect.confuse(p);
		p.setMod(Stat.ATTACK, 2);
	}

}