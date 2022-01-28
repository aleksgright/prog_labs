package moves;

import ru.ifmo.se.pokemon.*;

public class GigaDrain extends SpecialMove{
	
	public GigaDrain(){
		super(Type.GRASS, 75, 100);
	}

	@Override
	protected java.lang.String describe(){
		return "использует GigaDrain";
	}

	@Override
	protected void applySelfDamage(Pokemon def, double damage){
		def.setMod(Stat.HP, -(int)Math.round(damage/2));		
	}

}