package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Sliggoo extends Goomy{
	
	public Sliggoo(String name, int level){
		super(name, level);
		setStats(68, 75, 53, 83, 113, 60);
		addMove(new GigaDrain());
	}
}