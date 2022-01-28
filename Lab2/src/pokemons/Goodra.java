package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Goodra extends Sliggoo{
	
	public Goodra(String name, int level){
		super(name, level);
		setStats(90, 100, 70, 110, 150, 80);
		addMove(new Bite());
	}
}