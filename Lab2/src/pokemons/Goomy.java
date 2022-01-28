package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Goomy extends Pokemon{
	
	public Goomy(String name, int level){
		super(name, level);
		setStats(45, 50, 35, 55, 75, 40);
		setType(Type.DRAGON);
		setMove(new Rest(), new Confide());
	}
}