package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Chimeco extends Chingling{
	
	public Chimeco(String name, int level){
		super(name, level);
		setStats(75, 50, 80, 95, 90, 65);
		addMove(new Venoshock());
	}
}