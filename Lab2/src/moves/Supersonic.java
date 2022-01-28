package moves;

import ru.ifmo.se.pokemon.*;

public class Supersonic extends StatusMove{
	
	public Supersonic(){
		super(Type.NORMAL, 0, 55);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Supersonic";
	}
	
	@Override
	protected void applyOppEffects(Pokemon p){
		Effect.confuse(p);
	}

}