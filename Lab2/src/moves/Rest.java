package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove{
	
	public Rest(){
		super(Type.PSYCHIC, 0, 100);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Rest";
	}
	
	@Override
	protected void applySelfEffects(Pokemon p){
		Effect e = new Effect().turns(2).condition(Status.SLEEP);
		p.restore();
		p.setCondition(e);
	}

}