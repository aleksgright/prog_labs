package moves;

import ru.ifmo.se.pokemon.*;

public class Venoshock extends SpecialMove{
	
	public Venoshock(){
		super(Type.POISON, 65, 100);
	}
	
	@Override
	protected java.lang.String describe(){
		return "использует Venoshock";
	}
	
	@Override
	protected void applyOppDamage(Pokemon def, double damage){
		int k = 1;
		if(def.getCondition() == Status.POISON){
			k = 2;
		}
		def.setMod(Stat.HP, (int)Math.round(damage*k));
	}
}